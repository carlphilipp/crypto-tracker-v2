package org.cph.crypto.config;

import org.cph.crypto.client.coinmarketcap.CoinMarketCapAdapter;
import org.cph.crypto.core.spi.*;
import org.cph.crypto.email.EmailAdapter;
import org.cph.crypto.template.TemplateAdapter;
import org.cph.crypto.uuid.Jug;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Value("${context.scheme}")
	private String scheme;

	@Value("${context.host}")
	private String host;

	@Value("${context.port}")
	private String port;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

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

	@Bean
	public TickerClient tickerClient(RestTemplate restTemplate) {
		return new CoinMarketCapAdapter(restTemplate);
	}

	@Bean
	public ContextService contextService() {
		return () -> {
			final String base = scheme + "://" + host;
			return "https".equals(scheme) ? base : base + ":" + port;
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PasswordEncoder passwordEncoder(final BCryptPasswordEncoder bCryptPasswordEncoder) {
		return bCryptPasswordEncoder::encode;
	}
}
