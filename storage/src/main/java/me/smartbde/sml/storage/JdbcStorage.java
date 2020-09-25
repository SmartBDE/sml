package me.smartbde.sml.storage;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;
import java.io.OutputStream;

public class JdbcStorage implements IStorage {
    private SparkSession spark;
    private String repoUrl;
    private String repoUser;
    private String repoPassword;

    public JdbcStorage(String url, String user, String password) {
        this.repoUrl = url;
        this.repoUser = user;
        this.repoPassword = password;
    }

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
                .format("jdbc")
                .option("url", repoUrl)
                .option("dbtable", name)
                .option("user", repoUser)
                .option("pass" + "word", repoPassword)
                .load();
    }

    @Override
    public void write(String name, Dataset<Row> ds, SaveMode mode) {
        ds.write()
                .format("jdbc")
                .mode(mode)
                .option("url", repoUrl)
                .option("dbtable", name)
                .option("user", repoUser)
                .option("password", repoPassword)
                .save();
    }

    @Override
    public void delete(String name) {
        throw new NotImplementedException();
    }
}
