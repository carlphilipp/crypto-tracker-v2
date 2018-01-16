package org.cph.crypto.persistence;

import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.persistence.entity.TickerDb;
import org.cph.crypto.persistence.repository.TickerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MongoTickerAdapter implements org.cph.crypto.core.spi.TickerRepository {
	private final TickerRepository repository;

	public MongoTickerAdapter(final TickerRepository repository) {
		this.repository = repository;
	}

	public Optional<Ticker> findOne(final String id) {
		return this.repository.findById(id).map(TickerDb::toTicker);
	}


	public List<Ticker> findAllById(List<String> ids) {
		return repository.findByIdIn(ids).stream().map(TickerDb::toTicker).collect(Collectors.toList());
	}


	public List<Ticker> findAllOrderByMarketCapDesc() {
		return repository.findAllByOrderByMarketCapDesc().stream().map(TickerDb::toTicker).collect(Collectors.toList());
	}


	public Ticker save(Ticker ticker) {
		return repository.save(TickerDb.from(ticker)).toTicker();
	}

	public void save(List<Ticker> tickers) {
		repository.saveAll(tickers.stream().map(TickerDb::from).collect(Collectors.toList()));
	}

	public void deleteAll() {
		this.repository.deleteAll();
	}
}
