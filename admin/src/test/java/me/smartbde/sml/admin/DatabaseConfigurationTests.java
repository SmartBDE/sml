package me.smartbde.sml.admin;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.DatabaseConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatabaseConfigurationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        DataSource dataSource = applicationContext.getBean(DataSource.class);

//        BasicConfigurationBuilder<DatabaseConfiguration> builder =
//                new BasicConfigurationBuilder<DatabaseConfiguration>(DatabaseConfiguration.class);
//        Parameters parameters = new Parameters();
//        builder.configure(
//                parameters.database()
//                        .setDataSource(dataSource)
//                        .setTable("configs")
//                        .setKeyColumn("ckey")
//                        .setValueColumn("cvalue")
//                        .setConfigurationNameColumn("cname")
//                        .setConfigurationName("collector")
//        );

        DatabaseConfiguration config = new DatabaseConfiguration();
        config.setDataSource(dataSource);
        config.setTable("configs");
        config.setKeyColumn("ckey");
        config.setValueColumn("cvalue");
        config.setConfigurationNameColumn("cname");
        config.setConfigurationName("collector");

        config.addProperty("host", "127.0.0.1");
        config.addProperty("port", "8000");
    }
}
