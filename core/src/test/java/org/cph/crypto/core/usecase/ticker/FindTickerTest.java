package org.cph.crypto.core.usecase.ticker;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.spi.TickerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class FindTickerTest {
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private FindTicker findTicker = new FindTicker(tickerRepository);

	@Test
	void testFindOne() {
		// given
		String tickerId = "BTC-USD";
		Ticker ticker = Utils.getBtcTicker();
		given(tickerRepository.findOne(tickerId)).willReturn(Optional.of(ticker));

		// when
		Ticker actual = findTicker.findOne(tickerId);

		// then
		then(tickerRepository).should().findOne(tickerId);
		assertEquals(actual, ticker);
	}

	@Test
	void testFindAllById() {
		// given
		List<String> tickerIds = Collections.singletonList("BTC-USD");
		Ticker ticker = Utils.getBtcTicker();
		given(tickerRepository.findAllById(tickerIds)).willReturn(Collections.singletonList(ticker));

		// when
		List<Ticker> actual = findTicker.findAllById(tickerIds);

		// then
		then(tickerRepository).should().findAllById(tickerIds);
		assertEquals(actual.get(0), ticker);
	}

	@Test
	void testFindAllOrderByMarketCapDesc() {
		// given
		Ticker ticker = Utils.getBtcTicker();
		given(tickerRepository.findAllOrderByMarketCapDesc()).willReturn(Collections.singletonList(ticker));

		// when
		List<Ticker> actual = findTicker.findAllOrderByMarketCapDesc();

		// then
		then(tickerRepository).should().findAllOrderByMarketCapDesc();
		assertEquals(actual.get(0), ticker);
	}
}
