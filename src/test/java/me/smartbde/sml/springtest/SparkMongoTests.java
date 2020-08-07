package me.smartbde.sml.springtest;

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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@RunWith(SpringRunner.class)
public class SparkMongoTests {
    @Test
    public void test() throws Exception {

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("SparkMongoTests")
                .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/stdin.logstash")
                .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/stdin.logstash")
                .getOrCreate();

        try (JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext())) {

            // Create a custom WriteConfig
            Map<String, String> writeOverrides = new HashMap<>();
            writeOverrides.put("collection", "logstash");
            writeOverrides.put("writeConcern.w", "majority");
            WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);

            JavaRDD<Document> documents = jsc
                    .parallelize(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
                    .map(new ParseDocument());

    /*Start Example: Save data from RDD to MongoDB*****************/
            //MongoSpark.save(documents, writeConfig);
            //MongoSpark.save(documents);

            JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
            JavaMongoRDD<Document> aggregatedRdd = rdd.withPipeline(
                    singletonList(Document.parse("{ $match: { test : { $gt : 5 } } }")));

            System.out.println(aggregatedRdd.count());
            if (aggregatedRdd.count() > 0) {
                System.out.println(aggregatedRdd.first().toJson());
            }

            Dataset<Row> implicitDS = MongoSpark.load(jsc).toDF();
            implicitDS.printSchema();
            implicitDS.show();

//            Dataset<Character> explicitDS = MongoSpark.load(jsc).toDS(Character.class);
//            explicitDS.printSchema();
//            explicitDS.show();

            implicitDS.createOrReplaceTempView("logstash");
            Dataset<Row> centenarians = spark.sql("SELECT test FROM logstash WHERE test >= 8");
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
}
