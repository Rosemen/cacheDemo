package com.scau.cache.controller;

import com.scau.cache.entity.Teacher;
import com.scau.cache.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/getById/{t_id}")
    public Teacher getById(@PathVariable("t_id") String t_id){
        Teacher teacher = teacherService.getTeacherById(t_id);
        return teacher;
    }
}
