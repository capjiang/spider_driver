package com.jiang.user_manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.user_manage.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username like #{username}")
    User selectUserByUsername(String username);

    @Select("select * from user where role like #{role}")
    List<User> selectUsersByRole(String role);

    @Update("update user set password = #{password} where username like #{username}")
    Integer updatePWByUsername(String username, String password);

    @Update("update user set role = #{password} where username like #{username}")
    Integer updateRoleByUsername(String username, String role);
}
