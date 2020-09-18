package me.smartbde.sml.controller;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.configuration.DatabaseConfiguration;

public class Application {
    public static void main(String[] args) throws Exception {
        String url = PropertiesUtil.prop("spring.datasource.url");
        String user = PropertiesUtil.prop("spring.datasource.username");
        String pass = PropertiesUtil.prop("spring.datasource.password");

        MysqlDataSource dataSource;
        dataSource = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(pass);

        DatabaseConfiguration config = new DatabaseConfiguration(dataSource,
                "configs",
                "cname",
                "ckey",
                "cvalue",
                "collector");

        String host = config.getString("host");
        String port = config.getString("port");

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String url = String.format("jetty:http://{0}:{1}/collector", host, port);
                from(url).process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        // TODO Auto-generated method stub
                        System.out.println("进入jetty...");
                    }
                });
            }
        });

        synchronized (Application.class) {
            Application.class.wait();
        }
    }
}
