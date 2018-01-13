package org.cph.crypto.email;

import org.cph.crypto.core.entity.Email;
import org.cph.crypto.core.spi.EmailService;
import org.cph.crypto.email.config.EmailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAdapter implements EmailService {

	private static Logger LOGGER = LoggerFactory.getLogger(EmailAdapter.class);
	private static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	private final EmailProperties emailProperties;

	public EmailAdapter() {
		emailProperties = EmailConfig.INSTANCE.getEmailProperties();
	}

	public EmailAdapter(final EmailProperties emailProperties) {
		this.emailProperties = emailProperties;
	}

	public void sendWelcomeEmail(final Email email) {
		new Thread(() -> {
			Properties props = new Properties();
			props.put("mail.smtp.host", emailProperties.getServer().getHost());
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", emailProperties.getServer().getPort());
			props.put("mail.smtp.socketFactory.port", emailProperties.getServer().getPort());
			props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.ssl.enable", true);
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailProperties.getEmail().getUsername(), emailProperties.getEmail().getPassword());
				}
			});
			session.setDebug(false);
			MimeMessage msg = new MimeMessage(session);
			try {
				InternetAddress addressFrom = new InternetAddress(emailProperties.getEmail().getFrom());
				msg.setFrom(addressFrom);
				InternetAddress[] addressTo = new InternetAddress[]{new InternetAddress(email.getTo())};
				msg.setRecipients(Message.RecipientType.TO, addressTo);
				msg.setSubject(email.getSubject());
				msg.setContent(email.getContent(), "text/html");
				msg.setSentDate(new Date());
				Transport.send(msg);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}).start();
	}
}
