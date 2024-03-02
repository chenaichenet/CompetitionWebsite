/**
 * FileName: CompetitionInterceptor
 * Author:   嘉平十七
 * Date:     2021/2/19 8:46
 * Description: 比赛详情页拦截
 */
package com.hunau.competition.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompetitionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/toLogin");
            return false;
        }
        return true;
    }
}