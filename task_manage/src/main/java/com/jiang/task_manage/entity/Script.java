package com.jiang.task_manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jiang.task_manage.constant.Enviroment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Script {
    @TableId(type = IdType.AUTO)
    private Integer scriptId;
    private String project;
    private String scope;
    private String name;

    private String getInputRedisKey() {
        return Enviroment.DEFAULT_SCRIPT_REDIS_KEY + ":";
    }

    public String getDeployLocation() {
        return project + "/" + scope + "/" + name + ".py";
    }

    public String getSpiderLocation(String version) {
        return project + "/" + scope + "/" + name + "_" + version + ".py";
    }

    public String getInputLocation() {
        return project + "/" + scope + "/" + name + ".xlsx";
    }

    public String getSpider() {
        return project + "_" + scope + "_" + name;
    }


}
