package me.smartbde.sml.pipeline;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.Test;

import java.time.Duration;


public class SparkStreamingTests {
    private static String appName = "SparkStreamingTests";
    private static String master = "local[*]";

    @Test
    public void test() {
        SparkConf sparkConf = new SparkConf().setMaster(master).setAppName(appName);
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

    }
}
