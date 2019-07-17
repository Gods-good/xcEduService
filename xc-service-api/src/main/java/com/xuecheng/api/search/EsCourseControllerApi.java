package com.xuecheng.api.search;


import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value="课程搜索接口",description="课程搜索接口")
public interface EsCourseControllerApi {

    final String API_PRE = "/search/course";
    //根据配置信息id查询配置信息
    @GetMapping(API_PRE+"/list/{page}/{size}")
    public QueryResponseResult<CoursePub> list(@PathVariable("page") int page,
                                               @PathVariable("size") int size,
                                               CourseSearchParam courseSearchParam);

}
