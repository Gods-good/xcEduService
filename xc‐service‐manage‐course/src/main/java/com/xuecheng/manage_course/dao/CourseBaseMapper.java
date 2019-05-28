package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Description
 * @auther Jack
 * @create 2019-05-28 17:22
 */
@Mapper
public interface CourseBaseMapper {
    CourseBase findCourseBaseById(String Id);
}
