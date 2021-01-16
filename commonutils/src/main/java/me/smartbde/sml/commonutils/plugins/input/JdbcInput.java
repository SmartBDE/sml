package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IInput;
import me.smartbde.sml.storage.JdbcStorage;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * 格式输入要求：无
 */
public class JdbcInput extends AbstractPlugin implements IInput {
    protected JdbcStorage jdbcStorage = null;

    @Override
    public Dataset<Row> getDataset(SparkSession spark) {
        jdbcStorage.init(spark);

        Dataset<Row> ds = jdbcStorage.read(properties.get("table"));
        if (properties.get("result") != null) {
            ds.createOrReplaceTempView(properties.get("result"));
        }
        return ds;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("url") != null
                && properties.get("user") != null
                && properties.get("pwd") != null
                && properties.get("table") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public String getName() {
        return "JdbcInput";
    }

    @Override
    public boolean prepare(SparkSession spark) {
        if (properties != null && checkConfig().getKey()) {
            jdbcStorage = new JdbcStorage(properties.get("url"), properties.get("user"), properties.get("pwd"));
            return true;
        }
        return false;
    }
}
