package com.xuecheng.manage_cms.web.controllerr;

import com.xuecheng.api.config.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-04 18:36
 */
@RestController
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    CmsConfigService cmsConfigService;

    @Override
    public CmsConfigResult getmodel(@PathVariable("id") String id) {
        return cmsConfigService.getConfigById(id);
    }
}
