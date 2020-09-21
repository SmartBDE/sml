package me.smartbde.sml.smlbatch;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/**
 * 运行步骤：
 * 1. 初始化环境
 * 2. 按输入列表循环调用
 * 2-1. 按顺序执行filter
 * 2-2. 按顺序执行output
 * 3. 如果有下一步任务，则触发下一步任务
 */
public class BatchApplication {
    private static String appName = "BatchApplication";
    private static String master = "local[*]";

    public static void main(String[] args) throws Exception {
        SparkConf sparkConf = new SparkConf().setMaster(master).setAppName(appName);
        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();

        // 从配置中装载插件并初始化

        // 从配置中装载input，调用filter，执行output

        // 看是否有触发下一步的执行

        spark.stop();
    }
}
