package me.smartbde.sml.commonutils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * 批量的output
 */
public interface IOutput extends IPlugin {
    public void process(Dataset<Row> df);
}
