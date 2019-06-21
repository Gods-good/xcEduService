package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    //删除课程图片记录
    //如果大于0表示成功，否则失败
    public long deleteByCourseid(String courseid);
}
