package com.xuecheng.learning.service;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.framework.domain.learning.response.LearningCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.learning.client.SearchClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LearningService {

    @Autowired
    SearchClient searchClient;

    //学生在学习页面点击课程计划，请求此方法获取视频地址
    public GetMediaResult getmedia(String courseId, String teachplanId) {

        //校验该学生是否有该课程的学习权限
        //....

        //远程调用search搜索服务
        TeachplanMediaPub getmedia = searchClient.getmedia(teachplanId);
        if(getmedia == null || StringUtils.isEmpty(getmedia.getMediaUrl())){
            //无法获取视频地址
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_NOTFOUNDVIDEOURL);
        }
        //视频地址
        String mediaUrl = getmedia.getMediaUrl();
        GetMediaResult getMediaResult = new GetMediaResult();
        getMediaResult.setFileUrl(mediaUrl);
        return getMediaResult;

    }
}
