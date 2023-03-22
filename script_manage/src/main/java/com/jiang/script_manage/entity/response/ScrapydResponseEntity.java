package com.jiang.script_manage.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScrapydResponseEntity {
    private String node_name;
    private String status;
    private String jobid;
}
