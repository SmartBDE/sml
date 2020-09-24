package me.smartbde.sml.publisher;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import me.smartbde.sml.utils.PropertiesUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.configuration.DatabaseConfiguration;

public class Application {
    public static void main(String[] args) throws Exception {
        String url = PropertiesUtil.prop("application.datasource.url");
        String user = PropertiesUtil.prop("application.datasource.username");
        String pass = PropertiesUtil.prop("application.datasource.password");

        MysqlDataSource dataSource;
        dataSource = new MysqlDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(pass);

        DatabaseConfiguration config = new DatabaseConfiguration(dataSource,
                "configs",
                "cname",
                "ckey",
                "cvalue",
                "publisher");

        String protocol = config.getString("from.protocol");
        String host = config.getString("from.host");
        String port = config.getString("from.port");

        String toProtocol = config.getString("to.protocol");
        String toHost = config.getString("to.host");
        String toPort = config.getString("to.port");

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String fromUrl = String.format("%s://%s:%s/publisher", protocol, host, port);
                String toUrl = String.format("%s://%s", toProtocol, toHost);
                from(fromUrl).process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                    }
                }).to(toUrl); // TODO fixme
            }
        });

        synchronized (Application.class) {
            Application.class.wait();
        }
    }}
