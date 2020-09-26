package me.smartbde.sml.commonutils.plugins.output;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.IOutput;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * 格式输入要求：行格式
 */
public class FileOutput implements IOutput {
    @Override
    public void process(Dataset<Row> df) {

    }

    /**
     * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
     *
     * @param config
     */
    @Override
    public void setConfig(Map<String, String> config) {

    }

    /**
     * Get Config.
     */
    @Override
    public Map<String, String> getConfig() {
        return null;
    }

    /**
     * Return true and empty string if config is valid, return false and error message if config is invalid.
     */
    @Override
    public Pair<Boolean, String> checkConfig() {
        return null;
    }

    /**
     * Get Plugin Name.
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
     *
     * @param spark
     */
    @Override
    public boolean prepare(SparkSession spark) {
        return false;
    }
}
