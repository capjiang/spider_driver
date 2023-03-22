package com.jiang.task_manage.service.impl;

import com.jiang.task_manage.constant.Enviroment;
import com.jiang.task_manage.entity.Script;
import com.jiang.task_manage.mapper.ScriptVersionMapper;
import com.jiang.task_manage.service.ScriptService;
import com.jiang.task_manage.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptVersionMapper scriptVersionMapper;

    @Override
    public boolean upload(MultipartFile file, Script script) {
        String version = DateUtils.dateToFileString(new Date());
        // 存在缺陷：project和scope的文件夹需要手动创建好
        String fileLocation = Enviroment.getScriptSpiderLocation() +
                "/" + script.getSpiderLocation(version);
        try {
            file.transferTo(new File(fileLocation));
            scriptVersionMapper.insertScriptVersion(script.getScriptId(), version);
            log.info(fileLocation + " transfer successful");
        } catch (IOException e) {
            log.error(fileLocation + " transfer fail");
            return false;
        }
        return true;
    }

    @Override
    public boolean deploy(Script script, String version) {
        File originFile = new File(Enviroment.getScriptDeployLocation() + "/" + script.getDeployLocation());
        // 文件已在系统内
        if (originFile.exists()) {
            if (!originFile.delete()) {
                log.info(originFile + " delete fail");
                return false;
            }
        }
        String readyDeployLocation = Enviroment.getScriptSpiderLocation() + "/" + script.getSpiderLocation(version);
        File deployedFile = new File(readyDeployLocation);
        if (!deployedFile.exists() || !deployedFile.renameTo(originFile)) {
            log.error(deployedFile + " not exist or " + originFile + " rename fail");
            return false;
        }
        log.info(readyDeployLocation + " deploy successful");
        return true;
    }

}
