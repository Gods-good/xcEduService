package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-03 19:01
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称、站点id、页面web访问路径查询
    CmsPage findBySiteIdAndPageNameAndPageWebPath(String pageName,String siteId,String pageWebPath);
}
