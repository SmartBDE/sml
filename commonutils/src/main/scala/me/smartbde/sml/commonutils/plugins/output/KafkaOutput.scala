package me.smartbde.sml.commonutils.plugins.output

import java.lang
import javafx.util
import javafx.util.Pair
import me.smartbde.sml.commonutils.plugins.output.kafka.KafkaSink
import me.smartbde.sml.commonutils.{AbstractPlugin, IOutput}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import java.util.Properties

/**
 * 功能说明：Kafka的批量输出模式
 * 格式输入要求：无
 */
class KafkaOutput extends AbstractPlugin with IOutput with Serializable {
  var topic: String = _
  var kafkaProducer: Broadcast[KafkaSink[String, String]] = _

  override def prepare (spark: SparkSession): Boolean = {
    if (super.prepare(spark)) {
      topic = properties.get("topics")
      // 广播KafkaSink
      kafkaProducer = {
        val kafkaProducerConfig = {
          val p = new Properties()
          p.setProperty("bootstrap.servers", properties.get("bootstrap.servers"))
          p.setProperty("key.serializer", properties.get("key.serializer"))
          p.setProperty("value.serializer", properties.get("value.serializer"))
          p
        }
        spark.sparkContext.broadcast(KafkaSink[String, String](kafkaProducerConfig))
      }

      return true
    }
    false
  }

  override def process(df: Dataset[Row]): Unit = {
    df.foreachPartition(rdd => {
      if (!rdd.isEmpty) {
        rdd.foreach(record => {
          val s = record.getString(0)
          if (s.nonEmpty)
              kafkaProducer.value.send(topic, s)
          // do something else
        })
      }
    })
  }

  /**
   * Return true and empty string if config is valid, return false and error message if config is invalid.
   */
  override def checkConfig(): util.Pair[lang.Boolean, String] = {
    if (properties == null)
      return new Pair[lang.Boolean, String](false, "missing config")

    if (properties.get("bootstrap.servers") != null
      && properties.get("key.serializer") != null
      && properties.get("value.serializer") != null
      && properties.get("topics") != null)
      return new Pair[lang.Boolean, String](true, "")

    return new Pair[lang.Boolean, String](false, "missing config")
  }

  /**
   * Get Plugin Name.
   */
  override def getName = "KafkaOutput"
}

