package org.cph.crypto.client.coinmarketcap;

import org.cph.crypto.client.coinmarketcap.internal.Response;
import org.cph.crypto.client.coinmarketcap.internal.TickerMapper;
import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.spi.TickerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CoinMarketCapAdapter implements TickerClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoinMarketCapAdapter.class);

	private final RestTemplate restTemplate;

	public CoinMarketCapAdapter(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Optional<Ticker> getTicker(final Currency currency, final String ticker) {
		LOGGER.debug("Search ticker: {}", ticker);
		return getAllTickers(currency)
			.stream()
			.filter(tick -> tick.getCurrency1().getCode().equals(ticker))
			.findFirst();
	}

	public List<Ticker> getTickers(final Currency currency, final List tickers) {
		LOGGER.debug("Search tickers: {}", tickers);
		return getAllTickers(currency)
			.stream()
			.filter(ticker -> tickers.contains(ticker.getCurrency1().getCode()))
			.collect(Collectors.toList());
	}

	private List<Ticker> getAllTickers(final Currency currency) {
		final UriComponents uriComponents = UriComponentsBuilder.newInstance()
			.scheme("https")
			.host("api.coinmarketcap.com")
			.path("/v1/ticker")
			.queryParam("limit", "0")
			.queryParam("convert", currency.getCode()).build();

		LOGGER.debug("HTTP request: {}", uriComponents.toUri());
		final Response[] responses = this.restTemplate.getForObject(uriComponents.toUri(), Response[].class);
		assert responses != null;
		return Arrays.stream(responses)
			.filter(response -> !response.getId().contains("futures")) //Not sure why two coins can have the same code, so filter out for now
			.map(response -> TickerMapper.responseToTicker(currency, response))
			.filter(ticker -> ticker.getCurrency1() != Currency.UNKNOWN)
			.collect(Collectors.toList());
	}
}
