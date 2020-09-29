package me.smartbde.sml.commonutils.plugins.output;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IOutput;
import me.smartbde.sml.storage.JdbcStorage;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

/**
 * 格式输入要求：无
 */
public class JdbcOutput extends AbstractPlugin implements IOutput {
    protected JdbcStorage jdbcStorage = null;

    @Override
    public void process(Dataset<Row> df) {
        jdbcStorage.write(properties.get("table"), df, SaveMode.Append);
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
        return "JdbcOutput";
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
