package org.cph.crypto.core.usecase.ticker;

import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.exception.TickerNotFoundException;
import org.cph.crypto.core.spi.TickerRepository;
import java.util.List;

public final class FindTicker {
	private final TickerRepository tickerRepository;

	public FindTicker(final TickerRepository tickerRepository) {
		this.tickerRepository = tickerRepository;
	}

	public final Ticker findOne(final String id) {
		return tickerRepository.findOne(id).orElseThrow(() -> new TickerNotFoundException(id));
	}


	public final List<Ticker> findAllById(final List<String> ids) {
		return tickerRepository.findAllById(ids);
	}

	public final List<Ticker> findAllOrderByMarketCapDesc() {
		return tickerRepository.findAllOrderByMarketCapDesc();
	}
}
