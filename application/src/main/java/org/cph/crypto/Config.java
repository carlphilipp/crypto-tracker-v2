package org.cph.crypto;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.spi.*;
import org.cph.crypto.core.usecase.ticker.UpdateTicker;
import org.cph.crypto.email.EmailAdapter;
import org.cph.crypto.template.TemplateAdapter;
import org.cph.crypto.uuid.Jug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Optional;

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

	@Bean
	public UpdateTicker updateTicker(TickerRepository tickerRepository, TickerClient tickerClient) {
		return new UpdateTicker(tickerRepository, tickerClient);
	}

	@Bean
	public TickerRepository tickerRepository() {
		return new TickerRepository() {
			@Override
			public Optional<Ticker> findOne(String id) {
				return Optional.empty();
			}

			@Override
			public List<Ticker> findAllById(List<String> ids) {
				return null;
			}

			@Override
			public List<Ticker> findAllOrderByMarketCapDesc() {
				return null;
			}

			@Override
			public Ticker save(Ticker ticker) {
				return null;
			}

			@Override
			public List<Ticker> save(List<Ticker> tickers) {
				return null;
			}

			@Override
			public void deleteAll() {

			}
		};
	}

	@Bean
	public TickerClient tickerClient() {
		return new TickerClient() {
			@Override
			public List<Ticker> getTickers(Currency currency, List<String> tickers) {
				return null;
			}

			@Override
			public Optional<Ticker> getTicker(Currency currency, String ticker) {
				return Optional.empty();
			}
		};
	}
}
