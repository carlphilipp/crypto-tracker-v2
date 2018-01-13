package org.cph.crypto.config;

import org.cph.crypto.client.coinmarketcap.CoinMarketCapAdapter;
import org.cph.crypto.core.spi.EmailService;
import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.core.spi.TemplateService;
import org.cph.crypto.core.spi.TickerClient;
import org.cph.crypto.email.EmailAdapter;
import org.cph.crypto.template.TemplateAdapter;
import org.cph.crypto.uuid.Jug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

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
}
