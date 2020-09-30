package me.smartbde.sml.commonutils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public interface IStreamingInput<T> extends IPlugin {
    public interface Handler {
        void execute(Dataset<Row> df);
    }
    /**
     * This must be implemented to convert RDD[T] to Dataset[Row] for later processing
     * */
    public Dataset<Row> rdd2dataset(SparkSession spark, JavaRDD<T> rdd);

    /**
     * start should be invoked in when data is ready.
     * */
    public void start(SparkSession spark, JavaStreamingContext ssc, Handler handler);

    /**
     * Create spark dstream from data source, you can specify type parameter.
     * */
    public JavaDStream<T> getDStream(JavaStreamingContext ssc);
}
