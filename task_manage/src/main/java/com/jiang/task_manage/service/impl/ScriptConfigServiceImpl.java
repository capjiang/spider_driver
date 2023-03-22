package com.jiang.task_manage.service.impl;

import com.jiang.task_manage.entity.ScriptConfig;
import com.jiang.task_manage.mapper.ScriptConfigMapper;
import com.jiang.task_manage.service.ScriptConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScriptConfigServiceImpl implements ScriptConfigService {

    @Autowired
    private ScriptConfigMapper scriptConfigMapper;

    @Override
    public boolean update(ScriptConfig scriptConfig) {
        return scriptConfigMapper.updateById(scriptConfig) > 0;
    }

}
