package me.smartbde.sml.commonutils.plugins.output

import java.lang
import javafx.util
import me.smartbde.sml.commonutils.plugins.output.kafka.KafkaSink
import me.smartbde.sml.commonutils.{AbstractPlugin, IOutput}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import java.util.Properties

/**
 * 功能说明：Kafka的批量输出模式
 * 格式输入要求：无
 */
class KafkaOutput extends AbstractPlugin with IOutput {
  var kafkaProducer: Broadcast[KafkaSink[String, String]] = _

  override def prepare (spark: SparkSession): Boolean = {
    if (super.prepare(spark)) { // 这里创建并广播Kafka
      // 广播KafkaSink
      kafkaProducer = {
        val kafkaProducerConfig = {
          val p = new Properties()
          p.setProperty("bootstrap.servers", properties.get("bootstrap.servers"))
          p.setProperty("key.serializer", classOf[StringSerializer].getName)
          p.setProperty("value.serializer", classOf[StringSerializer].getName)
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
          //kafkaProducer.value.send(Conf.outTopics, record._1.toString, record._2)
          // do something else
        })
      }
    })
  }

  /**
   * Return true and empty string if config is valid, return false and error message if config is invalid.
   */
  override def checkConfig(): util.Pair[lang.Boolean, String] = ???

  /**
   * Get Plugin Name.
   */
  override def getName = "KafkaOutput"
}

