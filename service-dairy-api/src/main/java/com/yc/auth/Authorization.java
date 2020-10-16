package com.yc.auth;

import java.lang.annotation.*;

/**
 * 用户登陆校验注解
 * <p>
 * 访问接口前将会校验用户登陆信息
 *
 * @author YC
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {

    boolean orgRequired() default true;

}
