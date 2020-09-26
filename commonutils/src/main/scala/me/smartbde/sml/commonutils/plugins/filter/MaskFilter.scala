package me.smartbde.sml.commonutils.plugins.filter

import java.lang

import javafx.util.Pair
import me.smartbde.sml.commonutils.{AbstractPlugin, IFilter, ISession}
import org.apache.spark.sql.{Dataset, Row, SparkSession}

class MaskFilter extends AbstractPlugin with IFilter {
  override def process(spark: SparkSession, df: Dataset[Row], session: ISession): Dataset[Row] = {
    ???
  }

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
