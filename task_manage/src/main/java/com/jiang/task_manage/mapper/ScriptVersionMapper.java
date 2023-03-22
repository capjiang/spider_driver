package com.jiang.task_manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.task_manage.entity.ScriptVersion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ScriptVersionMapper extends BaseMapper<ScriptVersion> {
    @Select("select version from script_version where script_id = #{scriptId} and status = 1")
    List<String> selectScriptVersionsById(@Param("scriptId") int scriptId);

    @Update("insert into script_version (script_id, version, status) values (#{scriptId}, #{version}, 1)")
    int insertScriptVersion(@Param("scriptId") int scriptId, @Param("version") String version);
}
