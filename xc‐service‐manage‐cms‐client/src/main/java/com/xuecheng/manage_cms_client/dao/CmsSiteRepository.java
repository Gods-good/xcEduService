package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-03 18:55
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
    
}
