package com.jiang.task_manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jiang.task_manage.mapper")
public class TaskManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManageApplication.class, args);
    }

}
