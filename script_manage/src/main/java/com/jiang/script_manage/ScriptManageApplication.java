package com.jiang.script_manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jiang.script_manage.mapper")

public class ScriptManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScriptManageApplication.class, args);
    }

}
