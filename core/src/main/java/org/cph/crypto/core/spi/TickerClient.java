package org.cph.crypto.core.spi;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Ticker;
import java.util.List;
import java.util.Optional;

public interface TickerClient {

	List<Ticker> getTickers(Currency currency, List<String> tickers);

	Optional<Ticker> getTicker(Currency currency, String ticker);
}
