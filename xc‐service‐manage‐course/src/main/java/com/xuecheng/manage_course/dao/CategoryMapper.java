package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-29 18:45
 */
@Mapper
public interface CategoryMapper {

    //查询分类
    public CategoryNode selectList();
}
