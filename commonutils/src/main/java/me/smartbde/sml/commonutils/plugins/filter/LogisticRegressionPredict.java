package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.ISQLFilter;
import me.smartbde.sml.commonutils.ISession;
import me.smartbde.sml.commonutils.UdfRegister;
import me.smartbde.sml.scratch.JavaLogisticRegression;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

import java.util.*;
import scala.Option;
import scala.Some;
import scala.collection.Seq;

/**
 * 功能说明：线性回归预测函数，利用训练好的线性回归模型对输入进行预测
 * 格式输入要求：Dataset<Row>中以算法输入所需格式对信息进行保存
 *
 * 增加udf函数LogisticRegression_predict
 *
 * 处理实现
 *   select predict(x1,x2,x3....), x1, x2, x3... from table
 *   select predict(x1,x2,x3....) from table
 */
public class LogisticRegressionPredict extends AbstractPlugin implements ISQLFilter {
    private JavaLogisticRegression javaLogisticRegression;

    /**
     * Allow to register user defined UDFs
     *
     * @return empty list if there is no UDFs to be registered
     */
    @Override
    public List<Pair<String, UserDefinedFunction>> getUdfList() {
        List<DataType> types = new ArrayList<DataType>();
        types.add(DataTypes.createArrayType(DataTypes.DoubleType));
        types.add(DataTypes.createArrayType(DataTypes.DoubleType));
        Option<Seq<DataType>> option = (Option<Seq<DataType>>) new Some<List<DataType>>(types);

        List<Pair<String, UserDefinedFunction>> list = new ArrayList<Pair<String, UserDefinedFunction>>();
        list.add(new Pair<String, UserDefinedFunction>("LogisticRegressionPredict_V1",
                new UserDefinedFunction(new UDF2<Double[], Double[], Double>() {
                    @Override
                    public Double call(Double[] doubles1, Double[] doubles2) throws Exception {
                        return javaLogisticRegression.predict(doubles1, doubles2);
                    }
                }, DataTypes.DoubleType, option)));

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
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
        return df.sqlContext().sql("");
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
                && properties.get("modelpath") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
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
    public boolean prepare(SparkSession spark) {
        if (properties == null) {
            return false;
        }
        javaLogisticRegression = new JavaLogisticRegression(
                Integer.parseInt(properties.get("dimension")),
                Integer.parseInt(properties.get("seed")));

        UdfRegister.findAndRegisterUdfs(spark, this);
        return true;
    }
}
