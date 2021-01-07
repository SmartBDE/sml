package me.smartbde.sml.commonutils;

import me.smartbde.sml.commonutils.plugins.filter.LogisticRegressionPredict;
import me.smartbde.sml.commonutils.plugins.filter.StartLogger;
import me.smartbde.sml.commonutils.plugins.filter.StopLogger;
import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;

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

        ds.createOrReplaceTempView("person");

        Dataset<Row> r = ds.sqlContext().sql("select s_strLen(name), name from person");
        r.show();

        spark.stop();
    }

    @Test
    public void testLoader() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        IFilter filter = (IFilter) PluginUtils.newClazz("me.smartbde.sml.commonutils.plugins.filter.LogisticRegressionPredict");
        System.out.println(filter.toString());
        if (filter instanceof ISQLUdfFilter) {
            System.out.println(filter.toString() + "is SQLFilter");
        }
    }

    @Test
    public void testLogger() {
        FilterSession sess = new FilterSession("testLogger");
        HashMap map = new HashMap();
        map.put("table", "logs");
        map.put("user", "springtest");
        map.put("pwd", "123456");
        map.put("url", "jdbc:mysql://127.0.0.1:33061/springtest?useUnicode=true&characterEncoding=utf-8&useSSL=false");

        StartLogger startLogger = new StartLogger();
        startLogger.setConfig(map);
        startLogger.prepare(spark);
        startLogger.process(spark, null, sess);

        StopLogger stopLogger = new StopLogger();
        stopLogger.setConfig(map);
        stopLogger.prepare(spark);
        stopLogger.process(spark, null, sess);
    }

    @Test
    public void testLRPredict() {
        FilterSession sess = new FilterSession("testLRPredict");
        HashMap map = new HashMap();
        map.put("dimension", "10");
        map.put("seed", "47");
        map.put("modelpath", "../data/model/LogisticRegressionV1.model");

        LogisticRegressionPredict predict = new LogisticRegressionPredict();
        predict.setConfig(map);

        if (predict.prepare(spark)) {
            predict.process(spark, null, sess);
        }
    }
}
