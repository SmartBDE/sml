package me.smartbde.sml.commonutils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.DataStreamWriter;

public interface IStructuredStreamingOutput {
    /**
     * Things to do before process.
     * */
    boolean open(Long partitionId, Long epochId);

    /**
     * Things to do with each Row.
     * */
    void process(Row row);

    /**
     * Things to do after process.
     * */
    void close(Throwable errorOrNull);

    /**
     * Structured Streaming process.
     * */
    DataStreamWriter<Row> process(Dataset<Row> ds);
}
