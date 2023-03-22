package com.jiang.task_manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ScriptVersion {
    @TableId
    private int scriptVersionId;
    private int scriptId;
    private String version;
    private int status;
}
