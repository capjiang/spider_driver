package com.jiang.task_manage.service.impl;

import com.jiang.task_manage.constant.Enviroment;
import com.jiang.task_manage.entity.Script;
import com.jiang.task_manage.service.InputService;
import com.jiang.task_manage.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InputServiceImpl implements InputService {

    @Override
    public List<Map<String, String>> getInputDatas(Script script) {
        String inputLocation = Enviroment.getInputLocation() + "/" + script.getInputLocation();
        List<Map<String, String>> inputData = ExcelUtils.getInputData(inputLocation, true);
        return inputData;
    }
}
