package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDicthinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @auther Jack
 * @create 2019-06-01 9:54
 */
@RestController
public class SysDicthinaryController implements SysDicthinaryControllerApi {
    @Autowired
    SysDicthinaryService sysDicthinaryService;
    @Override
    public SysDictionary getByType(@PathVariable("type") String type) {
        return sysDicthinaryService.findDictionaryByType(type);
    }
}
