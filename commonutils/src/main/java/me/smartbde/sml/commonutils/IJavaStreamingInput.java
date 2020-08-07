package me.smartbde.sml.commonutils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface IJavaStreamingInput<T> extends IPlugin {
    public Dataset<Row> rdd2dataset(SparkSession spark, RDD<T> rdd);

    public DStream<T> getDStream(StreamingContext ssc);

    /**
     * Create spark javaDStream from data source, you can specify type parameter.
     * */
    public JavaDStream<T> getJavaDstream(JavaStreamingContext jssc);

    /**
     * This must be implemented to convert JavaRDD[T] to Dataset[Row] for later processing
     * */
    public Dataset<Row> javaRdd2dataset(SparkSession sparkSession, JavaRDD<T> javaRDD);
}
