package com.jiang.user_manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jiang.user_manage.mapper")
public class UserManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManageApplication.class, args);
    }

}
