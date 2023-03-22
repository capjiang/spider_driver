package com.jiang.task_manage.service;

import com.jiang.task_manage.entity.Script;
import org.springframework.web.multipart.MultipartFile;

public interface ScriptService {
    boolean upload(MultipartFile file, Script script);

    boolean deploy(Script script, String version);

}
