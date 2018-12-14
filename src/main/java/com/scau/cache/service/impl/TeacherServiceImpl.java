package com.scau.cache.service.impl;

import com.scau.cache.entity.Teacher;
import com.scau.cache.mapper.TeacherMapper;
import com.scau.cache.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@CacheConfig(cacheNames = {"teacherCache"})
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Cacheable(key = "#t_id",condition = "#t_id != null")
    @Override
    public Teacher getTeacherById(String t_id) {
        Teacher teacher = teacherMapper.getTeacherById(t_id);
        System.out.println("getTeacherById...execute....");
        return teacher;
    }
}
