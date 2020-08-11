package me.smartbde.sml.scratch;

import org.apache.spark.sql.SparkSession;
import org.junit.Test;

public class JavaLogisticRegressionTests {
    @Test
    public void test() throws Exception {
        JavaLogisticRegression lrSample = new JavaLogisticRegression();

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("JavaLogisticRegressionTests")
                .getOrCreate();
        double [] result = lrSample.run(spark, "..\\data\\mllib\\sample_linear_regression_data.txt", 10);
        System.out.println(result);

        spark.stop();
    }
}
