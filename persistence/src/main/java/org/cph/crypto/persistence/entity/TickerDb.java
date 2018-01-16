package org.cph.crypto.persistence.entity;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Ticker;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ticker")
public final class TickerDb {
	@Id
	private String id;
	@Indexed
	private Currency currency1;
	private Currency currency2;
	private Double price;
	private String exchange;
	private Double volume24h;
	private Double marketCap;
	private Double percentChange1h;
	private Double percentChange24h;
	private Double percentChange7d;
	private Long lastUpdated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Currency getCurrency1() {
		return currency1;
	}

	public void setCurrency1(Currency currency1) {
		this.currency1 = currency1;
	}

	public Currency getCurrency2() {
		return currency2;
	}

	public void setCurrency2(Currency currency2) {
		this.currency2 = currency2;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public Double getVolume24h() {
		return volume24h;
	}

	public void setVolume24h(Double volume24h) {
		this.volume24h = volume24h;
	}

	public Double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}

	public Double getPercentChange1h() {
		return percentChange1h;
	}

	public void setPercentChange1h(Double percentChange1h) {
		this.percentChange1h = percentChange1h;
	}

	public Double getPercentChange24h() {
		return percentChange24h;
	}

	public void setPercentChange24h(Double percentChange24h) {
		this.percentChange24h = percentChange24h;
	}

	public Double getPercentChange7d() {
		return percentChange7d;
	}

	public void setPercentChange7d(Double percentChange7d) {
		this.percentChange7d = percentChange7d;
	}

	public Long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Ticker toTicker() {
		final Ticker ticker = new Ticker();
		ticker.setId(id);
		ticker.setCurrency1(currency1);
		ticker.setCurrency2(currency2);
		ticker.setPrice(price);
		ticker.setExchange(exchange);
		ticker.setVolume24h(volume24h);
		ticker.setMarketCap(marketCap);
		ticker.setPercentChange1h(percentChange1h);
		ticker.setPercentChange24h(percentChange24h);
		ticker.setPercentChange7d(percentChange7d);
		ticker.setLastUpdated(lastUpdated);
		return ticker;
	}

	public static TickerDb from(Ticker ticker) {
		final TickerDb tickerDb = new TickerDb();
		tickerDb.setId(ticker.getId());
		tickerDb.setCurrency1(ticker.getCurrency1());
		tickerDb.setCurrency2(ticker.getCurrency2());
		tickerDb.setPrice(ticker.getPrice());
		tickerDb.setExchange(ticker.getExchange());
		tickerDb.setVolume24h(ticker.getVolume24h());
		tickerDb.setMarketCap(ticker.getMarketCap());
		tickerDb.setPercentChange1h(ticker.getPercentChange1h());
		tickerDb.setPercentChange24h(ticker.getPercentChange24h());
		tickerDb.setPercentChange7d(ticker.getPercentChange7d());
		tickerDb.setLastUpdated(ticker.getLastUpdated());
		return tickerDb;
	}
}
