package com.jiang.schedule_manage.controller;

import com.jiang.schedule_manage.entity.request.TaskInfoReq;
import com.jiang.schedule_manage.entity.response.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class TaskController {


    /**
     * 定时器列表
     */
    @PostMapping("/list")
    public ResponseResult list(@RequestBody TaskInfoReq reqVo) {
        return null;
    }

    /**
     * 定时器修改
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody TaskInfoReq reqVo) {
        return null;
    }

    /**
     * 暂停任务
     */
    @PostMapping("/pause")
    public ResponseResult pause(Integer taskId) {
        return null;
    }

    /**
     * 增加任务
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody TaskInfoReq taskInfoReq) {
        return null;
    }

    /**
     * 恢复任务
     */
    @PostMapping("/resume")
    public ResponseResult resume(Integer taskId) {
        return null;
    }

    /**
     * 删除任务
     */
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody TaskInfoReq reqVo) {
        return null;
    }

}
