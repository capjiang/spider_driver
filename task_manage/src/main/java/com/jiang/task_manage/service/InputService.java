package com.jiang.task_manage.service;

import com.jiang.task_manage.entity.Script;

import java.util.List;
import java.util.Map;

public interface InputService {
    List<Map<String, String>> getInputDatas(Script script);
}
