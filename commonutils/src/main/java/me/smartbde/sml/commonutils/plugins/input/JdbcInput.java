package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IInput;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * 格式输入要求：无
 */
public class JdbcInput extends AbstractPlugin implements IInput {
    @Override
    public Dataset<Row> getDataset(SparkSession spark) {
        return null;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean prepare(SparkSession spark) {
        return false;
    }
}
