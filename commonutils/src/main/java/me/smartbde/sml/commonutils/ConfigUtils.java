package me.smartbde.sml.commonutils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.configuration.DatabaseConfiguration;

import javax.sql.DataSource;

public class ConfigUtils {
    private static MysqlDataSource dataSource = null;

    private static class ConfigUtilsHolder {
        public static ConfigUtils instance = new ConfigUtils();
    }

    //让构造函数为 private，这样该类就不会被实例化
    private ConfigUtils() {
    }

    //获取唯一可用的对象
    public static ConfigUtils getInstance() {
        return ConfigUtilsHolder.instance;
    }

    public static DataSource dataSource(String url, String user, String pass) {
        if (dataSource == null) {
            dataSource = new MysqlDataSource();
            dataSource.setURL(url);
            dataSource.setUser(user);
            dataSource.setPassword(pass);
        }

        return dataSource;
    }

    public DatabaseConfiguration config(String table,
                                        String nameColumn,
                                        String keyColumn,
                                        String valueColumn,
                                        String name) {
        DatabaseConfiguration config = new DatabaseConfiguration(dataSource,
                table,
                nameColumn,
                keyColumn,
                valueColumn,
                name);
        return config;
    }
}
