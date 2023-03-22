package com.jiang.schedule_manage.service;

import com.jiang.schedule_manage.entity.TaskInfo;
import com.jiang.schedule_manage.entity.request.TaskInfoReq;

public interface TaskService {
    /**
     * 添加任务
     */
    public boolean addJob(TaskInfoReq taskInfoReq);

    /**
     * 更新任务
     */
    public boolean updateJob(TaskInfo taskInfo);

    /**
     * 暂停任务
     */
    public boolean pauseJob(TaskInfo taskInfo);

    /**
     * 恢复任务
     */
    public boolean resumeJob(TaskInfo taskInfo);


}
