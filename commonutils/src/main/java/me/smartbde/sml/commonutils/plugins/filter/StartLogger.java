package me.smartbde.sml.commonutils.plugins.filter;

import javafx.util.Pair;
import me.smartbde.sml.storage.JdbcStorage;
import org.apache.spark.sql.SparkSession;

/**
 * 功能说明：前置在任务执行前，对任务执行前的信息进行日志保存
 * 格式输入要求：无
 */
public class StartLogger extends Logger {
    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("url") != null
                && properties.get("user") != null
                && properties.get("pwd") != null
                && properties.get("table") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public boolean prepare(SparkSession spark) {
        if (properties != null && checkConfig().getKey()) {
            jdbcStorage = new JdbcStorage(properties.get("url"), properties.get("user"), properties.get("pwd"));
            loggerFlag = "start";
            return true;
        }
        return false;
    }
}
