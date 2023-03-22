package com.jiang.user_manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserVO {
    private Integer userId;
    private String username;
    private String name;
    private String avatar;
    private String email;
    private String role;
    private String token;

    public UserVO(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
