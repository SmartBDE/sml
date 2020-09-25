package me.smartbde.sml.commonutils;

import me.smartbde.sml.commonutils.plugins.filter.StartLogger;
import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class SparkStreamingUtilTests implements Serializable {
    SparkSession spark = SparkSession.builder()
            .master("local")
            .appName("SparkStreamingUtilTests.testUdf")
            .getOrCreate();

    @Test
    public void testUdf() {
        spark.udf().register("s_strLen", new UDF1<String, Integer>() {
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

        Dataset<Row> r = ds.sqlContext().sql("select s_strLen(name), name from person");
        r.show();

        spark.stop();
    }

    @Test
    public void testLoader() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        IFilter filter = (IFilter) PluginUtils.newClazz("me.smartbde.sml.commonutils.plugins.filter.LogisticRegressionPredict");
        System.out.println(filter.toString());
        if (filter instanceof ISQLFilter) {
            System.out.println(filter.toString() + "is SQLFilter");
        }
    }

    @Test
    public void testLogger() {
        FilterSession sess = new FilterSession("testLogger");
        StartLogger logger = new StartLogger();
        HashMap map = new HashMap();
        map.put("table", "logs");
        map.put("user", "springtest");
        map.put("pwd", "123456");
        map.put("url", "jdbc:mysql://127.0.0.1:33061/springtest?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        logger.setConfig(map);
        logger.prepare(spark);
        logger.process(spark, null, sess);
    }
}
