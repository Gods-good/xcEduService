package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-03 19:01
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
