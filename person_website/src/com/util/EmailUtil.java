package com.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 腾讯企业邮箱发送
 * @author dream
 *
 */
public class EmailUtil {

	 private static Logger logger = Logger.getLogger("EmailUtil.class");    
		
	   //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
	    static class MyAuthenricator extends Authenticator{
	        String u = null;
	        String p = null;
	        public MyAuthenricator(String u,String p){
	            this.u=u;
	            this.p=p;
	        }
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(u,p);
	        }
	    }
	 
	    public static  boolean sendCode(String toeamil,String context,String title){
	    	 boolean flag=false;
	        Properties prop = new Properties();
	        //协议
	        prop.setProperty("mail.transport.protocol", "smtps");
	        //服务器
	        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
	        //端口
	        prop.setProperty("mail.smtp.port", "465");
	        //使用smtp身份验证
	        prop.setProperty("mail.smtp.auth", "true");
	        //使用SSL，企业邮箱必需！
	        //开启安全协议
	        MailSSLSocketFactory sf = null;
	        try {
	            sf = new MailSSLSocketFactory();
	            sf.setTrustAllHosts(true);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        prop.put("mail.smtp.ssl.enable", "true");
	        prop.put("mail.smtp.starttls.enable", "true");
	        prop.put("mail.smtp.ssl.socketFactory", sf);
	        //发件人，进行权限认证
	        Session session = Session.getDefaultInstance(prop, new MyAuthenricator("", ""));//发件人账号、密码
	        session.setDebug(true);
	        MimeMessage mimeMessage = new MimeMessage(session);
	        try {
	            //发件人地址
	            mimeMessage.setFrom(new InternetAddress("",""));//发件人账号，别名；可以设置发件人的别名,不需要就省略
	            //收件人的地址
	            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toeamil));
	            mimeMessage.setSubject(title);
	            mimeMessage.setSentDate(new Date());
	            mimeMessage.setText(context);
	            mimeMessage.saveChanges();
	            Transport.send(mimeMessage);
	            flag=true;
	        } catch (Exception e) {
	        	 flag=false;
	            logger.error("scan 邮件异常 " +e);
	        }
	        return flag;
	    }
	    
	    public static void main(String args[]){

	    }
	
}
