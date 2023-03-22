package com.jiang.task_manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.task_manage.entity.Script;
import com.jiang.task_manage.entity.ScriptDTO;
import com.jiang.task_manage.entity.response.ResponseResult;
import com.jiang.task_manage.entity.response.ResponseResultGenerator;
import com.jiang.task_manage.service.ScriptService;
import com.jiang.task_manage.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/script")
public class ScriptController extends AbstractJackson2HttpMessageConverter {

    @Autowired
    private ScriptService scriptService;

    protected ScriptController(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @PostMapping("/upload")
    public ResponseResult uploadScript(@RequestPart("file") MultipartFile file,
                                       @RequestPart("script") Script script) {
        if (file.isEmpty()) {
            return ResponseResultGenerator.genBadRequestResult("File is empty");
        }
        if (script.getScriptId() == null || StringUtils.isEmpty(script.getProject(), script.getScope(), script.getName())) {
            return ResponseResultGenerator.genBadRequestResult("script can not has null attr");
        }
        if (scriptService.upload(file, script)) {
            return ResponseResultGenerator.genSuccessResult();
        } else {
            return ResponseResultGenerator.genUnknownErrorResult();
        }
    }

    @PostMapping("/deploy")
    public ResponseResult deployScript(@RequestBody ScriptDTO scriptDTO) {
        if (StringUtils.isEmpty(scriptDTO.getProject(), scriptDTO.getScope(), scriptDTO.getName())) {
            return ResponseResultGenerator.genBadRequestResult("script can not has null attr");
        }
        if (StringUtils.isEmpty(scriptDTO.getVersion())) {
            return ResponseResultGenerator.genBadRequestResult("version is empty");
        }
        Script script = scriptDTO.getScript();
        if (scriptService.deploy(script, scriptDTO.getVersion())) {
            return ResponseResultGenerator.genSuccessResult();
        } else {
            return ResponseResultGenerator.genUnknownErrorResult();
        }
    }
}
