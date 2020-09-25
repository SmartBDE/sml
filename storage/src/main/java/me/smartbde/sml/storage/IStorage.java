package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

/**
 * 这是基于spark实现的存储系统，提供了数据访问及写入的抽象接口，
 * 由spark底层实现支持基于分布式集群的读写，支持多数据源管理
 */
public interface IStorage {
    public void init(SparkSession spark);

    public void create(String schema);

    public void drop(String schema);

    public Dataset<Row> read(String name);

    /**
     * @param name 目标的名字
     * @param ds 数据
     */
    public void write(String name, Dataset<Row> ds, SaveMode mode);

    public void delete(String name);


}
