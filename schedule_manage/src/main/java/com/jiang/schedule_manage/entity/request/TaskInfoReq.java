package com.jiang.schedule_manage.entity.request;

import lombok.Data;

/**
 * 任务请求类
 **/
@Data
public class TaskInfoReq {
    /**
     * 任务编号
     */
    private Integer id;
    /**
     * 任务时间表达式
     */
    private String cron;
    /**
     * 任务状态
     */
    private String status;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 每页显示条数
     */
    private int pageSize = 10;
    /**
     * 当前页数
     */
    private int pageCurrent = 1;
}
