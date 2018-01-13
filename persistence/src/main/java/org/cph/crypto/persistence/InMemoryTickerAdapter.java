package org.cph.crypto.persistence;

import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.spi.TickerRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryTickerAdapter implements TickerRepository {

	private Map<String, Ticker> inMemoryDb = new HashMap<>();

	@Override
	public Optional<Ticker> findOne(final String id) {
		return Optional.ofNullable(inMemoryDb.get(id));
	}

	@Override
	public List<Ticker> findAllById(final List<String> ids) {
		return ids.stream().map(id -> inMemoryDb.get(id)).collect(Collectors.toList());
	}

	@Override
	public List<Ticker> findAllOrderByMarketCapDesc() {
		return inMemoryDb.values()
			.stream()
			.sorted((ticker1, ticker2) -> (int) (ticker1.getMarketCap() - ticker2.getMarketCap()))
			.collect(Collectors.toList());
	}

	@Override
	public Ticker save(final Ticker ticker) {
		inMemoryDb.put(ticker.getId(), ticker);
		return ticker;
	}

	@Override
	public void save(final List<Ticker> tickers) {
		tickers.forEach(this::save);
	}

	@Override
	public void deleteAll() {
		inMemoryDb.clear();
	}
}
