package com.jiang.user_manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String role;
    private String token;
    private String status;
    private String oldPassword;
    private String newPassword;

    public UserDTO(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
