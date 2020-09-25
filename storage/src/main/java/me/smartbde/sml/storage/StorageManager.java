package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 底层的存储系统可能由一个或多个Storage提供实现支持，
 * 实现对数据和文件(模型)的存储支持
 */
public class StorageManager {
    private IStorage datasetStorage;
    private IModel modelStorage;

    public StorageManager(IStorage datasetStorage, IModel modelStorage) {
        this.datasetStorage = datasetStorage;
        this.modelStorage = modelStorage;
    }

    public void init(SparkSession spark) {
        datasetStorage.init(spark);
    }

    public void create(String schema) {
        datasetStorage.create(schema);
    }

    public void drop(String schema) {
        datasetStorage.drop(schema);
    }

    public Dataset<Row> read(String name) {
        return datasetStorage.read(name);
    }

    public void write(String name, Dataset<Row> ds, SaveMode mode) {
        datasetStorage.write(name, ds, mode);
    }

    public void delete(String name) {
        datasetStorage.delete(name);
    }

    public InputStream readModel(String path) {
        return modelStorage.readModel(path);
    }

    public void writeModel(String path, OutputStream modelStream) {
        modelStorage.writeModel(path, modelStream);
    }

    public void deleteModel(String path) {
        modelStorage.deleteModel(path);
    }
}
