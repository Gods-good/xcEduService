package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @Description
 * @auther Jack
 * @create 2019-06-21 18:47
 */
@Controller
public class CmsPagePreviewController extends BaseController {
    @Autowired
    CmsPageService cmsPageService;
    //接收到页面id
    @RequestMapping("/cms/preview/{pageId}")
    public void  preview(@PathVariable("pageId")String pageid){
        //调用service歌迷页面得到静态化内容
        String content = cmsPageService.getHtmlByPageId(pageid);
        //使用response将静态化内容和响应到浏览器
        ServletOutputStream outputStream = null;
        try {
             outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-type","test/html;charset=utf-8");
        //输出静态化内容
        try {
            outputStream.write(content.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
