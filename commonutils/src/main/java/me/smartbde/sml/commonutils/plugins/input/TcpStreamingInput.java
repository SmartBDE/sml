package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IStreamingInput;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * 格式输入要求：无
 */
public class TcpStreamingInput extends AbstractPlugin implements IStreamingInput {
    @Override
    public Dataset<Row> rdd2dataset(SparkSession spark, JavaRDD rdd) {
        StructType schema = DataTypes.createStructType(new StructField[] {
                new StructField("content", DataTypes.StringType, false, Metadata.empty())
        });

        // 把JavaRDD<String>转换成为JavaRDD<Row>
        JavaRDD<Row> rowRDD = rdd.map(new Function<String, Row>() {
            @Override
            public Row call(String s) throws Exception {
                return RowFactory.create(s);
            }
        });

        Dataset<Row> ds = spark.createDataFrame(rowRDD, schema);
        if (properties.get("result") != null) {
            ds.createOrReplaceTempView(properties.get("result"));
        }
        return ds;
    }

    @Override
    public void start(SparkSession spark, JavaStreamingContext ssc, Handler handler) {
        getDStream(ssc).foreachRDD(new VoidFunction2<JavaRDD<String>, Time>() {
            @Override
            public void call(JavaRDD<String> rdd, Time time) throws Exception {
                Dataset<Row> dataset = rdd2dataset(spark, rdd);
                handler.execute(dataset);
            }
        });
    }

    @Override
    public JavaDStream<String> getDStream(JavaStreamingContext ssc) {
        String host = properties.get("host");
        int port = Integer.parseInt(properties.get("port"));

        return ssc.socketTextStream(host, port);
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("duration") != null
                && properties.get("host") != null
                && properties.get("port") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public String getName() {
        return "TcpStreamingInput";
    }
}
