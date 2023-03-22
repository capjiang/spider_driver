package com.jiang.task_manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("script_task")
public class Task {

    @TableId(type = IdType.AUTO)
    private int taskId;
    private String jobid;
    private String spider;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private int status;     // 0为未运行，1为完成，2为异常

}
