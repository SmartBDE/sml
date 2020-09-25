package me.smartbde.sml.storage;

import com.mongodb.spark.MongoSpark;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;
import java.io.OutputStream;

public class MongoStorage implements IStorage {
    private SparkSession spark;
    private String repository;

    @Override
    public void init(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public void create(String schema) {
        throw new NotImplementedException();
    }

    @Override
    public void drop(String schema) {
        throw new NotImplementedException();
    }

    @Override
    public Dataset<Row> read(String name) {
        return spark.read()
                .format("com.mongodb.spark.sql.DefaultSource")
                .option("uri", repository + "." + name)
                .load();
    }

    @Override
    public void write(String name, Dataset<Row> ds, SaveMode mode) {
        ds.write()
                .format("com.mongodb.spark.sql.DefaultSource")
                .mode(mode)
                .option("uri", repository + "." + name)
                .save();
    }

    @Override
    public void delete(String name) {
        throw new NotImplementedException();
    }
}
