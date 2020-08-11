package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 底层的存储系统可能由一个或多个Storage提供实现支持
 */
public class StorageManager implements IStorage {
    private IStorage datasetStorage;
    private IStorage modelStorage;

    public StorageManager(IStorage datasetStorage, IStorage modelStorage) {
        this.datasetStorage = datasetStorage;
        this.modelStorage = modelStorage;
    }

    @Override
    public void init(String repository) {
        datasetStorage.init(repository);
        modelStorage.init(repository);
    }

    @Override
    public void create(String schema) {
        datasetStorage.create(schema);
        modelStorage.create(schema);
    }

    @Override
    public void drop(String schema) {
        datasetStorage.create(schema);
        modelStorage.create(schema);
    }

    @Override
    public Dataset<Row> read(String name) {
        return datasetStorage.read(name);
    }

    @Override
    public void write(String name, Dataset<Row> ds) {
        datasetStorage.write(name, ds);
    }

    @Override
    public void delete(String name) {
        datasetStorage.delete(name);
    }

    @Override
    public InputStream readModel(String path) {
        return modelStorage.readModel(path);
    }

    @Override
    public void writeModel(String path, OutputStream modelStream) {
        modelStorage.writeModel(path, modelStream);
    }

    @Override
    public void deleteModel(String path) {
        modelStorage.deleteModel(path);
    }
}
