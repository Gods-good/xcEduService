package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.CourseBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-29 16:22
 */
@Service
public class CourseService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    //查询课程列表
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest){
        if (page<0){
            page = 1;
        }
        if (page<=0){
            size = 10;
        }
        //调用dao
        //设置分页参数
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseListPage = courseBaseMapper.findCourseListPage(courseListRequest);
        //拿到列表数据
        List<CourseInfo> list = courseListPage.getResult();
        //总数
        long total = courseListPage.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }

}
