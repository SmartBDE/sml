package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.IInput;
import org.apache.commons.configuration2.Configuration;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public class JdbcInput implements IInput {
    @Override
    public Dataset<Row> getDataset(SparkSession spark) {
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
        return null;
    }

    @Override
    public void prepare(SparkSession spark) {

    }
}
