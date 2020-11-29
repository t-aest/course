package com.course.business.controller.admin;

import com.course.server.domain.Section;
import com.course.server.domain.Test;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.SectionService;
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
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger Log = LoggerFactory.getLogger(SectionController.class);

    public static final String BUSINESS_NAME = "小节";

    @Autowired
    private SectionService sectionService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        sectionService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表保存 id为空则添加，反正修改
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto){


        //保存校验

        ResponseDto responseDto = new ResponseDto();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    /**
     * 列表删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") String id){
        ResponseDto responseDto = new ResponseDto();
        sectionService.delete(id);
        return responseDto;
    }
}
