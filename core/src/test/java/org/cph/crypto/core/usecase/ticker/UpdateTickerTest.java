package org.cph.crypto.core.usecase.ticker;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.spi.TickerClient;
import org.cph.crypto.core.spi.TickerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UpdateTickerTest {

	private TickerClient tickerClient = Mockito.mock(TickerClient.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private UpdateTicker updateTicker = new UpdateTicker(tickerRepository, tickerClient);

	@Test
	void testUpdateAll() {
		// given
		Ticker ticker = Utils.getBtcTicker();
		given(tickerClient.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())).willReturn(Collections.singletonList(ticker));

		// when
		updateTicker.updateAll();

		// then
		then(tickerClient).should().getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString());
		then(tickerRepository).should().save(Collections.singletonList(ticker));
	}
}
