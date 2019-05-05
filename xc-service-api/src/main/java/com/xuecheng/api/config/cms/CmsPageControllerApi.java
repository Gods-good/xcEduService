package com.xuecheng.api.config.cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-02 21:51
 */
@Api(value = "cms管理页面接口",description = "cms页面管理的接口，提供页面添加、删除、修改、查询操作")
public interface CmsPageControllerApi {

    final String API_PRE = "/cms/page";
    //分页查询
    @ApiOperation(value = "分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",paramType = "path"),
            @ApiImplicitParam(name = "size",value = "每次记录数",paramType = "path")

    })
    @GetMapping(API_PRE+"/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page")int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest);
}
