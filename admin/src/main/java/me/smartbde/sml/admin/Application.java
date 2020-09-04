package me.smartbde.sml.admin;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableJpaRepositories("me.smartbde.sml.admin.domain.repository")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}