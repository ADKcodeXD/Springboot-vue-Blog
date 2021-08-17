package com.adk.handler;

import com.adk.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该类用于处理异常
 * 拦截处理controller中的异常
 */
@ControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json数据
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
