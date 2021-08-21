package com.adk.common.Cache;


import java.lang.annotation.*;

/**
 * 缓存工具类
 * 注解
 * 使用aop增强进行实现
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    long expire() default 1*60*1000;

    String name() default "";
}
