package com.jiang.script_manage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SCRIPT_TASK_EXCHANGE = "script_task_exchange";       // 脚本任务交换机
    public static final String SCRIPT_TASK_QUEUE = "script_task.direct.queue";   // 脚本任务队列
    public static final String MAIL_QUEUE = "mail.direct.queue";      // 邮件队列
    public static final String SCRIPT_TASK_BINDING_KEY = "script_task";   // 脚本任务关键字
    public static final String MAIL_BINDING_KEY = "mail";   // 邮件关键字


    // 队列
    @Bean
    public Queue taskQueue() {
        // 持久化true
        return new Queue(SCRIPT_TASK_QUEUE, true);
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE, true);
    }

    // 交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(SCRIPT_TASK_EXCHANGE, true, false);
    }

    // 绑定
    @Bean
    public Binding bindingDirectTask() {
        return BindingBuilder.bind(taskQueue()).to(directExchange()).with(SCRIPT_TASK_BINDING_KEY);
    }

    @Bean
    public Binding bindingDirectMail() {
        return BindingBuilder.bind(mailQueue()).to(directExchange()).with(MAIL_BINDING_KEY);
    }
}
