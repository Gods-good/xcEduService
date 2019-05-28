package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-13 15:42
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {

    //将不可预知的异常在map中配置异常所对应的错误代码及信息
    //ImmutableMap是不可变
    private ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //用于构建ImmutableMap的数据
    private static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    //捕获不可预知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult catchException(Exception e){
        if (EXCEPTIONS == null){
            //异常类型和错误代码的map构建成功
            EXCEPTIONS = builder.build();
        }
        //从map中找异常类型所对应的错误代码
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if (resultCode !=null){
            return new ResponseResult(resultCode);
        }
        //否则统一抛出99999异常
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

    //可以捕获异常
    //ExceptionHandler和ControllerAdvice配合起来实现捕获异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        //处理异常
        ResultCode resultCode = e.getResultCode();

        //给用户返回ResponseResult类的信息，以json格式输出
        return new ResponseResult(resultCode);
    }

    static {
        builder.put(org.springframework.http.converter.HttpMessageNotReadableException.class, CommonCode.INVLIDATE);
    }
}