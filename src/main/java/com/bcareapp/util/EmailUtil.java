package com.bcareapp.util;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import com.bcareapp.constants.GetEnvironmentFactoryConstant;

public class EmailUtil {
	private static final Logger logger = Logger.getLogger(EmailUtil.class);
	private static String username;
	private static String password;
	private static String smtpHost;
	static {
		try {
			Object environmentInstance = GetEnvironmentFactoryConstant.getEnvironmentInstance();

			Method getAWSRegion = environmentInstance.getClass().getMethod("getEmailUsername");
			Object result = getAWSRegion.invoke(environmentInstance);
			username = (String) result;

			getAWSRegion = environmentInstance.getClass().getMethod("getEmailPassword");
			result = getAWSRegion.invoke(environmentInstance);
			password = (String) result;

			getAWSRegion = environmentInstance.getClass().getMethod("getFromEmail");
			result = getAWSRegion.invoke(environmentInstance);
			getAWSRegion = environmentInstance.getClass().getMethod("getEmailSMTPHost");
			result = getAWSRegion.invoke(environmentInstance);
			smtpHost = (String) result;

		} catch (Exception e) {
			logger.debug("Error while initializing Email Constants", e);
		}

	}

	private static Session session = null;

	private static Session getMailSession() {
		if (session == null) {
			try {
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.host", smtpHost);
				if (smtpHost.equalsIgnoreCase("smtp.gmail.com")) {
					props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				} else {
					props.put("mail.smtp.ssl.trust", "*");
				}
				session = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
			} catch (Exception e) {
				logger.error("Exception occurred while initializing mail", e);
			}
		}
		return session;
	}

	public static boolean sendMail(String subject, String description, String toEmailAddress) {

		if (CommonsDataUtil.isNullOrEmpty(toEmailAddress) || CommonsDataUtil.isNullOrEmpty(subject)
				|| CommonsDataUtil.isNullOrEmpty(description)) {
			logger.warn("Email address not found for sending email");
			return false;
		}
		boolean bool = true;
		try {
			Session session = getMailSession();

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress.trim()));
			message.setSubject(subject);
			message.setContent(description, "text/html; charset=utf-8");
			Transport.send(message);

		} catch (Exception e) {
			logger.error("Error occurred while sending email", e);
			bool = false;
		}
		return bool;
	}

}
