package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractFilter;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * 功能说明：对输入执行SQL操作并返回操作后的结果
 * 格式输入要求：无
 *
 * 这可能是最常用的插件，通过SQL实现了全面的数据操作，如格式转化
 */
public class SqlFilter extends AbstractFilter {
    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
        return null;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        return null;
    }

    @Override
    public String getName() {
        return "SqlFilter";
    }

    @Override
    public boolean prepare(SparkSession spark) {
        return false;
    }
}
