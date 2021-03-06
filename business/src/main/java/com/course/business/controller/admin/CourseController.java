package com.course.business.controller.admin;

import com.course.server.domain.Course;
import com.course.server.domain.Test;
import com.course.server.dto.*;
import com.course.server.exception.ValidatorException;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.service.TestService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * @Description:
 * @Author: Taest
 * @CreateDate: 2020/10/28$ 20:40$
 */
@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger Log = LoggerFactory.getLogger(CourseController.class);

    public static final String BUSINESS_NAME = "课程";

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService courseCategoryService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表保存 id为空则添加，反正修改
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto){


         // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 列表删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") String id){
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }

    /**
     * 查找课程下所有的分类
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable("courseId") String courseId){
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    @GetMapping("/find-content/{id}")
    public ResponseDto findContent(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        CourseContentDto contentDto = courseService.findContent(id);
        responseDto.setContent(contentDto);
        return responseDto;
    }

    @PostMapping("/save-content")
    public ResponseDto saveContent(@RequestBody CourseContentDto contentDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.saveContent(contentDto);
        return responseDto;
    }

    @RequestMapping(value = "/sort")
    public ResponseDto sort(@RequestBody SortDto sortDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.sort(sortDto);
        return responseDto;
    }

}
