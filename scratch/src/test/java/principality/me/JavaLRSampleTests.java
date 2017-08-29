package principality.me;

import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import principality.me.sample.JavaLRSample;

public class JavaLRSampleTests {
    @Test
    public void test() throws Exception {
        JavaLRSample lrSample = new JavaLRSample();

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("JavaLRSampleTests")
                .getOrCreate();
        lrSample.run(spark, "..\\data\\mllib\\sample_linear_regression_data.txt", 10);

        spark.stop();
    }
}
