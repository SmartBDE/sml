package me.smartbde.sml.pipeline;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class SparkStreamingTests implements Serializable {
    @Test
    public void test() throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                PrintWriter pw = null;
                try {
                    serverSocket = new ServerSocket(9999);
                    System.out.println("服务启动，等待连接");
                    Socket socket = serverSocket.accept();
                    System.out.println("连接成功，来自：" + socket.getRemoteSocketAddress());
                    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    int j = 0;
                    while (j < 100) {
                        j++;
                        String str = "spark streaming test " + j;
                        pw.println(str);
                        pw.flush();
                        System.out.println(str);
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        pw.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.run();
    }

    @Test
    public void testStreaming() throws Exception {
        String appName = "SparkStreamingTests";
        String master = "local[*]";

        //初始化sparkConf
        SparkConf sparkConf = new SparkConf().setMaster(master).setAppName(appName);
        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();

        int duration = 5;

        //获得JavaStreamingContext
        JavaStreamingContext ssc = new JavaStreamingContext(
                new JavaSparkContext(spark.sparkContext()), Durations.seconds(duration));

        String host = "127.0.0.1";
        int port = 9999;

        //从socket源获取数据
        JavaReceiverInputDStream<String> lines = ssc.socketTextStream(host, port);

//        StructType details = new StructType(new StructField[]{
//                new StructField("subject", DataTypes.StringType, false, Metadata.empty()),
//                new StructField("grade", DataTypes.StringType, false, Metadata.empty()),
//                new StructField("remark", DataTypes.StringType, false, Metadata.empty())
//        });
//
//        StructType recordType = new StructType();
//        recordType = recordType.add("details", details, false);
//
//        StructType structType = new StructType();
//        structType = structType.add("name", DataTypes.StringType, false);
//        structType = structType.add("planet", DataTypes.StringType, false);
//        structType = structType.add("number", DataTypes.StringType, false);
//        structType = structType.add("record", recordType, false);

        // 把DStream转换成为Dataset<Row>
        // https://stackoverflow.com/questions/40926947/how-to-convert-javapairinputdstream-into-dataset-dataframe-in-spark

        StructType schema = DataTypes.createStructType(new StructField[] {
                new StructField("content", DataTypes.StringType, false, Metadata.empty())
        });

        lines.foreachRDD((rdd, time) -> {
            // 把JavaRDD<String>转换成为JavaRDD<Row>
            JavaRDD<Row> rowRDD = rdd.map(new Function<String, Row>() {
                @Override
                public Row call(String s) throws Exception {
                    return RowFactory.create(s);
                }
            });

            Dataset<Row> df = spark.createDataFrame(rowRDD, schema);
            df.show();

            // df可以保存在内存中，也可以写入存储层，考虑到实际情况，把合并结果保存在存储层为妥

            // 创建临时视图后，可以用SQL访问
//            df.createOrReplaceTempView("content");
        });

//        //拆分行成单词
//        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
//            public Iterator<String> call(String s) throws Exception {
//                return Arrays.asList(s.split(" ")).iterator();
//            }
//        });
//        words.print();
//
//        //计算每个单词出现的个数
//        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
//            public Tuple2<String, Integer> call(String s) throws Exception {
//                return new Tuple2<String, Integer>(s, 1);
//            }
//        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
//            public Integer call(Integer integer, Integer integer2) throws Exception {
//                return integer + integer2;
//            }
//        });
//
//        //输出结果
//        wordCounts.print();

        //开始作业
        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ssc.close();
        }
    }
}
