package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
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
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        //分页查询
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            page = 20;
        }

        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        //判断如果传入站点id，讲站点id赋值到cmsPage查询值对象
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

//        cmsPage.setSiteId("页面");
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //分页参数
        PageRequest pageRequest = new PageRequest(page, size);
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
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    ///新增页面
    public CmsPageResult add(CmsPage cmsPage) {
        if(cmsPage == null){
            //校验，抛出异常
        }
        //校验页面是否重复，根据页面名称、站点id、页面web访问路径判断此页面是否重复
        //根据页面名称、站点id、页面web访问路径查询，如果查询到了说明页面已存在
        CmsPage cmsPage_l = cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(),
                cmsPage.getPageName(),
                cmsPage.getPageWebPath());
        if(cmsPage_l!=null){
            //抛出具体的异常。。
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTS);
        }
        //测试系统异常代码
//        int i=1/0;
        //将主键设置为空
        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);
        if(save!=null){
            //返回成功
            return new CmsPageResult(CommonCode.SUCCESS,save);
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL,null);

    }

    //根据id查询页面
    public CmsPageResult findByid(String id) {
         CmsPage one = cmsPageRepository.findOne(id);
        if (one != null) {
            return new CmsPageResult(CommonCode.SUCCESS, one);
        }
        return new CmsPageResult(CommonCode.FAIL, one);
    }

    //更新页面
    public CmsPageResult update(String id, CmsPage cmsPage) {
        //根据id查询页面信息
        CmsPage one = cmsPageRepository.findOne(id);
        if (one != null) {
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新类型
            one.setPageType(cmsPage.getPageType());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //执行更新
            CmsPage save = cmsPageRepository.save(one);
            if (save !=null){
                //返回成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }
    //删除页面
    public ResponseResult delete(String id){

        CmsPage one = cmsPageRepository.findOne(id);

        if (one!= null){
            cmsPageRepository.delete(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);

    }
}
