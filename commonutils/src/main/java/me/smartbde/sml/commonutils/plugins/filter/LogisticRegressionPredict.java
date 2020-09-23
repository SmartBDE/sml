package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.ISQLFilter;
import me.smartbde.sml.scratch.JavaLogisticRegression;
import org.apache.commons.configuration2.Configuration;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.*;

/**
 * 增加udf函数predict
 *
 * 处理函数
 *   select predict(x1,x2,x3....), x1, x2, x3... from table
 *   select predict(x1,x2,x3....) from table
 */
public class LogisticRegressionPredict implements ISQLFilter {
    private Configuration configuration;
    private JavaLogisticRegression javaLogisticRegression;
    private int count;

    /**
     * Allow to register user defined UDFs
     *
     * @return empty list if there is no UDFs to be registered
     */
    @Override
    public List<Pair<String, UserDefinedFunction>> getUdfList() {
        List<Pair<String, UserDefinedFunction>> list = new ArrayList<Pair<String, UserDefinedFunction>>();
        // 这里增加predict函数
        return list;
    }

    /**
     * Allow to register user defined UDAFs
     *
     * @return empty list if there is no UDAFs to be registered
     */
    @Override
    public List<Pair<String, UserDefinedAggregateFunction>> getUdafList() {
        return new ArrayList<Pair<String, UserDefinedAggregateFunction>>();
    }

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
        configuration = config;
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
        return "LogisticRegressionPredict";
    }

    /**
     * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
     *
     * @param spark
     */
    @Override
    public void prepare(SparkSession spark) {
        if (configuration == null) {
            throw new RuntimeException("configuration not set");
        }
        javaLogisticRegression = new JavaLogisticRegression(10, 47);
        count = Integer.parseInt(configuration.getProperty("").toString());
    }
}
