package com.jiang.schedule_manage.service;

import com.jiang.schedule_manage.entity.TaskInfo;
import com.jiang.schedule_manage.entity.request.TaskInfoReq;
import com.jiang.schedule_manage.entity.response.ResponseResult;


/**
 * 定时任务接口
 **/
public interface TaskInfoService {
    /**
     * 获取任务列表分页
     */
    ResponseResult selectTaskListByPage(TaskInfoReq taskInfoReq);

    /**
     * 添加定时任务
     */
    ResponseResult addJob(TaskInfo taskInfoReq);

    /**
     * 更新任务
     */
    ResponseResult updateJob(TaskInfo taskInfoReq);

    /**
     * 暂停任务
     */
    ResponseResult pauseJob(Integer taskId);

    /**
     * 恢复任务
     */
    ResponseResult resumeJob(Integer taskId);

    /**
     * 获取所有任务
     */
    ResponseResult selectTasks();

    /**
     * 删除任务
     */
    ResponseResult delete(TaskInfo reqVo);
}

