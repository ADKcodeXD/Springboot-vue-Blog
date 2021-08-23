package com.adk.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    用于定义统一的返回值
    返回的状态和头等
    vo包用于封装传送接受的各种参数
    将其封装为一个类 便于操作
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    //是否请求成功状态
    private boolean success;
    //状态码
    private int code;
    //返回信息
    private String msg;
    //返回数据的主体
    private Object data;

    //静态方法，当请求成功时 调用该方法返回给网页
    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
