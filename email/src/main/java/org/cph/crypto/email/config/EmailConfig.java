package org.cph.crypto.email.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.cph.crypto.email.EmailProperties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public final class EmailConfig {
	private static final EmailProperties emailProperties;
	public static final EmailConfig INSTANCE;

	public final EmailProperties getEmailProperties() {
		return emailProperties;
	}

	static {
		try {
			INSTANCE = new EmailConfig();
			final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			emailProperties = mapper.readValue(EmailConfig.class.getClassLoader().getResourceAsStream("email.yaml"), EmailProperties.class);
			final String decryptedPassword = INSTANCE.decryptPassword(emailProperties.getEmail().getPassword());
			emailProperties.getEmail().setPassword(decryptedPassword);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String decryptPassword(final String password) {
		final StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
		final RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		final String argument = runtimeMxBean.getInputArguments()
			.stream()
			.filter(s -> s.contains("jasypt.encryptor.password"))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("jasypt.encryptor.password not found"));

		final String jasyptPassword = argument.substring(argument.indexOf("=") + 1);
		stringEncryptor.setPassword(jasyptPassword);
		return stringEncryptor.decrypt(password);
	}

	private EmailConfig() {
	}
}
