package com.zjf.toolkit;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
* 邮件多人发送，可设置发送，抄送，密送
* @author zhutongyu
*/
public final class MailKit {

	private MailKit() {}

	public static final String PROTOCOL = "smtp";
	public static final String AUTH = "true";
	public static final String HOST = "smtp.mxhichina.com";
	public static final String PORT = "25";
	public static final String USERNAME = "hushirui@chenengdai.com";
	public static final String PASSWORD = "Hh199383";

	public static void send(String from, String to[], String cs[], String ms[], String subject, String content, String archievs[]) throws Exception {
		try {
			// Mail属性
			Properties properties = new Properties();
			properties.setProperty("mail.transport.protocol", PROTOCOL);
			properties.setProperty("mail.smtp.auth", AUTH);
			properties.setProperty("mail.smtp.host", HOST);
			properties.setProperty("mail.smtp.port", PORT);
			properties.setProperty("mail.smtp.username", USERNAME);
			properties.setProperty("mail.smtp.password", PASSWORD);
			// 创建会话
			Session session = Session.getInstance(properties); // System.getProperties()
			// 创建信息
			Message message = new MimeMessage(session);
			BodyPart bodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			// 发件人
			message.setFrom(new InternetAddress(from));
			// 发送
			if (to != null) {
				Address[] tos = toInternetAddress(to);
				message.setRecipients(Message.RecipientType.TO, tos);
			}
			// 抄送
			if (cs != null) {
				Address[] ccs = toInternetAddress(cs);
				message.setRecipients(Message.RecipientType.CC, ccs);
			}
			// 密送
			if (ms != null) {
				Address[] bccs = toInternetAddress(ms);
				message.setRecipients(Message.RecipientType.BCC, bccs);
			}
			// 发送日期
			message.setSentDate(new Date());
			// 主题
			message.setSubject(subject);
			// 内容
			message.setText(content);
			// 显示以HTML格式的文本内容
			bodyPart.setContent(content, "text/html;charset=GBK");
			multipart.addBodyPart(bodyPart);
			// 添加多个附件
			if (archievs != null) {
				addTach(archievs, multipart);
			}
			message.setContent(multipart);
			// 邮件服务器进行验证
			Transport transport = session.getTransport(PROTOCOL);
			transport.connect(HOST, USERNAME, PASSWORD);
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Mail sent Failed");
		}
	}

	// 添加附件
	public static void addTach(String fileList[], Multipart multipart) throws MessagingException, UnsupportedEncodingException {
		for (int index = 0; index < fileList.length; index++) {
			MimeBodyPart mailArchieve = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileList[index]);
			mailArchieve.setDataHandler(new DataHandler(fds));
			mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "GBK", "B"));
			multipart.addBodyPart(mailArchieve);
		}
	}

	private static InternetAddress[] toInternetAddress(String[] array) throws Exception {
		InternetAddress[] addresses = new InternetAddress[array.length];
		for (int i = 0; i < array.length; i++) {
			addresses[i] = new InternetAddress(array[i]);
		}
		return addresses;
	}

	public static void main(String args[]) {
		try {
			String from = "hushirui@chenengdai.com";
			String[] to = { "houjun@chenengdai.com", "qianyun@chenengdai.com" };
			String[] cs = { "yangbeiyan@chenengdai.com" };
			String[] ms = { "hushirui@chenengdai.com" };
			String subject = "邮件主题 - 仅作测试, 无需回复";
			String content = "邮件内容 - 仅作测试, 无需回复";
			String[] archievs = null;
			MailKit.send(from, to, cs, ms, subject, content, archievs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}