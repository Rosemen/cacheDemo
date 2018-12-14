package com.scau.cache.mapper;

import com.scau.cache.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {
    @Select("select * from t_teacher where t_id = #{t_id}")
    Teacher getTeacherById(String t_id);
}
