package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import me.smartbde.sml.scratch.JavaLogisticRegression;
import me.smartbde.sml.storage.JdbcStorage;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 功能说明：将用户输入的行文本解析为带格式的Dataset<Row>
 * 格式输入要求：以行为格式输入的文本信息，以字符串格式保存在Row中，可以用分隔符切割
 *
 * 用户的输入是多行的文本，系统会需要从行文本中，按特定的格式，对信息进行提取，
 * 提取的信息保存为Dataset<Row>格式，从而实现对信息的SQL操作能力
 */
public class LineParser extends AbstractPlugin implements IFilter {
    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
        JavaRDD<Row> rdd = df.toJavaRDD();

        JavaRDD<Row> rdd2 = rdd.map(new Function<Row, Row>() {
            @Override
            public Row call(Row row) throws Exception {
                final Pattern Delimiter = Pattern.compile(properties.get("delimiter"));
                String line = row.getString(0);
                String tok[] = Delimiter.split(line);
                return RowFactory.create(tok);
            }
        });

        // schema的格式：fieldName:type fieldName:type fieldName:type ...
        String schemaFormat = properties.get("schema");
        final Pattern Space = Pattern.compile(" ");
        String fields[] = Space.split(schemaFormat);

        StructField[] structFields = new StructField[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String[] splits = fields[i].split(":");
            String field = splits[0];
            DataType type;
            switch (splits[1]) {
                case "int":
                    type = DataTypes.IntegerType;
                    break;
                case "string":
                    type = DataTypes.StringType;
                    break;
                case "binary":
                    type = DataTypes.BinaryType;
                    break;
                case "bool":
                    type = DataTypes.BooleanType;
                    break;
                default:
                    type = DataTypes.NullType;
            }

            structFields[i] = new StructField(field, type, false, Metadata.empty());
        }

        StructType schema = DataTypes.createStructType(structFields);
        return spark.createDataFrame(rdd2, schema);
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("delimiter") != null
                && properties.get("schema") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public String getName() {
        return "LineParser";
    }
}
