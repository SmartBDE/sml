package me.smartbde.sml.commonutils.plugins.filter;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 功能说明：对Dataset<Row>中的信息，用脚本进行处理，通常这发生在Dataset<Row>.map中
 * 格式输入要求：无
 *
 * 可以灵活加载的脚本，以及编译后不错的速度，是groovy的一大亮点
 */
public class GroovyScript extends AbstractPlugin implements IFilter, Serializable {
    private Logger logger = LoggerFactory.getLogger(GroovyScript.class);
    private Broadcast<GroovyObject> broadcastGroovyObject;


    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
        JavaRDD<Row> rdd = df.toJavaRDD();

        String schemaFormat = properties.get("inputSchema");
        StructField[] structFields = parseSchema(schemaFormat);
        int length = structFields.length;
        String funcName = properties.get("func");

        JavaRDD<Row> rdd2 = rdd.map(new Function<Row, Row>() {
            @Override
            public Row call(Row row) throws Exception {
                GroovyObject groovyObject = broadcastGroovyObject.value();
                // 解析row，然后将参数传给script
                if (length == 1) {
                    Object args = row.get(0);
                    Object object = groovyObject.invokeMethod(funcName, args);
                    return RowFactory.create(object);
                } else {
                    List<Object> args = new ArrayList<>();
                    for (int i = 0; i < length; i++) {
                        args.add(row.get(i));
                    }
                    List<Object> objects = (List<Object>) groovyObject.invokeMethod(funcName, args);
                    return RowFactory.create(objects.toArray());
                }
            }
        });

        String schemaFormat2 = properties.get("outputSchema");
        StructField[] structFields2 = parseSchema(schemaFormat2);
        StructType schema = DataTypes.createStructType(structFields2);

        return spark.createDataFrame(rdd2, schema);
    }

    // schema的格式：fieldName:type fieldName:type fieldName:type ...
    private StructField[] parseSchema(String schemaFormat) {
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
        return structFields;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("script") != null
                && properties.get("func") != null
                && properties.get("inputSchema") != null
                && properties.get("outputSchema") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public String getName() {
        return "GroovyScript";
    }

    @Override
    public boolean prepare(SparkSession spark) {
        if (properties != null && checkConfig().getKey()) {
            try {
//                String separator = System.getProperty("file.separator");
//                String path = properties.get("script");
//                int index = path.lastIndexOf(separator);
//                String root = path.substring(0, index);
//                String file = path.substring(index+1, path.length());
                ClassLoader cl = GroovyScript.class.getClassLoader();
                GroovyClassLoader groovyClassLoader = new GroovyClassLoader(cl);
                try {
                    FileInputStream inputStream = new FileInputStream(properties.get("script"));
                    int length = inputStream.available();
                    byte[] bytes = new byte[length];
                    inputStream.read(bytes);
                    inputStream.close();
                    // 文件编码必须为utf-8
                    String content = new String(bytes, StandardCharsets.UTF_8);
                    // 装载并编译
                    Class groovyClass = groovyClassLoader.parseClass(content);
                    GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
                    JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());
                    broadcastGroovyObject = javaSparkContext.broadcast(groovyObject);
                } catch (IOException e) {
                    logger.warn(e.toString());
                } catch (Exception e) {
                    logger.info(e.toString());
                }
            } catch (Exception e) {
                logger.info(e.toString());
            }
            return true;
        }
        return false;
    }
}
