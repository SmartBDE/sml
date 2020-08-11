package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.InputStream;
import java.io.OutputStream;

public class MongoStorage implements IStorage {
    @Override
    public void init(String repository) {

    }

    @Override
    public void create(String schema) {

    }

    @Override
    public void drop(String schema) {

    }

    @Override
    public Dataset<Row> read(String name) {
        return null;
    }

    @Override
    public void write(String name, Dataset<Row> ds) {

    }

    @Override
    public void delete(String name) {

    }

    @Override
    public InputStream readModel(String path) {
        return null;
    }

    @Override
    public void writeModel(String path, OutputStream modelStream) {

    }

    @Override
    public void deleteModel(String path) {

    }
}
