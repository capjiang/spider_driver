package com.jiang.schedule_manage.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TaskInfo {
    private Integer id;
    private String cron;
    private String jobName;
    private String status;
    private Date createTime;
    private Date updateTime;
}
