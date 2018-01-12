package org.cph.crypto;

import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.uuid.Jug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public IdGenerator idGenerator() {
		return new Jug();
	}
}
