package com.xuecheng.manage_cms.web.controllerr;

import com.xuecheng.api.config.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.GenerateHtmlResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-04 18:36
 */
@RestController
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    CmsPageService cmsPageService;
    @Override
    public QueryResponseResult<CmsPage> findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page,size,queryPageRequest);
    }

    @Override
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @Override
    public CmsPageResult findById(@PathVariable("id") String id) {
        return cmsPageService.getById(id);
    }

    @Override
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.update(id,cmsPage);
    }

    @Override
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }

    @Override
    public GenerateHtmlResult generateHtml(@PathVariable("pageId")String pageId) {
        return cmsPageService.generateHtml(pageId);
    }

    @Override
    public GenerateHtmlResult getHtml(@PathVariable("pageId")String pageId) {
        return cmsPageService.getHtml(pageId);
    }
}
