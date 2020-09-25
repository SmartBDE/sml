package me.smartbde.sml.storage;

import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Function1;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FileStorage implements IStorage {
    private SparkSession spark;

    @Override
    public void init(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public void create(String schema) {

    }

    @Override
    public void drop(String schema) {

    }

    @Override
    public Dataset<Row> read(String name) {
        throw new NotImplementedException();
//        Dataset<String> ds = spark.read().textFile(name);
//
//        Function1<String, Row> f = new Function1<String, Row>() {
//            @Override
//            public Row apply(String s) {
//                StructType schema = DataTypes.createStructType(new StructField[] {
//                        new StructField("content", DataTypes.StringType, false, Metadata.empty())
//                });
//                Object[] objects = new Object[1];
//                objects[0] = s;
//                return new GenericRowWithSchema(objects, schema);
//            }
//        };
//
//        Dataset<Row> df = ds.map((Function1<String, Row>)f, Encoders.STRING());
//        return df;
    }

    /**
     * 批量写入，采用append的模式
     *
     * @param name 目标的名字
     * @param ds   数据
     */
    @Override
    public void write(String name, Dataset<Row> ds, SaveMode mode) {
//        Dataset<String> df = ds.map((Function1<Row, String>) row -> row.mkString(), Encoders.STRING());
//        df.write().text(name);
        //ds.write().text(name);
    }

    @Override
    public void delete(String name) {

    }
}
