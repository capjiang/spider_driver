package com.jiang.task_manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScriptDTO {
    private Integer scriptId;
    private String project;
    private String scope;
    private String name;
    private String version;

    public Script getScript() {
        return new Script(scriptId, project, scope, name);
    }
}
