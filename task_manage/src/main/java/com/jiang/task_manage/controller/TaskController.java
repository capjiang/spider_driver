package com.jiang.task_manage.controller;

import com.jiang.task_manage.entity.Script;
import com.jiang.task_manage.entity.response.ResponseResult;
import com.jiang.task_manage.entity.response.ResponseResultGenerator;
import com.jiang.task_manage.service.TaskService;
import com.jiang.task_manage.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 跑测试数据
     *
     * @param script
     * @return
     */
    @PostMapping("/run")
    public ResponseResult runTasks(@RequestBody Script script) {
        if (StringUtils.isEmpty(script.getProject(), script.getScope(), script.getName())) {
            return ResponseResultGenerator.genBadRequestResult();
        }
        if (taskService.runTasks(script)) {
            return ResponseResultGenerator.genSuccessResult();
        } else {
            return ResponseResultGenerator.genUnknownErrorResult();
        }
    }

}
