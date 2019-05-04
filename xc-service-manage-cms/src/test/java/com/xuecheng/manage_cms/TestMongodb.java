package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        CmsPage one = cmsPageRepository.findOne("5a754adf6abb500ad05688d9");
        System.out.println(one);

    }
}
