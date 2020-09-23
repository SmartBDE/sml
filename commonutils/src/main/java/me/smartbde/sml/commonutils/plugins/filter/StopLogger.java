package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.IFilter;
import org.apache.commons.configuration2.Configuration;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class StopLogger implements IFilter {
    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df) {
        return null;
    }

    @Override
    public void setConfig(Configuration config) {

    }

    @Override
    public Configuration getConfig() {
        return null;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void prepare(SparkSession spark) {

    }
}
