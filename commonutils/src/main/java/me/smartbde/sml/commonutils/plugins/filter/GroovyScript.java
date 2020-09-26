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
 * 功能说明：对Dataset<Row>中的信息，用脚本进行处理，通常这发生在Dataset<Row>.map中
 * 格式输入要求：无
 *
 * 可以热加载的脚本，以及编译后不错的速度，是groovy的一大亮点
 */
public class GroovyScript extends AbstractFilter {
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
        return "GroovyScript";
    }

    @Override
    public boolean prepare(SparkSession spark) {
        return false;
    }
}
