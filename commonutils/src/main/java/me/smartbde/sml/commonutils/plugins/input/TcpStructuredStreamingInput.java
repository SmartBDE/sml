package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IStructuredStreamingInput;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class TcpStructuredStreamingInput extends AbstractPlugin implements IStructuredStreamingInput {
    /**
     * Return true and empty string if config is valid, return false and error message if config is invalid.
     */
    @Override
    public Pair<Boolean, String> checkConfig() {
        return null;
    }

    /**
     * Get Plugin Name.
     */
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Dataset<Row> getDataset(SparkSession spark) {
        return null;
    }
}
