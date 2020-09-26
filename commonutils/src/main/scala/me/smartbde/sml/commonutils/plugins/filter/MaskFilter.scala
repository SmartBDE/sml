package me.smartbde.sml.commonutils.plugins.filter

import java.{lang, util}
import javafx.util.Pair

import me.smartbde.sml.commonutils.{IFilter, ISession}
import org.apache.spark.sql.{Dataset, Row, SparkSession}

class MaskFilter extends IFilter {
  var config : util.Map[String, String] = _

  override def process(spark: SparkSession, df: Dataset[Row], session: ISession): Dataset[Row] = {
    ???
  }

  /**
   * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
   **/
  override def setConfig(config: util.Map[String, String]): Unit = {
    this.config = config
  }

  /**
   * Get Config.
   **/
  override def getConfig: util.Map[String, String] = config

  /**
   * Return true and empty string if config is valid, return false and error message if config is invalid.
   */
  override def checkConfig(): Pair[lang.Boolean, String] = ???

  /**
   * Get Plugin Name.
   */
  override def getName: String = "MaskFilter"

  /**
   * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
   */
  override def prepare(spark: SparkSession): Boolean = ???
}
