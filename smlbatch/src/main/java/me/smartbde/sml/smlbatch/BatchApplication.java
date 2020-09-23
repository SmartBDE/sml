package me.smartbde.sml.smlbatch;

import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * BatchApplication会以定时任务管理者的方式，对配置的job定时执行
 *
 * 运行步骤：
 * 1. 初始化环境
 * 2. 按输入列表循环调用
 * 2-1. 按顺序执行filter
 * 2-2. 按顺序执行output
 */
public class BatchApplication {
    private static String appName = "BatchApplication";
    private static String master = "local[*]";
    private static SparkConf sparkConf = new SparkConf().setMaster(master).setAppName(appName);
    private static SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(Integer.parseInt(PropertiesUtil.prop("application.scheduledExecutorService.threads")));

    public static void main(String[] args) {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                runBatchJob(spark);
            }
        }, 0, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                runBatchJob(spark);
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    private static void runBatchJob(SparkSession spark)  {
        // 从配置中装载插件并初始化

        // 从配置中装载input，调用filter，执行output

        // 看是否有触发下一步的执行
        System.out.println("here");
    }
}
