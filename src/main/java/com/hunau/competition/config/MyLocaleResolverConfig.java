/**
 * FileName: LocaleResolverConfig
 * Author:   嘉平十七
 * Date:     2021/2/7 21:41
 * Description: 语言环境解析配置类
 */
package com.hunau.competition.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolverConfig implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String l = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();    //默认的
        if (!StringUtils.isEmpty(l)){
            String[] split = l.split("_");//分割zh和CN
            locale=new Locale(split[0],split[1]);//一个语言，一个国家
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}