package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-03 18:55
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

}
