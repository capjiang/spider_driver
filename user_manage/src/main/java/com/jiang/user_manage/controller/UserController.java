package com.jiang.user_manage.controller;


import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.entity.UserDTO;
import com.jiang.user_manage.entity.UserVO;
import com.jiang.user_manage.entity.response.ResponseResult;
import com.jiang.user_manage.entity.response.ResponseResultGenerator;
import com.jiang.user_manage.service.MailService;
import com.jiang.user_manage.service.UserService;
import com.jiang.user_manage.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * 获取信息
     *
     * @param token
     * @return
     */
    @PostMapping("/info")
    @PreAuthorize("hasAnyRole('LADMIN', 'MADMIN', 'HADMIN')")
    public ResponseResult info(@RequestBody String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseResultGenerator.genBadRequestResult();
        }
        User user = userService.getUserByToken(token);
        if (user == null) {
            return ResponseResultGenerator.genBadRequestResult();
        } else {
            return ResponseResultGenerator.genSuccessResult(new UserVO(user), "token获取信息成功！", token);
        }
    }

    /**
     * 注册
     *
     * @param userDTO
     * @param mailCode
     * @return
     */
    @PostMapping("/registry")
    public ResponseResult registry(@RequestBody UserDTO userDTO, @RequestBody String mailCode) {
        // 验证验证码
        if (!mailService.verifyCode(userDTO.getEmail(), mailCode)) {
            return ResponseResultGenerator.genSuccessResult(null, "验证码错误！", null);
        }
        User reg_user = new User(userDTO);
        if (userService.registry(reg_user)) {
            return ResponseResultGenerator.genSuccessResult(new UserVO(reg_user), "注册成功！", null);
        } else {
            return ResponseResultGenerator.genSuccessResult(new UserVO(reg_user), "账号已经存在！", null);
        }
    }

    /**
     * 修改密码
     *
     * @param userDTO
     * @param mailCode
     * @return
     */
    @PostMapping("/resetPW")
    public ResponseResult resetPW(@RequestBody UserDTO userDTO, @RequestBody String mailCode) {
        // 验证验证码
        if (!mailService.verifyCode(userDTO.getEmail(), mailCode)) {
            return ResponseResultGenerator.genSuccessResult(null, "验证码错误！", null);
        }
        if (userService.resetPWByUsername(userDTO.getUsername(), userDTO.getPassword())) {
            return ResponseResultGenerator.genSuccessResult(null, "修改密码成功！", null);
        } else {
            return ResponseResultGenerator.genSuccessResult(null, "账号不存在！", null);
        }
    }

    /**
     * 修改信息
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/updateInfo")
    @PreAuthorize("hasAnyRole('LADMIN', 'MADMIN', 'HADMIN')")
    public ResponseResult updateInfo(@RequestBody UserDTO userDTO) {
        if (userService.updateInfo(userDTO)) {
            return ResponseResultGenerator.genSuccessResult(null, "修改信息成功！", null);
        } else {
            return ResponseResultGenerator.genBadRequestResult();
        }
    }

    /**
     * 上传头像
     *
     * @param userDTO
     * @param file
     * @return
     */
    @PostMapping(value = "/updateAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('LADMIN', 'MADMIN', 'HADMIN')")
    public ResponseResult updateAvatar(@RequestBody UserDTO userDTO, @RequestParam("file") MultipartFile file) {
        if (userService.updateAvatar(userDTO, file)) {
            return ResponseResultGenerator.genSuccessResult(null, "头像上传成功！", null);
        } else {
            return ResponseResultGenerator.genBadRequestResult();
        }
    }

    /**
     * @param userDTO
     * @return
     */
    @PostMapping("/updateRole")
    @PreAuthorize("hasRole('HADMIN')")
    public ResponseResult updateRole(@RequestBody UserDTO userDTO) {
        if (userService.updateRole(userDTO)) {
            return ResponseResultGenerator.genSuccessResult(null, "修改权限成功！", null);
        } else {
            return ResponseResultGenerator.genBadRequestResult();
        }
    }


}
