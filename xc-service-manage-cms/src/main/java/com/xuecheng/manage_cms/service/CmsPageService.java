package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-04 18:33
 */
@Service
public class CmsPageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 页面列表分页查询
     * @param page 当前页码
     * @param size 页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        //分页查询
        if(page<0){
            page = 1;
        }
        page = page -1 ;
        if(size<0){
            page = 20;
        }

        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        CmsPage cmsPage = new CmsPage();
//        cmsPage.setSiteId("页面");
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        //分页参数
        PageRequest pageRequest = new PageRequest(page,size);
        //执行分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);
        //数据列表集合
        List<CmsPage> content = all.getContent();
        //总记录数
        long total = all.getTotalElements();
        //定义QueryResult
        QueryResult queryResult = new QueryResult();
        queryResult.setList(content);
        queryResult.setTotal(total);
        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
