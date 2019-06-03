package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @auther Jack
 * @create 2019-06-01 9:55
 */
@Service
public class SysDicthinaryService {
    @Autowired
    SysDicthinaryRepository sysDicthinaryRepository;
    //根据字典分类type查询字典信息
    public SysDictionary findDictionaryByType(String type){
        return sysDicthinaryRepository.findBydType(type);
    }
}
