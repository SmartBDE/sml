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
    private List<IStorage> storageList;

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
    public void writeModel(String path, OutputStream modelBinary) {

    }

    @Override
    public void deleteModel(String path) {

    }
}
