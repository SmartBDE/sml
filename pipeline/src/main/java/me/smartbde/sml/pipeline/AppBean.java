package me.smartbde.sml.pipeline;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.*;
import me.smartbde.sml.pipeline.domain.model.Jobs;
import me.smartbde.sml.pipeline.domain.model.PluginClass;
import me.smartbde.sml.pipeline.domain.model.PluginInfo;
import me.smartbde.sml.pipeline.domain.model.Streamings;
import me.smartbde.sml.pipeline.domain.repository.MySQLJobsRepository;
import me.smartbde.sml.pipeline.domain.repository.MySQLPluginClassRepository;
import me.smartbde.sml.pipeline.domain.repository.MySQLPluginsRepository;
import me.smartbde.sml.pipeline.domain.repository.MySQLStreamingsRepository;
import org.apache.commons.collections.map.HashedMap;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1. 创建spark session
 * 2. 注册udf
 * 3. 根据配置创建input, filter, output
 * 4. 运行(以input判断是stream或batch)
 * 4-1. stream
 *    a. 初始化input, filter, output
 *    b. 把input注册到TempView
 *    c. 启动并运行
 * 4-2. batch
 *    a. 按顺序执行
 *    b. 按需触发下一个任务
 */
@Component
public class AppBean implements ApplicationRunner {
    @Autowired
    private MySQLStreamingsRepository mySQLStreamingsRepository;
    @Autowired
    private MySQLJobsRepository mySQLJobsRepository;
    @Autowired
    private MySQLPluginsRepository mySQLPluginsRepository;
    @Autowired
    private MySQLPluginClassRepository mySQLPluginClassRepository;
    private String appName = "StreamApplication";
    private String master = "local[*]";
    private SparkConf sparkConf = new SparkConf().setMaster(master).setAppName(appName);
    private SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Logger logger = LoggerFactory.getLogger(AppBean.class);

        List<Streamings> streamings = mySQLStreamingsRepository.findAll();
        Streamings streaming = streamings.get(0); // 这里应该只有一个
        FilterSession session = new FilterSession(streaming.getJobname());

        // 从配置中装载插件并初始化
        List<Jobs> jobs = mySQLJobsRepository.findByNameOrderByPriorityAsc(session.getJobName());

        IStreamingInput streamingInput = null;
        List<IInput> inputs = new ArrayList<>();
        List<IFilter> filters = new ArrayList<>();
        List<IOutput> outputs = new ArrayList<>();

        boolean okFlag = true;
        for (Jobs job : jobs) {
            try {
                String clazz = job.getPlugin().split("\\.")[0];
                // 首先要创建plugin，采用录获取名字的方式动态加载
                PluginClass pluginClass = mySQLPluginClassRepository.findOne(clazz);

                IPlugin plugin = (IPlugin) PluginUtils.newClazz(pluginClass.getClazz());
                if (pluginClass.getType().equals("filter") || pluginClass.getType().equals("sqlFilter")) {
                    filters.add((IFilter) plugin);
                } else if (pluginClass.getType().equals("streamInput")) {
                    streamingInput = (IStreamingInput) plugin;
                } else if (pluginClass.getType().equals("batchInput")) {
                    inputs.add((IInput) plugin);
                } else if (pluginClass.getType().equals("output")) {
                    outputs.add((IOutput) plugin);
                }
                // 然后把配置传给plugin，并对配置进行检查
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
                    logger.info("can not pass config:" + plugin.getName());
                    okFlag = false;
                }
            } catch (Exception e) {
                logger.info(e.toString());
            }
        }

        // 只要有一个配置不通过，就不继续执行了
        if (okFlag && streamingInput != null) {
            Map<String, String> config = streamingInput.getConfig();
            int duration = Integer.parseInt(config.get("duration"));

            JavaStreamingContext ssc = new JavaStreamingContext(
                    new JavaSparkContext(spark.sparkContext()), Durations.seconds(duration));

            streamingInput.start(spark, ssc, new IStreamingInput.Handler() {
                @Override
                public void execute(Dataset<Row> ds) {
                    for (IFilter filter : filters) {
                        ds = filter.process(spark, ds, session);
                    }

                    for (IOutput output : outputs) {
                        output.process(ds);
                    }
                }
            });

            ssc.start();
            try {
                ssc.awaitTermination();
            } finally {
                ssc.close();
            }
        }
    }
}
