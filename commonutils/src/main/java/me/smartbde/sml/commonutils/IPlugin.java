package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import org.apache.commons.configuration2.Configuration;
import org.apache.spark.sql.SparkSession;

public interface IPlugin {
    /**
     * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
     * */
    public void setConfig(Configuration config);
    /**
     * Get Config.
     * */
    public Configuration getConfig();

    /**
     *  Return true and empty string if config is valid, return false and error message if config is invalid.
     */
    public Pair<Boolean, String> checkConfig();

    /**
     * Get Plugin Name.
     */
    public String getName();

    /**
     * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
     */
    public void prepare(SparkSession spark);
}
