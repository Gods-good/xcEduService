package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by mrt on 2018/6/23.
 */
@Api(value="课程管理的接口",description="课程管理的接口，提供课程添加、删除、修改、查询操作")
public interface CourseControllerApi {
    final String API_PRE = "/course";

    //分页查询课程列表
    @GetMapping(API_PRE+"/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page,
                                                          @PathVariable("size") int size,
                                                          CourseListRequest courseListRequest);
}
