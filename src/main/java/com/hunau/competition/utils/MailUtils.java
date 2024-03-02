/**
 * FileName: TestMail
 * Author:   嘉平十七
 * Date:     2021/2/8 15:28
 * Description:
 */
package com.hunau.competition.utils;

import com.hunau.competition.config.MailConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MailUtils {
    private final static Logger logger = LoggerFactory.getLogger(MailUtils.class);

    private static String mailHost;
    private static String fromMail;
    private static String fromMailPassword;
    private static String mailTemplate;

    @Autowired
    private MailConfig mailConfig;

    @PostConstruct //Java提供的，该注解被用来修饰一个非静态的void（）方法
    public void init(){
        mailHost = mailConfig.getMailHost();
        fromMail = mailConfig.getUsername();
        fromMailPassword = mailConfig.getPassword();
        mailTemplate = mailConfig.getTemplatesPath();
    }

    //发送邮件
    public static void sendMail(String email, String mailTitle, String mailContent) throws Exception {
        //读取html模板
        String html = MailUtils.readHtmlToString(mailTemplate);
        //写入模板内容
        Document doc = Jsoup.parse(html);
        doc.getElementById("title").html(mailTitle);
        doc.getElementById("content").html(mailContent);
        String content = doc.toString();

        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        // 设置邮件传输协议为SMTP
        props.setProperty("mail.transport.protocol", "SMTP");
        // 设置SMTP服务器地址
        props.setProperty("mail.host", mailHost);
        // 设置SMTP服务器是否需要用户验证，需要验证设置为true
        props.setProperty("mail.smtp.auth", "true");
        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, fromMailPassword);
            }
        };
        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromMail)); // 设置发送者
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
        message.setSubject(mailTitle);  //设置标题
        message.setContent(content, "text/html;charset=utf-8");
        message.setSentDate(new Date());
        message.saveChanges();  //存储邮件信息

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }

    //正则表达式检查邮件
    public static boolean checkMail(String email) {
        Pattern regex = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    /**
     * 读取html文件转为String
     * @param htmlFileName 模板文件相对路径
     * @return 字符串
     * @throws Exception
     */
    public static String readHtmlToString(String htmlFileName) throws Exception {
        InputStream is = null;
        Reader reader = null;
        try {
            is = MailUtils.class.getClassLoader().getResourceAsStream(htmlFileName);
            if (is == null){
                throw new Exception("未找到邮件模板文件");
            }
            reader = new InputStreamReader(is,"UTF-8");
            StringBuilder sb = new StringBuilder();
            int bufferSize = 1024;
            char[] buffer = new char[bufferSize];
            int length = 0;
            while ((length = reader.read(buffer,0,bufferSize)) != -1)
                sb.append(buffer,0,length);
            return sb.toString();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("关闭io流异常", e);
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("关闭io流异常", e);
            }
        }


    }

    public static void main(String[] args) {
        try {
            sendMail("","thymeleaf中文文档","https://fanlychie.github.io/post/thymeleaf.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}