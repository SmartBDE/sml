package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.spark.sql.SparkSession;

public interface IPlugin {
    /**
     * Set Config.
     * */
    public void setConfig(YAMLConfiguration config);
    /**
     * Get Config.
     * */
    public YAMLConfiguration getConfig();

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
