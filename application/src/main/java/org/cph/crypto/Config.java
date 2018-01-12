package org.cph.crypto;

import org.cph.crypto.core.spi.EmailService;
import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.core.spi.TemplateService;
import org.cph.crypto.email.EmailAdapter;
import org.cph.crypto.template.TemplateAdapter;
import org.cph.crypto.uuid.Jug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public IdGenerator idGenerator() {
		return new Jug();
	}

	@Bean
	public TemplateService templateService() {
		return new TemplateAdapter();
	}

	@Bean
	public EmailService emailService() {
		return new EmailAdapter();
	}
}
