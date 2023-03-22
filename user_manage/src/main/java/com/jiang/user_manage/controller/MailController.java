package com.jiang.user_manage.controller;

import com.jiang.user_manage.entity.MailDTO;
import com.jiang.user_manage.entity.response.ResponseResult;
import com.jiang.user_manage.entity.response.ResponseResultGenerator;
import com.jiang.user_manage.service.MailService;
import com.jiang.user_manage.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendCode")
    public ResponseResult sendCode(@RequestBody MailDTO mailDTO) {
        String mail = mailDTO.getMail();
        if (StringUtils.isEmpty(mail)) {
            return ResponseResultGenerator.genBadRequestResult();
        }
        if (mailService.sendVerificationCode(mail)) {
            return ResponseResultGenerator.genSuccessResult();
        } else {
            return ResponseResultGenerator.genUnknownErrorResult();
        }
    }

}
