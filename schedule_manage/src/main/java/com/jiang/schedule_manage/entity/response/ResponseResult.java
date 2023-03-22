package com.jiang.schedule_manage.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseResult {
    private int code;
    private Object data;
    private String msg;
    private String token;
}
