package me.smartbde.sml.commonutils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface IInput extends IPlugin {
    public Dataset<Row> getDataset(SparkSession spark);
}
