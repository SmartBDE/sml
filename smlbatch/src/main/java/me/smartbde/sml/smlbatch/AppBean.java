package me.smartbde.sml.smlbatch;

import me.smartbde.sml.smlbatch.domain.model.Schedules;
import me.smartbde.sml.smlbatch.domain.repository.MySQLSchedulesRepository;
import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class AppBean implements ApplicationRunner {
    @Autowired
    private MySQLSchedulesRepository mySQLSchedulesRepository;
    private String appName = "BatchApplication.AppBean";
    private String master = "local[*]";
    private SparkConf sparkConf = new SparkConf().setMaster(master).setAppName(appName);
    private SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(Integer.parseInt(PropertiesUtil.prop("application.scheduledExecutorService.threads")));

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<Schedules> list = mySQLSchedulesRepository.findAll();

        for (Schedules s : list) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    runBatchJob(spark, s);
                }
            }, 0, TimeUnit.SECONDS);
        }

//        scheduler.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                runBatchJob(spark);
//            }
//        }, 0, 1, TimeUnit.DAYS);
    }


    private void runBatchJob(SparkSession spark, Schedules schedules)  {
        Logger logger = LoggerFactory.getLogger(AppBean.class);
        logger.debug(schedules.getJobname());

        // 从配置中装载插件并初始化

        // 从配置中装载input，调用filter，执行output

        // 看是否有触发下一步的执行
    }
}
