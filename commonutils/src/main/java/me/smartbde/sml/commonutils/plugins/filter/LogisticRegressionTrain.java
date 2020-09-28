package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import me.smartbde.sml.scratch.JavaLogisticRegression;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

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
        int count = Integer.parseInt(properties.get("count"));
        Dataset<String> ds = df.map(new MapFunction<Row, String>() {
            @Override
            public String call(Row row) {
                return row.getString(row.fieldIndex("text"));
            }
        }, Encoders.STRING());
        ds.show();
        double[] weights = javaLogisticRegression.run(spark, ds, count);

        List<Double> list = new ArrayList<Double>();
        for (double d : weights) {
            list.add(d);
        }

        List<Row> list2 = new ArrayList<Row>();
        list2.add(RowFactory.create(list.toString()));

        StructType schema = new StructType(new StructField[] {
                new StructField("weights", DataTypes.StringType, false, Metadata.empty())
        });

        // 把数组转换为Dataset<Row>
        Dataset<Row> r = spark.createDataFrame(list2, schema);
        return r;
    }

    /**
     * Return true and empty string if config is valid, return false and error message if config is invalid.
     */
    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("dimension") != null
                && properties.get("seed") != null
                && properties.get("count") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
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
        if (properties == null) {
            return false;
        }
        javaLogisticRegression = new JavaLogisticRegression(
                Integer.parseInt(properties.get("dimension")),
                Integer.parseInt(properties.get("seed")));
        return true;
    }
}
