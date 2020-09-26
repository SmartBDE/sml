package me.smartbde.sml.commonutils.plugins.input;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IStreamingInput;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.dstream.DStream;

import java.util.Map;

/**
 * 格式输入要求：无
 */
public class TcpStreamingInput extends AbstractPlugin implements IStreamingInput {
    @Override
    public Dataset<Row> rdd2dataset(SparkSession spark, RDD rdd) {
        return null;
    }

    @Override
    public void start(SparkSession spark, StreamingContext ssc, Handler handler) {

    }

    @Override
    public DStream getDStream(StreamingContext ssc) {
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
