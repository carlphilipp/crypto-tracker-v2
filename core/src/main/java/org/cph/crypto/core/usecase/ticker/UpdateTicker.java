package org.cph.crypto.core.usecase.ticker;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.spi.TickerClient;
import org.cph.crypto.core.spi.TickerRepository;

public class UpdateTicker {
	private final TickerRepository tickerRepository;
	private final TickerClient client;

	public UpdateTicker(final TickerRepository tickerRepository, final TickerClient client) {
		this.tickerRepository = tickerRepository;
		this.client = client;
	}

	public final void updateAll() {
		tickerRepository.save(this.client.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString()));
	}
}
