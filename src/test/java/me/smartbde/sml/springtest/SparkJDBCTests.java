package me.smartbde.sml.springtest;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class SparkJDBCTests {
    @Value("${spring.datasource.url}")
    private String mysqlUrl;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String passwd;

    private static String appName = "SparkJDBCTests";
    private static String master = "local[*]";

    @Test
    public void test() throws AnalysisException {
        SparkSession spark = SparkSession
                .builder()
                .appName(appName)
                .master(master)
                .getOrCreate();

        Dataset<Row> df = spark.read()
                .format("jdbc")
                .option("url", mysqlUrl)
                .option("user", user)
                .option("password", passwd)
                .option("dbtable", "person")
                .load();

        df.printSchema();

        df.createTempView("person");
        spark.sql("select * from person").show();
    }
}

