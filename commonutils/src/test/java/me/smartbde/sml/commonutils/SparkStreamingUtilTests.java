package me.smartbde.sml.commonutils;

import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.junit.Test;

import java.io.Serializable;

public class SparkStreamingUtilTests implements Serializable {
    @Test
    public void testUdf() {
        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("SparkStreamingUtilTests.testUdf")
                .getOrCreate();

        spark.udf().register("strLen", new UDF1<String, Integer>() {
            @Override
            public Integer call(String s) throws Exception {
                return s.length();
            }
        }, DataTypes.IntegerType);

        String url = PropertiesUtil.prop("application.datasource.url");
        String user = PropertiesUtil.prop("application.datasource.username");
        String passwd = PropertiesUtil.prop("application.datasource.password");

        Dataset<Row> ds = spark.read()
                .format("jdbc")
                .option("url", url)
                .option("dbtable", "person")
                .option("user", user)
                .option("pass" + "word", passwd)
                .load();

        ds.registerTempTable("person");

        Dataset<Row> r = ds.sqlContext().sql("select strLen(name), name from person");
        r.show();

        spark.stop();
    }
}
