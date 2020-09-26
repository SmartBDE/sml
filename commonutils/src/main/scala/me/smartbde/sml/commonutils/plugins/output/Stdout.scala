package me.smartbde.sml.commonutils.plugins.output

import java.{lang, util}
import javafx.util.Pair

import me.smartbde.sml.commonutils.IOutput
import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
 * 格式输入要求：行格式
 */
class Stdout extends IOutput {
  override def process(df: Dataset[Row]): Unit = ???

  /**
   * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
   **/
  override def setConfig(config: util.Map[String, String]): Unit = ???

  /**
   * Get Config.
   **/
  override def getConfig: util.Map[String, String] = ???

  /**
   * Return true and empty string if config is valid, return false and error message if config is invalid.
   */
  override def checkConfig(): Pair[lang.Boolean, String] = ???

  /**
   * Get Plugin Name.
   */
  override def getName = "Stdout"

  /**
   * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
   */
  override def prepare(spark: SparkSession): Boolean = ???
}
