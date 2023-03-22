package com.jiang.task_manage.controller;

import com.jiang.task_manage.entity.ScriptConfig;
import com.jiang.task_manage.entity.response.ResponseResult;
import com.jiang.task_manage.entity.response.ResponseResultGenerator;
import com.jiang.task_manage.service.ScriptConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scriptConfig")
public class ScriptConfigController {

    @Autowired
    private ScriptConfigService scriptConfigService;

    @PostMapping("/update")
    public ResponseResult updateScriptConfig(@RequestBody ScriptConfig scriptConfig) {
        if (scriptConfigService.update(scriptConfig)) {
            return ResponseResultGenerator.genSuccessResult();
        } else {
            return ResponseResultGenerator.genBadRequestResult();
        }
    }
}
