package com.adk.utils;

import com.adk.pojo.SysUser;

/**
 * 这是一个用于存放对象的一个类
 * 能够将拿到的对象长期存放在这当中，
 * 我们可以快速获取该对象
 */
public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL=new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
