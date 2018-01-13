package org.cph.crypto.core.spi;

import org.cph.crypto.core.entity.Ticker;
import java.util.List;
import java.util.Optional;

public interface TickerRepository {

	Optional<Ticker> findOne(String id);

	List<Ticker> findAllById(List<String> ids);

	List<Ticker> findAllOrderByMarketCapDesc();

	Ticker save(Ticker ticker);

	void save(List<Ticker> tickers);

	void deleteAll();
}
