package com.yc.auth;

import com.yc.common.constant.Constant;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.exception.UnAuthorizationException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录校验
 *
 * @author YC
 */
@Component("authorizationInterceptor")
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            Authorization authorization = getAuthorization((HandlerMethod) handler);
            //authorization 不为空，需要用户登陆，校验用户信息
            if (!ObjectUtils.isEmpty(authorization)) {
                SysUserEntity user = (SysUserEntity) request.getSession().getAttribute(Constant.SYSTEM_USER_KEYWORD);
                if (ObjectUtils.isEmpty(user)) {
                    throw new UnAuthorizationException("未登录的用户");
                }
                return true;
            }
            return true;
        }
        return true;
    }

    /**
     * 获取hander上的Authorization注解
     *
     * @param handler
     * @return
     */
    private Authorization getAuthorization(HandlerMethod handler) {
        Authorization authorization = handler.getMethodAnnotation(Authorization.class);
        if (ObjectUtils.isEmpty(authorization)) {
            authorization = AnnotatedElementUtils.getMergedAnnotation(handler.getBean().getClass(), Authorization.class);
        }
        return authorization;
    }
}
