package com.course.server.dto;

/**
 * @Description:
 * @Author: Taest
 * @CreateDate: 2020/12/2$ 20:13$
 */
public class ChapterPageDto extends PageDto{
    private String courseId;

    @Override
    public String toString() {
        return "ChapterPageDto{" +
                "courseId='" + courseId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
