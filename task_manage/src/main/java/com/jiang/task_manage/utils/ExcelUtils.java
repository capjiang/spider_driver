package com.jiang.task_manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtils {

    /**
     * @param location
     * @param runnable true为返回R标记的数据，false返回所有数据
     * @return
     */
    public static List<Map<String, String>> getInputData(String location, boolean runnable) {
        // 创建工作簿对象
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(new FileInputStream(location));
        } catch (IOException e) {
            log.error(location + " get sheet fail");
            e.printStackTrace();
        }
        // 获取工作簿下sheet的个数
//        int sheetNum = xssfWorkbook.getNumberOfSheets();
        // 只取第一个工作簿
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        int maxCol = sheet.getLastRowNum();
        if (maxCol < 1) {
            return null;
        }
        int maxRow = sheet.getRow(0).getLastCellNum();
        List<Map<String, String>> datas = new ArrayList<>();
        String[] keys = new String[maxRow];     // 属性
        for (int j = 0; j < maxRow; j++) {
            keys[j] = String.valueOf(sheet.getRow(0).getCell(j));
        }

        for (int i = 1; i < maxCol; i++) {
            // R标记判断
            if (runnable && !"R".equals(String.valueOf(sheet.getRow(i).getCell(0)).toUpperCase())) {
                continue;
            }
            Map<String, String> data = new HashMap<>();
            // 第一列为运行标记，不加入
            for (int j = 1; j < maxRow; j++) {
                data.put(keys[j], String.valueOf(sheet.getRow(i).getCell(j)));
            }
            datas.add(data);
        }
        return datas;
    }

}
