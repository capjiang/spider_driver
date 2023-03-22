package com.jiang.user_manage;

import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserManageApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testJDBC() {
        User user = userService.getUserByUsername("jiang");
        System.out.println(user);
    }

}
