package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.List;

public interface IFilter extends IPlugin {

    public Dataset<Row> process(SparkSession spark, Dataset<Row> df);

    /**
     * Allow to register user defined UDFs
     * @return empty list if there is no UDFs to be registered
     * */
    public List<Pair<String, UserDefinedFunction>> getUdfList();

    /**
     * Allow to register user defined UDAFs
     * @return empty list if there is no UDAFs to be registered
     * */
    public List<Pair<String, UserDefinedAggregateFunction>> getUdafList();

}
