package org.cph.crypto.config;

import org.cph.crypto.core.spi.TickerRepository;
import org.cph.crypto.persistence.InMemoryTickerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

	@Bean
	public TickerRepository tickerRepository() {
		return new InMemoryTickerAdapter();
	}
}
