/**
 * FileName: AdminLoginInterceptor
 * Author:   嘉平十七
 * Date:     2021/1/29 16:48
 * Description: 管理员页面过滤，重定向到登录页面
 */
package com.hunau.competition.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor implements HandlerInterceptor {

    /**
     * 方法前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //判断用户是否登录
        if (request.getSession().getAttribute("adminUser") == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}