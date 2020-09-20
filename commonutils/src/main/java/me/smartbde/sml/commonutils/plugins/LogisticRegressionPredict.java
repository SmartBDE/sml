package me.smartbde.sml.commonutils.plugins;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.ISQLFilter;
import org.apache.commons.configuration2.Configuration;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.List;

/**
 * 增加udf函数predict
 *
 * 处理函数
 *   select predict(x1,x2,x3....), x1, x2, x3... from table
 *   select predict(x1,x2,x3....) from table
 */
public class LogisticRegressionPredict implements ISQLFilter {
    /**
     * Allow to register user defined UDFs
     *
     * @return empty list if there is no UDFs to be registered
     */
    @Override
    public List<Pair<String, UserDefinedFunction>> getUdfList() {
        return null;
    }

    /**
     * Allow to register user defined UDAFs
     *
     * @return empty list if there is no UDAFs to be registered
     */
    @Override
    public List<Pair<String, UserDefinedAggregateFunction>> getUdafList() {
        return null;
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

    }

    /**
     * Get Config.
     */
    @Override
    public Configuration getConfig() {
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
    public void prepare(SparkSession spark) {

    }
}
