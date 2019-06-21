package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsPage;
import lombok.Data;

/**
 * @Description
 * @auther Jack
 * @create 2019-06-21 19:18
 */
@Data
public class CommonmethodsResult {
    String content;
    CmsPage cmsPage;

    public CommonmethodsResult(String content, CmsPage cmsPage) {
        this.content = content;
        this.cmsPage = cmsPage;
    }

    public CommonmethodsResult() {
    }
}
