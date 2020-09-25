package me.smartbde.sml.commonutils.plugins.output;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.IOutput;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * 格式输入要求：无
 */
public class JdbcOutput implements IOutput {
    @Override
    public void process(Dataset<Row> df) {

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
        return null;
    }

    @Override
    public void prepare(SparkSession spark) {

    }
}
