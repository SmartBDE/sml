package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class FileStorage implements IStorage {
    @Override
    public void init(SparkSession spark, String repository) {

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

    /**
     * 批量写入，采用truncate的模式
     *
     * @param name 目标的名字
     * @param ds   数据
     */
    @Override
    public void write(String name, Dataset<Row> ds) {

    }

    @Override
    public void delete(String name) {

    }
}
