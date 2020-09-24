package me.smartbde.sml.smlbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * BatchApplication会以定时任务管理者的方式，对配置的job定时执行
 *
 * 运行步骤：
 * 1. 初始化环境
 * 2. 按输入列表循环调用
 * 2-1. 按顺序执行filter
 * 2-2. 按顺序执行output
 */
@SpringBootApplication
@EnableJpaRepositories("me.smartbde.sml.smlbatch.domain.repository")
@ComponentScan("me.smartbde.sml.smlbatch")
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
