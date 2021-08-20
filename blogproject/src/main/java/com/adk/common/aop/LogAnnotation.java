package com.adk.common.aop;


import java.lang.annotation.*;

/**
 * aop日志注解
 * 注解开发相关
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String operator() default "";
}
