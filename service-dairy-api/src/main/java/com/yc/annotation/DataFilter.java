/**
 * Copyright (c) 2020
 */

package com.yc.annotation;

import java.lang.annotation.*;

/**
 * 数据过滤注解
 *
 * @author YC
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
    /**
     * 表的别名
     */
    String tableAlias() default "";

    /**
     * 查询条件前缀，可选值有：[where、and]
     */
    String prefix() default "";

    /**
     * 用户ID
     */
    String userId() default "creator";

    /**
     * 部门ID
     */
    String deptId() default "dept_id";

}