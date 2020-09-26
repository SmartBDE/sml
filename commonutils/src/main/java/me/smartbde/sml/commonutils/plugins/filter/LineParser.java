package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 功能说明：将用户输入的行文本解析为带格式的Dataset<Row>
 * 格式输入要求：以行为格式输入的文本信息，以字符串格式保存在Row中，可以用分隔符切割
 *
 * 用户的输入是多行的文本，系统会需要从行文本中，按特定的格式，对信息进行提取，
 * 提取的信息保存为Dataset<Row>格式，从而实现对信息的SQL操作能力
 */
public class LineParser extends AbstractPlugin {
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
        return null;
    }

    @Override
    public boolean prepare(SparkSession spark) {
        return false;
    }
}
