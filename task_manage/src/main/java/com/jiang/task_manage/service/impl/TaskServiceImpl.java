package com.jiang.task_manage.service.impl;

import com.jiang.task_manage.config.RabbitMQConfig;
import com.jiang.task_manage.constant.Enviroment;
import com.jiang.task_manage.entity.Script;
import com.jiang.task_manage.entity.ScriptConfig;
import com.jiang.task_manage.entity.Task;
import com.jiang.task_manage.mapper.ScriptConfigMapper;
import com.jiang.task_manage.mapper.TaskMapper;
import com.jiang.task_manage.service.InputService;
import com.jiang.task_manage.service.TaskService;
import com.jiang.task_manage.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private InputService inputService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ScriptConfigMapper scriptConfigMapper;

    @Override
    public boolean runTasks(Script script) {

        List<Map<String, String>> inputDatas = inputService.getInputDatas(script);
        ScriptConfig scriptConfig = scriptConfigMapper.selectById(script.getScriptId());
        String priority = String.valueOf(scriptConfig.getPriority());
        String spider = script.getSpider();
        Date date_now = new Date();
        String timeStr = DateUtils.dateToFileString(date_now);

        int size = inputDatas.size();
        for (int i = 0; i < size; i++) {
            HashMap<String, String> map = new HashMap<>(inputDatas.get(i));
            map.put("project", Enviroment.DEFAULT_SCRIPT_PROJECT);
            map.put("priority", priority);
            map.put("spider", spider);
            String jobid = spider + "_" + timeStr + "_" + i;
            map.put("jobid", jobid);
            map.put("time_str", timeStr);
            map.put("number", String.valueOf(i));
            try {
                // 数据库保存新task
                taskMapper.insert(new Task(0, jobid, spider, date_now, null, 0));
                // rabbitMQ存储代运行任务
                rabbitTemplate.convertAndSend(RabbitMQConfig.SCRIPT_TASK_EXCHANGE,
                        RabbitMQConfig.SCRIPT_TASK_BINDING_KEY, map);
                log.info(map.get("jobid") + " is in the task queue");
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
        }
        return true;
    }
}
