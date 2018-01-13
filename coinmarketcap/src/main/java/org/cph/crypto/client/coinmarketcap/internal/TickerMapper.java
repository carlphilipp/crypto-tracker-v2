package org.cph.crypto.client.coinmarketcap.internal;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TickerMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(TickerMapper.class);

	public static Ticker responseToTicker(final Currency currency, final Response response) {
		try {
			double price = (response.getPriceUsd() == null) ? 0 : Double.parseDouble(response.getPriceUsd());
			double volume24h = (response.get_24hVolumeUsd() == null) ? 0.0 : Double.parseDouble(response.get_24hVolumeUsd());
			double marketCap = (response.getMarketCapUsd() == null) ? 0.0 : Double.parseDouble(response.getMarketCapUsd());
			double percentChange1h = (response.getPercentChange1h() == null) ? 0.0 : Double.parseDouble(response.getPercentChange1h());
			double percentChange24h = (response.getPercentChange24h() == null) ? 0.0 : Double.parseDouble(response.getPercentChange24h());
			double percentChange7d = (response.getPercentChange7d() == null) ? 0.0 : Double.parseDouble(response.getPercentChange7d());
			long lastUpdated = (response.getLastUpdated() == null) ? 0L : Long.parseLong(response.getLastUpdated());

			final Ticker ticker = new Ticker();
			ticker.setId(response.getSymbol() + "-" + currency.getCode());
			ticker.setCurrency1(Currency.findCurrency(response.getSymbol()));
			ticker.setCurrency2(currency);
			ticker.setPrice(price);
			ticker.setExchange("coinmarketcap");
			ticker.setVolume24h(volume24h);
			ticker.setMarketCap(marketCap);
			ticker.setPercentChange1h(percentChange1h);
			ticker.setPercentChange24h(percentChange24h);
			ticker.setPercentChange7d(percentChange7d);
			ticker.setLastUpdated(lastUpdated);
			return ticker;
		} catch (Exception ex) {
			LOGGER.error("Error while processing: {}", response, ex);
			throw ex;
		}
	}

	private TickerMapper() {
	}
}
