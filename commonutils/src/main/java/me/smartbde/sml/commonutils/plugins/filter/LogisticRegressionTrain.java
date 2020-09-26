package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import me.smartbde.sml.scratch.JavaLogisticRegression;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 功能说明：线性回归预测模型训练函数，属于批量操作的处理器
 * 格式输入要求：训练算法所需格式
 *
 * 训练的结果，可以由本插件直接持久化到文件系统中
 */
public class LogisticRegressionTrain extends AbstractPlugin implements IFilter {
    private JavaLogisticRegression javaLogisticRegression;

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
        return "LogisticRegressionTrain";
    }

    /**
     * Prepare before running, do things like set config default value, add broadcast variable, accumulator.
     *
     * @param spark
     */
    @Override
    public boolean prepare(SparkSession spark) {
        javaLogisticRegression = new JavaLogisticRegression(10, 47);
        return true;
    }
}
