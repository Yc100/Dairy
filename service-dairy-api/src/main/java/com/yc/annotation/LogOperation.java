/**
 * Copyright (c) 2020
 */

package com.yc.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author YC
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
	String value() default "";
}
