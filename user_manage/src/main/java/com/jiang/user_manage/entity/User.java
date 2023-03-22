package com.jiang.user_manage.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@ToString
public class User {
    @TableId
    private Integer userId;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String role;
    private Integer status;
    private String token;


    public User(UserDTO userDTO) {
        BeanUtils.copyProperties(userDTO, this);
    }
}
