package me.smartbde.sml.commonutils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.DataStreamWriter;

/**
 * 专用于structured streaming的Input
 */
public interface IStructuredStreamingInput extends IPlugin {

    public Dataset<Row> getDataset(SparkSession spark);
}
