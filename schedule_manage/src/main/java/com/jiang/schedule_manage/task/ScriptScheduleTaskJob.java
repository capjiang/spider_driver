package com.jiang.schedule_manage.task;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jiang.schedule_manage.common.HttpClient;
import com.jiang.schedule_manage.constant.Environment;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
@Component
public class ScriptScheduleTaskJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        // 传递参数转为map
        String input = jobDataMap.getString("input");
        JSONObject jsonObject = JSONUtil.parseObj(input);
        Map map = new JSONObject(jsonObject).toBean(Map.class);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(map);
        // 发送请求
        String responseBody = HttpClient.sendPostRequest(Environment.SCRAPYD_URL, multiValueMap);
        log.info(responseBody);
    }
}
