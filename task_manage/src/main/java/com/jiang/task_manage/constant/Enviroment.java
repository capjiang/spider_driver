package com.jiang.task_manage.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Enviroment {
    public static final String DEFAULT_SCRIPT_PROJECT = "scrapyTraining";
    public static final String DEFAULT_SCRIPT_REDIS_KEY = "SCRIPT_INPUT";

    private static String SCRIPT_SPIDER_LOCATION;
    private static String SCRIPT_DEPLOY_LOCATION;
    private static String INPUT_LOCATION;

    // 静态变量无法直接@value
    @Value("${scrapy.spider-location}")
    private void setScriptSpiderLocation(String scriptSpiderLocation) {
        SCRIPT_SPIDER_LOCATION = scriptSpiderLocation;
    }

    @Value("${scrapy.deploy-location}")
    private void setScriptDeployLocation(String scriptDeployLocation) {
        SCRIPT_DEPLOY_LOCATION = scriptDeployLocation;
    }

    @Value("${scrapy.input-location}")
    private void setInputLocation(String inputLocation) {
        INPUT_LOCATION = inputLocation;
    }

    public static String getScriptSpiderLocation() {
        return SCRIPT_SPIDER_LOCATION;
    }

    public static String getScriptDeployLocation() {
        return SCRIPT_DEPLOY_LOCATION;
    }

    public static String getInputLocation() {
        return INPUT_LOCATION;
    }

}
