package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SortDto;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.TestMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
@Service
public class CourseService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    /**
     * 查询
     */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample courseExample = new CourseExample();
         courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course,courseDto);
            courseDtoList.add(courseDto);
        }
        pageDto.setList(courseDtoList);
    }

    /**
     * 保存  id为空则添加，反正修改
     */
    @Transactional
    public void save(CourseDto courseDto){
        Course course = CopyUtil.copy(courseDto, Course.class);
        if(StringUtils.isEmpty(courseDto.getId())){
            this.insert(course);
        }else {
            this.update(course);
        }

        // 批量保存课程分类
        courseCategoryService.saveBatch(course.getId(),courseDto.getCategorys());
    }
    /**
     * 添加
     */
    private void insert(Course course){
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 修改
     */
    private void update(Course course){
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public void delete(String id){
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新课程时长
     * @param courseId
     * @return
     */
    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

    /**
     * 查找课程内容
     */
    public CourseContentDto findContent(String id){
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content==null){
            return null;
        }
        return CopyUtil.copy(content, CourseContentDto.class);
    }
    /**
     * 保存课程内容，包含新增和修改
     */
    public int saveContent(CourseContentDto courseContentDto){
        CourseContent content = CopyUtil.copy(courseContentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if (i==0){
            i = courseContentMapper.insert(content);
        }
        return i;
    }

    /**
     * 排序
     * @param sortDto
     */
    @Transactional
    public void sort(SortDto sortDto) {
        // 修改当前记录的排序值
        myCourseMapper.updateSort(sortDto);

        // 如果排序值变大
        if (sortDto.getNewSort() > sortDto.getOldSort()) {
            myCourseMapper.moveSortsForward(sortDto);
        }

        // 如果排序值变小
        if (sortDto.getNewSort() < sortDto.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortDto);
        }
    }




}
