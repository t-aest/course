package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.domain.Test;
import com.course.server.dto.ChapterDto;
import com.course.server.service.ChapterService;
import com.course.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

/**
 * @Description:
 * @Author: Taest
 * @CreateDate: 2020/10/28$ 20:40$
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {


    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/list")
    public List<ChapterDto> list(){
        return chapterService.list();
    }
}
