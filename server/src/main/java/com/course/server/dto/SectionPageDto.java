package com.course.server.dto;

/**
 * @Description:
 * @Author: Taest
 * @CreateDate: 2020/12/2$ 20:42$
 */
public class SectionPageDto extends PageDto{
    private String courseId;
    private String chapterId;

    @Override
    public String toString() {
        return "SectionPageDto{" +
                "courseId='" + courseId + '\'' +
                ", chapterId='" + chapterId + '\'' +
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

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
