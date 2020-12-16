package com.course.file.controller.admin;

import com.course.server.domain.File;
import com.course.server.domain.Test;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.FileService;
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
@RequestMapping("/admin/file")
public class FileController {

    private static final Logger Log = LoggerFactory.getLogger(FileController.class);

    public static final String BUSINESS_NAME = "文件";

    @Autowired
    private FileService fileService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        fileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表保存 id为空则添加，反正修改
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody FileDto fileDto){


         // 保存校验
        ValidatorUtil.require(fileDto.getPath(), "相对路径");
        ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);
        ValidatorUtil.length(fileDto.getKey(), "文件标识", 1, 32);

        ResponseDto responseDto = new ResponseDto();
        fileService.save(fileDto);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    /**
     * 列表删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") String id){
        ResponseDto responseDto = new ResponseDto();
        fileService.delete(id);
        return responseDto;
    }
}
