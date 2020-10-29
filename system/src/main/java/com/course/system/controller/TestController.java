package com.course.system.controller;

import com.course.server.domain.Test;
import com.course.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: Taest
 * @CreateDate: 2020/10/28$ 20:40$
 */
@RestController
public class TestController {


    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public List<Test> test(){
        return testService.list();
    }
}
