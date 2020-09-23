package me.smartbde.sml.utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.util.Date;

public class DBUtils {
    public static void update(String sys) throws SQLException {
        String url = PropertiesUtil.prop("application.datasource.url");
        String user = PropertiesUtil.prop("application.datasource.username");
        String pass = PropertiesUtil.prop("application.datasource.password");

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(pass);

        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("update systems set start = ? where name = ?");

        Date date = new Date(); // 获取当前时间
        Timestamp t = new Timestamp(date.getTime());

        stmt.setTimestamp(1, t);
        stmt.setString(2, sys);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
