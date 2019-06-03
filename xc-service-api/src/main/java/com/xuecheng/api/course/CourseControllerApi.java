package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mrt on 2018/6/23.
 */
@Api(value="课程管理的接口",description="课程管理的接口，提供课程添加、删除、修改、查询操作")
public interface CourseControllerApi {
    final String API_PRE = "/course";

    //分页查询课程列表
    @GetMapping(API_PRE+"/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page")int page,
                                                          @PathVariable("size")int size,
                                                          CourseListRequest courseListRequest);

    //新增课程
    @PostMapping(API_PRE+"/coursebase/add")
    public AddCourseResult addCourseBase(@RequestBody CourseBase courseBase);

    //查询课程计划
    @GetMapping(API_PRE+"/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId);

    //添加课程计划
    @PostMapping(API_PRE+"/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan);
}
