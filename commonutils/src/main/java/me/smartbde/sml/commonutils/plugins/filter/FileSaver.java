package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractFilter;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 功能说明：对保存在session中内存对象进行持久化，并保存到文件系统中
 * 格式输入要求：无
 *
 * 这是一个特殊的插件，用于实现一些特别的保存操作
 */
public class FileSaver extends AbstractFilter {
    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
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
