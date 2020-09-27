package me.smartbde.sml.smlbatch;

import javafx.util.Pair;
import me.smartbde.sml.admin.domain.model.Jobs;
import me.smartbde.sml.admin.domain.model.PluginClass;
import me.smartbde.sml.admin.domain.model.PluginInfo;
import me.smartbde.sml.admin.domain.model.Schedules;
import me.smartbde.sml.admin.domain.repository.MySQLJobsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLPluginClassRepository;
import me.smartbde.sml.admin.domain.repository.MySQLPluginsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLSchedulesRepository;
import me.smartbde.sml.commonutils.*;
import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
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
    @Autowired
    private MySQLPluginClassRepository mySQLPluginClassRepository;
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
            try {
                String clazz = job.getPlugin().split(".")[0];
                // 首先要创建plugin，采用录获取名字的方式动态加载
                PluginClass pluginClass = mySQLPluginClassRepository.findOne(clazz);

                IPlugin plugin = (IPlugin) PluginUtils.newClazz(pluginClass.getClazz());
                if (pluginClass.getType().equals("filter") || pluginClass.getType().equals("sqlFilter")) {
                    filters.add((IFilter) plugin);
                } else if (pluginClass.getType().equals("batchInput") || pluginClass.getType().equals("streamInput")) {
                    inputs.add((IInput) plugin);
                } else if (pluginClass.getType().equals("output")) {
                    outputs.add((IOutput) plugin);
                }
                // 然后把配置传给plugin
                List<PluginInfo> pluginInfos = mySQLPluginsRepository.findByPlugin(job.getPlugin());
                Map<String, String> map = new HashedMap();
                for (PluginInfo info : pluginInfos) {
                    map.put(info.getCkey(), info.getCvalue());
                }
                plugin.setConfig(map);

                // 然后调用plugin的prepare
                Pair<Boolean, String> r = plugin.checkConfig();
                if (r.getKey()) {
                    plugin.prepare(spark);
                } else {
                    logger.info("can not pass config:" + r.getValue());
                }
            } catch (Exception e) {
                logger.info(e.toString());
            }
        }

        // 从配置中装载input，调用filter，执行output
        for (IInput input : inputs) {
            Dataset<Row> ds = input.getDataset(spark);
            for (IFilter filter : filters) {
                ds = filter.process(spark, ds, session);
            }

            for (IOutput output : outputs) {
                output.process(ds);
            }
        }

        // 看是否有触发下一步的执行
        if (schedules.getNextid() != null) {
            Schedules next = mySQLSchedulesRepository.findOne(schedules.getNextid());
            runBatchJob(spark, next);
        }
    }
}