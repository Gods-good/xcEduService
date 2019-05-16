package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-16 15:47
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}

