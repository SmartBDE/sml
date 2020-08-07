package me.smartbde.sml.commonutils;

import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface IStreamingInput<T> extends IPlugin {
    public interface Handler {
        void execute(Dataset<Row> df);
    }
    /**
     * This must be implemented to convert RDD[T] to Dataset[Row] for later processing
     * */
    public Dataset<Row> rdd2dataset(SparkSession spark, RDD<T> rdd);

    /**
     * start should be invoked in when data is ready.
     * */
    public void start(SparkSession spark, StreamingContext ssc, Handler handler);

    /**
     * Create spark dstream from data source, you can specify type parameter.
     * */
    public DStream<T> getDStream(StreamingContext ssc);
}
