package com.jiang.user_manage.service.impl;

import com.jiang.user_manage.service.MailService;
import com.jiang.user_manage.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final String DEFAULT_FORM_MAIL = "1765224939@qq.com";   // 默认发送邮箱
    private final String DEFAULT_MAIL_KEY = "MAIL_";       // redis key前缀

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送验证码
     *
     * @param targetMail
     * @return
     */
    @Override
    public boolean sendVerificationCode(String targetMail) {
        try {
            // 生成验证码
            String code = randomCode();
            stringRedisTemplate.opsForValue().set(DEFAULT_MAIL_KEY + targetMail, code);
            // 邮件内容
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证码邮件");
            mailMessage.setText("您收到的验证码为：" + code + "，请勿泄露给其他人！");
            mailMessage.setTo(targetMail);
            mailMessage.setFrom(DEFAULT_FORM_MAIL);
            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            log.error("Fail to send verification code to " + targetMail, e);
            return false;
        }
    }

    /**
     * 验证验证码
     *
     * @param mail
     * @param code
     * @return
     */
    @Override
    public boolean verifyCode(String mail, String code) {
        if (StringUtils.isEmpty(mail, code)) {
            return false;
        }
        String redis_code = stringRedisTemplate.opsForValue().get(DEFAULT_MAIL_KEY + mail);
        if (code.equals(redis_code)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 生成验证码
     *
     * @return
     */
    private String randomCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
