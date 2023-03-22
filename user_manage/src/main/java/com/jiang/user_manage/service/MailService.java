package com.jiang.user_manage.service;

public interface MailService {

    boolean sendVerificationCode(String targetMail);

    boolean verifyCode(String mail, String code);

}
