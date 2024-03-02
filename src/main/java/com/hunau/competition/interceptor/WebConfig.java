/**
 * FileName: WebConfig
 * Author:   嘉平十七
 * Date:     2021/1/29 16:48
 * Description: 拦截配置
 */
package com.hunau.competition.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理员
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
        //用户页面
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/verCode");
        //比赛详情页面
        registry.addInterceptor(new CompetitionInterceptor())
                .addPathPatterns("/competition/**");
    }
}