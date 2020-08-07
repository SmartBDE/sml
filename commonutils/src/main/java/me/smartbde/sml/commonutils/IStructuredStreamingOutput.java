package me.smartbde.sml.commonutils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.DataStreamWriter;

public interface IStructuredStreamingOutput extends IPlugin {
    /**
     * Things to do before process.
     * */
    public boolean open(Long partitionId, Long epochId);

    /**
     * Things to do with each Row.
     * */
    public void process(Row row);

    /**
     * Things to do after process.
     * */
    public void close(Throwable errorOrNull);

    /**
     * Waterdrop Structured Streaming process.
     * */
    public DataStreamWriter<Row> process(Dataset<Row> df);
}
