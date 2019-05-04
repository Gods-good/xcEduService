package com.xuecheng.api.config.cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-02 21:51
 */
public interface CmsPageControllerApi {

    final String API_PRE = "/cms/page";
    //分页查询
    @GetMapping(API_PRE+"/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page")int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest);
}
