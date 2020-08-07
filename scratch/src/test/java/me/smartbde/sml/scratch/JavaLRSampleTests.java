package me.smartbde.sml.scratch;

import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import me.smartbde.sml.scratch.JavaLRSample;

public class JavaLRSampleTests {
    @Test
    public void test() throws Exception {
        JavaLRSample lrSample = new JavaLRSample();

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("JavaLRSampleTests")
                .getOrCreate();
        double [] result = lrSample.run(spark, "..\\data\\mllib\\sample_linear_regression_data.txt", 10);
        System.out.println(result);

        spark.stop();
    }
}
