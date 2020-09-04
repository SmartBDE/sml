package me.smartbde.sml.storage;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.Collections.singletonList;

@RunWith(SpringRunner.class)
//@TestPropertySource("classpath:application.properties")
public class SparkMongoTests {
//    String mongoUrl = PropertiesUtil.prop("spring.data.mongodb.uri");
    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Test
    public void test() throws Exception {
        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("SparkMongoTests")
                .config("spark.mongodb.input.uri", mongoUrl + ".logstash")
                .config("spark.mongodb.output.uri", mongoUrl + ".logstash")
//                .config("spark.jars.packages", "org.mongodb.spark:mongo-spark-connector_2.11:2.4.2")
                .getOrCreate();

        try (JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext())) {

            // Create a custom WriteConfig
//            Map<String, String> writeOverrides = new HashMap<>();
//            writeOverrides.put("collection", "logstash");
//            writeOverrides.put("writeConcern.w", "majority");
//            WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);
//
//            JavaRDD<Document> documents = jsc
//                    .parallelize(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
//                    .map(new ParseDocument());

    /*Start Example: Save data from RDD to MongoDB*****************/
//            MongoSpark.save(documents, writeConfig);
//            MongoSpark.save(documents);

            JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
//            JavaMongoRDD<Document> aggregatedRdd = rdd.withPipeline(
//                    singletonList(Document.parse("{ $match: { test : { $gt : 5 } } }")));

            System.out.println(rdd.count());
//            System.out.println(aggregatedRdd.count());
//            if (aggregatedRdd.count() > 0) {
//                System.out.println(aggregatedRdd.first().toJson());
//            }

            Dataset<Row> implicitDS = MongoSpark.load(jsc).toDF();
            implicitDS.printSchema();
            implicitDS.show();

//            Dataset<Person> explicitDS = MongoSpark.load(jsc).toDS(Person.class);
//            explicitDS.printSchema();
//            explicitDS.show();

            implicitDS.createOrReplaceTempView("logstash");
            Dataset<Row> centenarians = spark.sql("SELECT * FROM logstash");
//            Dataset<Row> centenarians = spark.sql("SELECT test FROM logstash WHERE test >= 8");

            centenarians.show();
        }

        spark.stop();
    }

    static class ParseDocument implements Function<Integer, Document> {
        @Override
        public Document call(final Integer i) throws Exception {
            return Document.parse("{test: " + i + "}");
        }
    }

    @Configuration
    @PropertySource("classpath:application.properties")
    static class PropertiesWithJavaConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer
        propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    private String propUtil(String key) {
        Properties props = new Properties();
        try {
            Resource resource = new ClassPathResource("application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            return props.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPath() {
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            return path.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
