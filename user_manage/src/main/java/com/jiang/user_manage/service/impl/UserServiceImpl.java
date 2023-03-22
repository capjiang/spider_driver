package com.jiang.user_manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.entity.UserDTO;
import com.jiang.user_manage.entity.response.ResponseResult;
import com.jiang.user_manage.entity.response.ResponseResultGenerator;
import com.jiang.user_manage.mapper.UserMapper;
import com.jiang.user_manage.service.UserService;
import com.jiang.user_manage.util.JwtUtils;
import com.jiang.user_manage.util.PasswordUtils;
import com.jiang.user_manage.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${user-manage.user-avatar-dir}")
    private String DEFAULT_AVATAR_DIR;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public Boolean registry(User user) {
        // 该账户已存在
        if (userMapper.selectUserByUsername(user.getUsername()) != null) {
            return false;
        }
        // 初始化为低级管理员
        user.setPassword(PasswordUtils.encode(user.getPassword()));
        user.setRole("lAdmin");
        user.setStatus(0);
        return save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public ResponseResult createToken(UserDTO userDTO, User user) {
        String username = userDTO.getUsername();
        if (StringUtils.isEmpty(username)) {
            return ResponseResultGenerator.genBadRequestResult();
        }
        User login_user = userMapper.selectUserByUsername(username);
        String jwtToken = JwtUtils.createJwtToken(login_user.getUserId(), login_user.getUsername(), login_user.getPassword());
        return ResponseResultGenerator.genSuccessResult(jwtToken);
    }

    @Override
    public User getUserByToken(String token) {
        String userId = JwtUtils.getUserId(token);
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        return userMapper.selectById(Integer.parseInt(userId));
    }

    /**
     * 修改密码
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Boolean resetPWByUsername(String username, String password) {
        return userMapper.updatePWByUsername(username, password) > 0;
    }

    /**
     * 修改基本信息
     *
     * @param userDTO
     * @return
     */
    @Override
    public Boolean updateInfo(UserDTO userDTO) {
        try {
            String userId = JwtUtils.getUserId(userDTO.getToken());
            User up_user = new User();
            up_user.setUserId(Integer.parseInt(userId));
            up_user.setName(userDTO.getName());
            return userMapper.updateById(up_user) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 上传头像
     *
     * @param userDTO
     * @param file
     * @return
     */
    @Override
    public boolean updateAvatar(UserDTO userDTO, MultipartFile file) {
        try {
            String userId = JwtUtils.getUserId(userDTO.getToken());
            // 文件下载处理
            String originalFilename = file.getOriginalFilename();
            String originFormat = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
            String filename = DEFAULT_AVATAR_DIR + "/" + userId + originFormat;
            File file1 = new File(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();

            User up_user = new User();
            up_user.setUserId(Integer.parseInt(userId));
            up_user.setAvatar(filename);
            return userMapper.updateById(up_user) == 1;
        } catch (Exception e) {
            log.error("update avatar error", e);
            return false;
        }
    }

    /**
     * 修改角色
     *
     * @param userDTO
     * @return
     */
    @Override
    public boolean updateRole(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String role = userDTO.getRole();
        if (!StringUtils.isEmpty(username, role)) {
            return userMapper.updateRoleByUsername(username, role.toUpperCase()) == 1;
        }
        return false;
    }

}
