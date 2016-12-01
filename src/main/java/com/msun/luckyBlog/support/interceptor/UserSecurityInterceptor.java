/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.msun.luckyBlog.persistence.domain.Info;

/**
 * 用户登录后台管理的拦截器 存在该用户session则允许通过，否则返回登录页面
 * 
 * @author zxc Dec 1, 2016 6:38:46 PM
 */
@Component
public class UserSecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("cur_user");
        if (obj == null || !(obj instanceof Info)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
