package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.IPlugin;
import me.smartbde.sml.scratch.JavaLogisticRegression;
import org.apache.commons.configuration2.Configuration;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 预测训练函数，属于批量操作的处理器
 */
public class LogisticRegressionTrain implements IFilter {
    private Configuration configuration;
    private JavaLogisticRegression javaLogisticRegression;

    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df) {
        return null;
    }

    /**
     * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
     *
     * @param config
     */
    @Override
    public void setConfig(Configuration config) {
        this.configuration = config;
    }

    /**
     * Get Config.
     */
    @Override
    public Configuration getConfig() {
        return configuration;
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
        return "LogisticRegressionTrain";
    }

    /**
     * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
     *
     * @param spark
     */
    @Override
    public void prepare(SparkSession spark) {
        javaLogisticRegression = new JavaLogisticRegression(10, 47);
    }
}
