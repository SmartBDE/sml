package me.smartbde.sml.commonutils.plugins.filter

import java.lang

import javafx.util
import me.smartbde.sml.commonutils.IFilter
import org.apache.commons.configuration2.Configuration
import org.apache.spark.sql.{Dataset, Row, SparkSession}

class MaskFilter extends IFilter {
  override def process(spark: SparkSession, df: Dataset[Row]): Dataset[Row] = ???

  /**
   * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
   **/
  override def setConfig(config: Configuration): Unit = ???

  /**
   * Get Config.
   **/
  override def getConfig: Configuration = ???

  /**
   * Return true and empty string if config is valid, return false and error message if config is invalid.
   */
  override def checkConfig(): util.Pair[lang.Boolean, String] = ???

  /**
   * Get Plugin Name.
   */
  override def getName: String = ???

  /**
   * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
   */
  override def prepare(spark: SparkSession): Unit = ???
}