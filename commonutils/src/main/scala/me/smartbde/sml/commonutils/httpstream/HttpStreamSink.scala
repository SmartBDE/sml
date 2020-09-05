package org.apache.spark.sql.execution.streaming.http

import org.apache.spark.internal.Logging
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.sources.DataSourceRegister
import org.apache.spark.sql.sources.StreamSinkProvider
import org.apache.spark.sql.streaming.OutputMode

import Params.map2Params

class HttpStreamSinkProvider
		extends StreamSinkProvider with DataSourceRegister {
	def createSink(
		sqlContext: SQLContext,
		parameters: Map[String, String],
		partitionColumns: Seq[String],
		outputMode: OutputMode): Sink = {
		new HttpStreamSink(parameters.getRequiredString("httpServletUrl"),
			parameters.getRequiredString("topic"),
			parameters.getInt("maxPacketSize", 10 * 1024 * 1024));
	}

	def shortName(): String = "httpStream"
}

class HttpStreamSink(httpPostURL: String, topic: String, maxPacketSize: Int)
		extends Sink with Logging {
	val producer = HttpStreamClient.connect(httpPostURL);
	val RETRY_TIMES = 5;
	val SLEEP_TIME = 100;

	override def addBatch(batchId: Long, data: DataFrame) {
		//send data to the HTTP server
		var success = false;
		var retried = 0;
		while (!success && retried < RETRY_TIMES) {
			try {
				retried += 1;
				producer.sendDataFrame(topic, batchId, data, maxPacketSize);
				success = true;
			}
			catch {
				case e: Throwable ⇒ {
					success = false;
					super.logWarning(s"failed to send", e);
					if (retried < RETRY_TIMES) {
						val sleepTime = SLEEP_TIME * retried;
						super.logWarning(s"will retry to send after ${sleepTime}ms");
						Thread.sleep(sleepTime);
					}
					else {
						throw e;
					}
				}
			}
		}
	}
}