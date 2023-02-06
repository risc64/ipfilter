package com.llf.ipfilter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IP过滤注解
 * 运行时有效，方法级
 * @author 李良发
 * @version v1.0.0
 * @since 2023/1/31 14:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IpFilter {
    
}
