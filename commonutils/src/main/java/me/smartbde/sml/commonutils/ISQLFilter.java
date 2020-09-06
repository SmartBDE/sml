package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.List;

/**
 * 提供udf列表，并允许注册
 */
public interface ISQLFilter extends IFilter {
    /**
     * Allow to register user defined UDFs
     *
     * @return empty list if there is no UDFs to be registered
     */
    public List<Pair<String, UserDefinedFunction>> getUdfList();

    /**
     * Allow to register user defined UDAFs
     *
     * @return empty list if there is no UDAFs to be registered
     */
    public List<Pair<String, UserDefinedAggregateFunction>> getUdafList();

}
