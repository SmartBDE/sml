package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import kafka.serializer.StringDecoder;
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
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.*;

/**
 * 采用direct模式从kafka读取数据，由partition决定并行度(读取)，解决并行读的性能问题
 */
public class KafkaStreamingInput extends AbstractPlugin implements IStreamingInput {

    /**
     * Return true and empty string if config is valid, return false and error message if config is invalid.
     */
    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("metadata.broker.list") != null
                && properties.get("group.id") != null
                && properties.get("auto.offset.reset") != null
                && properties.get("topics") != null
                && properties.get("duration") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    /**
     * Get Plugin Name.
     */
    @Override
    public String getName() {
        return "KafkaStreamingInput";
    }

    /**
     * This must be implemented to convert RDD[T] to Dataset[Row] for later processing
     *
     * @param spark
     * @param rdd
     */
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

    /**
     * start should be invoked in when data is ready.
     *
     * @param spark
     * @param ssc
     * @param handler
     */
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

    /**
     * Create spark dstream from data source, you can specify type parameter.
     *
     * @param ssc
     */
    @Override
    public JavaDStream getDStream(JavaStreamingContext ssc) {
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", properties.get("metadata.broker.list"));
        kafkaParams.put("group.id", properties.get("group.id"));
        kafkaParams.put("auto.offset.reset", properties.get("auto.offset.reset"));

        Set<String> topics = new HashSet<>(Arrays.asList(properties.get("topics").split(",")));

        JavaPairInputDStream<String, String> input = KafkaUtils.createDirectStream(
                ssc,
                String.class,
                String.class,
                StringDecoder.class,
                StringDecoder.class,
                kafkaParams, topics);

        JavaDStream<String> valueDStream = input.map(new Function<Tuple2<String, String>, String>() {
            public String call(Tuple2<String, String> v1) throws Exception {
                return v1._2();
            }
        });

        return valueDStream;
    }
}
