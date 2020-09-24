package me.smartbde.sml.smlbatch;

import me.smartbde.sml.admin.domain.model.Jobs;
import me.smartbde.sml.admin.domain.model.Schedules;
import me.smartbde.sml.admin.domain.repository.MySQLJobsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLPluginsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLSchedulesRepository;
import me.smartbde.sml.commonutils.*;
import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class AppBean implements ApplicationRunner {
    private final Integer NOW = -1;
    private final Integer ONCE = 0;
    private final Integer EVERYDAY = 1;

    @Autowired
    private MySQLSchedulesRepository mySQLSchedulesRepository;
    @Autowired
    private MySQLJobsRepository mySQLJobsRepository;
    @Autowired
    private MySQLPluginsRepository mySQLPluginsRepository;
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
            if (s.getType().equals(NOW)) {
                scheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        runBatchJob(spark, s);
                    }
                }, 0, TimeUnit.SECONDS);

            } else if (s.getType().equals(ONCE)) {
                Date date = new Date();

                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(date);
                rightNow.add(Calendar.HOUR, 1);
                rightNow.add(Calendar.MINUTE, -55);
                rightNow.add(Calendar.SECOND, -30);
                Long initialDelay = rightNow.getTime().getTime();

                scheduler.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        runBatchJob(spark, s);
                    }
                }, initialDelay, 0, TimeUnit.DAYS);

            } else if (s.getType().equals(EVERYDAY)) {
                Date date = new Date();

                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(date);
                rightNow.add(Calendar.HOUR, 1);
                rightNow.add(Calendar.MINUTE, -55);
                rightNow.add(Calendar.SECOND, -30);
                Long initialDelay = rightNow.getTime().getTime();

                scheduler.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        runBatchJob(spark, s);
                    }
                }, initialDelay, 1, TimeUnit.DAYS);
            }
        }
    }

    private void runBatchJob(SparkSession spark, Schedules schedules)  {
        Logger logger = LoggerFactory.getLogger(AppBean.class);
        logger.debug(schedules.getJobname());

        FilterSession session = new FilterSession(schedules.getJobname());

        // 从配置中装载插件并初始化
        List<Jobs> jobs = mySQLJobsRepository.findByName(session.getJobName());

        List<IInput> inputs = new ArrayList<>();
        List<IFilter> filters = new ArrayList<>();
        List<IOutput> outputs = new ArrayList<>();

        for (Jobs job : jobs) {
            // 首先要创建plugin，采用扫描目录获取名字的方式动态加载

            // 然后把配置传给plugin

            // 然后调用plugin的prepare
        }

        // 从配置中装载input，调用filter，执行output


        // 看是否有触发下一步的执行
        if (schedules.getNextId() != null) {
            Schedules next = mySQLSchedulesRepository.findOne(schedules.getNextId());
            runBatchJob(spark, next);
        }
    }
}
