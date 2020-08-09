package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface IStorage {
    public void init(String repository);

    public void create(String path);

    public void drop(String path);

    public Dataset<Row> read(String name);

    /**
     * 批量写入，采用truncate的模式
     *
     * @param name 目标的名字
     * @param ds 数据
     */
    public void write(String name, Dataset<Row> ds);

    public void delete(String name);
}
