package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.List;

/**
 * 所有的处理都在这里实现，通过SQL的方式进行(可配置)，输入Dataset<Row>，输出Dataset<Row>
 *
 * 有两类filter，第一类是sql形式，通过udf/udaf实现扩展，第二类是自定义函数，直接对输入输出进行处理
 * 因为SQL的实现能力有限，所以通过udf/udaf的方式进行扩展，从而达到整合算法的能力
 */
public interface IFilter extends IPlugin {

    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session);
}
