package com.xuecheng.api.config.cms;

import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by jack on 2019/4/30.
 */
@Api(value="cms页面管理的接口",description="cms页面管理的接口，提供轮播图信息的配置、精品课程的配置及查询操作")
public interface CmsConfigControllerApi {
    final String API_PRE = "/cms/config";

    //根据id查询页面
    @ApiOperation(value="根据id查询页面")
    @GetMapping(API_PRE+"/getmodel/{id}")
    public CmsConfigResult getmodel(@PathVariable("id") String id);

}
