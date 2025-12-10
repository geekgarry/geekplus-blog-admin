package com.geekplus.common.util.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * author     : geekplus
 * date       : 10/12/23 23:09
 * description: 自定义邮箱发送，使用javax.mail
 */
@Component
public class EmailUtil {

    @Value("${geekplus.email.address}")
    private String emailAddress;

    @Value("${geekplus.email.password}")
    private String password;

    @Value("${geekplus.email.host}")
    private String host;

    @Value("${geekplus.email.port}")
    private String port;

    @Value("${geekplus.email.auth}")
    private String auth;

    @Value("${geekplus.email.tls.enable}")
    private String tlsEnable;

    public void sendEmail(String toSomeOne, String body, String type, String title){

        Properties p = new Properties();
//		session.setDebug(true);
        //设置邮箱传输协议
        p.put("mail.transport.protocol", "smtp");
        //设置邮箱的主机地址
        p.put("mail.smtp.host", host);
        //设置邮箱的端口
        p.put("mail.smtp.port", port);
        //邮箱是否认证
        p.put("mail.smtp.auth", auth);
        //是否开启ssl
		p.put("mail.smtp.starttls.enable", tlsEnable);

        Session session = Session.getInstance(p,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, password);
            }
        });

        //简历邮箱消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            //设置发送者邮箱地址信息
            Address address = new InternetAddress(emailAddress,"GeekPlus","utf-8");
            message.setFrom(address);
            //设置接受者邮箱地址信息
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toSomeOne));
            //设置接受者邮箱地址信息
//			message.setRecipient(RecipientType.CC, new InternetAddress("xxxx@qq.com"));
            //设置接受者邮箱地址信息
//			message.setRecipient(RecipientType.CC, new InternetAddress("xxxx@qq.com"));
            //设置邮件主题信息及编码
            message.setSubject(title,"utf-8");
            //设置邮件的内容编码
            //"text/plain;charset=UTF-8";
            if("html".equals(type)){
                message.setContent(body,"text/html;charset=UTF-8");
            } else if("text".equals(type)) {
                message.setText(body,"utf-8");
            }
            //设置邮件发送日期
            message.setSentDate(new Date());

            //保存邮件的所有信息
            message.saveChanges();

//			OutputStream os = new FileOutputStream("f:/1.eml");
//			 message.writeTo(os);
//			 os.close();
            //开始发送
            Transport.send(message);
            System.out.println("发送邮件");
        } catch ( Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件异常失败");
        }
//		return message;
    }
}
