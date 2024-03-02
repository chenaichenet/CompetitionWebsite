/**
 * FileName: MailConfig
 * Author:   嘉平十七
 * Date:     2021/2/19 17:56
 * Description: MailUtil工具类读取yml文件属性的配置类
 */
package com.hunau.competition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.templatePath}")
    private String TemplatesPath;


    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemplatesPath() {
        return TemplatesPath;
    }

    public void setTemplatesPath(String templatesPath) {
        TemplatesPath = templatesPath;
    }
}