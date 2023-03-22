package com.jiang.schedule_manage.config;

import com.jiang.schedule_manage.entity.TaskInfo;
import com.jiang.schedule_manage.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SpringJobFactory springJobFactory;
    @Autowired
    private TaskInfoService taskInfoService;

    @PostConstruct
    public void start() {
        //启动所有任务
        try {
            scheduler.setJobFactory(springJobFactory);
            // scheduler.clear();
            List<TaskInfo> tasks = taskInfoService.selectTasks();
            for (TaskInfo taskInfo : tasks) {
                if (EnumTaskEnable.START.getCode().equals(taskInfo.getStatus()) && !StringUtils.isEmpty(taskInfo.getCron())) {
                    taskInfoService.addJob(taskInfo);
                }
            }
            log.info("定时任务启动完成");
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("定时任务初始化失败");
        }
    }
}
