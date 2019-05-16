package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-04 18:33
 */
@Service
public class CmsConfigService {
    @Autowired
    CmsConfigRepository cmsConfigRepository;

    //根据id查询配置管理数据模型
    public CmsConfigResult getConfigById(String id){
        CmsConfig one = cmsConfigRepository.findOne(id);
        if (one !=null){
            return new CmsConfigResult(CommonCode.SUCCESS,one);
        }
        return new CmsConfigResult(CommonCode.FAIL,null);
    }
}
