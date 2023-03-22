package com.jiang.schedule_manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiang.schedule_manage.entity.TaskInfo;
import com.jiang.schedule_manage.entity.request.TaskInfoReq;
import com.jiang.schedule_manage.entity.response.ResponseResult;
import com.jiang.schedule_manage.entity.response.ResponseResultGenerator;
import com.jiang.schedule_manage.mapper.TaskInfoMapper;
import com.jiang.schedule_manage.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskInfoMapper taskInfoMapper;


    @Override
    public ResponseResult selectTaskListByPage(TaskInfoReq taskInfoReq) {
        IPage<TaskInfo> page = new Page(taskInfoReq.getPageCurrent(), taskInfoReq.getPageSize());
        taskInfoMapper.selectPage(page, new QueryWrapper<>());
        return ResponseResultGenerator.genSuccessResult(page.getRecords());
    }

    @Override
    public ResponseResult addJob(TaskInfo taskInfo) {
        if (!CronExpression.isValidExpression(taskInfo.getCron())) {
            log.error("更新任务失败，表达式有误：{}", taskInfo.getCron());
            return ResponseResultGenerator.genBadRequestResult("cron表达式有误");
        }
        if (taskInfoMapper.insert(taskInfo) > 0) {
            return ResponseResultGenerator.genSuccessResult();
        }
        return ResponseResultGenerator.genUnknownErrorResult();
    }

    @Override
    public ResponseResult updateJob(TaskInfo taskInfoReq) {
        if (!CronExpression.isValidExpression(taskInfoReq.getCron())) {
            log.error("更新任务失败，表达式有误：{}", taskInfoReq.getCron());
            return ResponseResultGenerator.genBadRequestResult("cron表达式有误");
        }
        TaskInfo taskInfo = taskInfoMapper.selectById(taskInfoReq.getId());
        if (taskInfo == null) {
            return ResponseResultGenerator.genBadRequestResult("任务不存在");
        }
        taskInfo.setUpdateTime(new Date());
        if (taskInfoMapper.updateById(taskInfo) > 0) {
            return ResponseResultGenerator.genSuccessResult();
        }
        return ResponseResultGenerator.genUnknownErrorResult();
    }

    @Override
    public ResponseResult pauseJob(Integer taskId) {
        TaskInfo taskInfo = taskInfoMapper.selectById(taskId);
        if (taskInfo == null) {
            return ResponseResultGenerator.genBadRequestResult("任务不存在");
        }
        if () {
            return null;
        }
    }

    @Override
    public ResponseResult resumeJob(Integer taskId) {
        return null;
    }

    @Override
    public ResponseResult selectTasks() {
        return null;
    }

    @Override
    public ResponseResult delete(TaskInfo reqVo) {
        return null;
    }
}
