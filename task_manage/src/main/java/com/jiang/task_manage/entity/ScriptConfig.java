package com.jiang.task_manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("script_config")
public class ScriptConfig {

    private Integer scriptId;
    private Integer priority;
    private Integer status;     // 0为未检查，1为已完成
    private Boolean autoStatus;     // 自动状态转化

}
