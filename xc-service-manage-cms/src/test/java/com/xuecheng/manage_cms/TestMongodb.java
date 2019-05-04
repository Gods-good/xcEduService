package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-03 19:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMongodb {

    @Autowired
    CmsPageRepository cmsPageRepository;

    //查询cmspase
    @Test
    public void testFindList(){

//        CmsPage one = cmsPageRepository.findOne("5a754adf6abb500ad05688d9");
//        System.out.println(one);
//
        //分页查询
        int page = 0;
        int size =10;
        PageRequest pageRequest = new PageRequest(page,size);
        //条件参数
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
//        //定义实体类
//        CmsPage cmsPage = new CmsPage();
//        cmsPage.setSiteId("s01");
//        cmsPage.setTemplateId("t01");
//        cmsPage.setPageName("测试页面");
//        cmsPage.setPageCreateTime(new Date());
//        List<CmsPageParam> cmsPageParams = new ArrayList<>();
//        CmsPageParam cmsPageParam = new CmsPageParam();
//        cmsPageParam.setPageParamName("param1");
//        cmsPageParam.setPageParamValue("value1");
//        cmsPageParams.add(cmsPageParam);
//        cmsPage.setPageParams(cmsPageParams);
//        cmsPageRepository.save(cmsPage);
    }

}
