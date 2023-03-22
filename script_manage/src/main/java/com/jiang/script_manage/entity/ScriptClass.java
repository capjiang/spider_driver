package com.jiang.script_manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScriptClass {
    private int id;
    private String name;
    private int pid;
    private int level;
}
