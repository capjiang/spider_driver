package com.jiang.script_manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Script {
    @TableId
    private Integer scriptId;
    private String project;
    private String scope;
    private String name;

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
