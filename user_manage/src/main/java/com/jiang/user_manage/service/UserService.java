package com.jiang.user_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.entity.UserDTO;
import com.jiang.user_manage.entity.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;


public interface UserService extends IService<User> {

    Boolean registry(User user);

    User getUserByUsername(String username);

    ResponseResult createToken(UserDTO userDTO, User user);

    User getUserByToken(String token);

    Boolean resetPWByUsername(String username, String password);

    Boolean updateInfo(UserDTO userDTO);

    boolean updateAvatar(UserDTO userDTO, MultipartFile file);

    boolean updateRole(UserDTO userDTO);
}
