package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 功能说明：将用户输入的json转换为Dataset<Row>
 * 格式输入要求：Dataset<Row>，json信息以单个文本字段的方式，保存在Row中
 *
 * 常见的一种场景是，用户的输入以json的格式批量传输，系统会需要从json文本中，
 * 抽取出想要的信息，并保存为Dataset<Row>格式，从而实现对信息的SQL操作能力
 */
public class JsonParser extends AbstractPlugin implements IFilter {
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
        return "JsonParser";
    }

    @Override
    public boolean prepare(SparkSession spark) {
        return false;
    }
}
