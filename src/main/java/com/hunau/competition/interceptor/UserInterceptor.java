/**
 * FileName: UserInterceptor
 * Author:   嘉平十七
 * Date:     2021/2/6 15:05
 * Description: 用户过滤，重定向到登录页面
 */
package com.hunau.competition.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/toLogin");
            return false;
        }
        return true;
    }
}