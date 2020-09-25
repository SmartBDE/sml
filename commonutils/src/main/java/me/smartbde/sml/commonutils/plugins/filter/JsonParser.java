package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * 格式输入要求：Dataset<Row>.toJSON
 */
public class JsonParser implements IFilter {
    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
        return null;
    }

    @Override
    public void setConfig(Map<String, String> config) {

    }

    @Override
    public Map<String, String> getConfig() {
        return null;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        return null;
    }

    @Override
    public String getName() {
        return "JsonParser";
    }

    @Override
    public void prepare(SparkSession spark) {

    }
}
