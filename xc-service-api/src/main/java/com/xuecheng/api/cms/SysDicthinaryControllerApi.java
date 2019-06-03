package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by jack on 2019/6/1.
 */
@Api(value="数据字典的管理",description="提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {

    final String API_PRE = "/sys/dictionary";

    //查询
    @ApiOperation(value = "数据字典查询接口")
    @GetMapping(API_PRE+"/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type);
}
