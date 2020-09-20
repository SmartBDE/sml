package me.smartbde.sml.scratch;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

public class JavaLogisticRegressionTests {
    @Test
    public void test() throws Exception {
        JavaLogisticRegression lrSample = new JavaLogisticRegression();

//        System.out.println(System.getProperty("os.name"));
//        System.out.println(System.getProperty("file.separator"));
        String separator = System.getProperty("file.separator");

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("JavaLogisticRegressionTests")
                .getOrCreate();

        double[] result;
        Dataset<String> rawLines;
        if (separator.equals("/")) {
            rawLines = lrSample.readFile(spark, "../data/mllib/sample_linear_regression_data.txt");

        } else {
            rawLines = lrSample.readFile(spark, "..\\data\\mllib\\sample_linear_regression_data.txt");
        }
        result = lrSample.run(spark, rawLines, 500);
        System.out.println(result);

        spark.stop();
    }
}
