package com.jiang.user_manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MailDTO {

    private String mail;
    private String subject;
    private String text;

}
