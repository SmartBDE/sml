package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import me.smartbde.sml.storage.JdbcStorage;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Logger extends AbstractPlugin implements IFilter {
    protected JdbcStorage jdbcStorage = null;
    protected String loggerFlag = null;

    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {
        Row row = RowFactory.create(session.getJobName(),
                session.getId(), loggerFlag, new Timestamp(new java.util.Date().getTime()));
        List<Row> list = new ArrayList<>();
        list.add(row);
        StructType schema = DataTypes.createStructType(new StructField[] {
                new StructField("jobname", DataTypes.StringType, false, Metadata.empty()),
                new StructField("sessionid", DataTypes.StringType, false, Metadata.empty()),
                new StructField("act", DataTypes.StringType, false, Metadata.empty()),
                new StructField("acttime", DataTypes.TimestampType, false, Metadata.empty())
        });
        Dataset<Row> ds = spark.createDataFrame(list, schema);
        jdbcStorage.write(properties.get("table"), ds, SaveMode.Append);
        return df;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("url") != null
                && properties.get("user") != null
                && properties.get("pwd") != null
                && properties.get("table") != null
                && properties.get("flag") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public String getName() {
        return "Logger";
    }

    @Override
    public boolean prepare(SparkSession spark) {
        if (properties != null && checkConfig().getKey()) {
            jdbcStorage = new JdbcStorage(properties.get("url"), properties.get("user"), properties.get("pwd"));
            loggerFlag = properties.get("flag");
            return true;
        }
        return false;
    }
}
