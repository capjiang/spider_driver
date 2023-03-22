package com.jiang.script_manage.starter;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiang.script_manage.config.RabbitMQConfig;
import com.jiang.script_manage.constant.Environment;
import com.jiang.script_manage.entity.Task;
import com.jiang.script_manage.entity.response.ScrapydResponseEntity;
import com.jiang.script_manage.mapper.TaskMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RabbitListener(queues = {RabbitMQConfig.SCRIPT_TASK_QUEUE})
public class ScriptTaskStarter {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskMapper taskMapper;

    @RabbitHandler
    public void startScriptTask(@Payload Map<String, String> data,
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                Channel channel) throws IOException {
        ScrapydResponseEntity result = null;
        try {
            if (data.isEmpty()) {
                log.info("script task empty");
            }
            String scrapydUrl = Environment.SCRAPYD_URL;
            // 请求头设置,x-www-form-urlencoded格式的数据
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 提交参数设置
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.setAll(data);

            // 组装请求体
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            // 发送post请求，并打印结果
            result = restTemplate.postForObject(scrapydUrl, request, ScrapydResponseEntity.class);
            log.info(result.getJobid() + " : " + result.getStatus());
            // 手动应答
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 出现异常后发送拒绝信号或者重新入队信号，第二个参数表示是否批量拒绝（同上），第三个参数表示是否重新入队（true表示重新入队）
            channel.basicNack(deliveryTag, false, true);
        }
        // 数据库设置task状态
        if (result != null && "ok".equals(result.getStatus())) {
            taskMapper.update(null, new UpdateWrapper<Task>()
                    .set("end_date", new Date()).set("status", 1)   // 完成状态
                    .eq("jobid", result.getJobid()));
        } else {
            taskMapper.update(null, new UpdateWrapper<Task>()
                    .set("end_date", new Date()).set("status", 2)   // 异常状态
                    .eq("jobid", result.getJobid()));
        }
    }

}
