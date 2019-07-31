package com.xuecheng.manage_course.exception;

import com.xuecheng.framework.exception.ExceptionCatch;
import com.xuecheng.framework.model.response.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice //控制器增强
public class CustomExceptionCatch extends ExceptionCatch {

    //指定本系统中的异常类型所对应的错误代码
    static{
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
        //扩展课程管理自己的异常类型所对应的错误代码...
    }
}
