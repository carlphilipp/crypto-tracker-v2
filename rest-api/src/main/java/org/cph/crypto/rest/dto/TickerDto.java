package org.cph.crypto.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.cph.crypto.core.entity.Ticker;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "currency1", "currency2", "price", "exchange", "volume24h", "marketCap", "percentChange1h", "percentChange24h", "percentChange7d", "lastUpdated"})
public class TickerDto {
	private String id;
	private CurrencyDto currency1;
	private CurrencyDto currency2;
	private double price;
	private String exchange;
	private double volume24h;
	private double marketCap;
	private double percentChange1h;
	private double percentChange24h;
	private double percentChange7d;
	private long lastUpdated;

	public TickerDto(String id, CurrencyDto currency1, CurrencyDto currency2, double price, String exchange, double volume24h, double marketCap, double percentChange1h, double percentChange24h, double percentChange7d, long lastUpdated) {
		this.id = id;
		this.currency1 = currency1;
		this.currency2 = currency2;
		this.price = price;
		this.exchange = exchange;
		this.volume24h = volume24h;
		this.marketCap = marketCap;
		this.percentChange1h = percentChange1h;
		this.percentChange24h = percentChange24h;
		this.percentChange7d = percentChange7d;
		this.lastUpdated = lastUpdated;
	}

	public String getId() {
		return id;
	}

	public CurrencyDto getCurrency1() {
		return currency1;
	}

	public CurrencyDto getCurrency2() {
		return currency2;
	}

	public double getPrice() {
		return price;
	}

	public String getExchange() {
		return exchange;
	}

	public double getVolume24h() {
		return volume24h;
	}

	public double getMarketCap() {
		return marketCap;
	}

	public double getPercentChange1h() {
		return percentChange1h;
	}

	public double getPercentChange24h() {
		return percentChange24h;
	}

	public double getPercentChange7d() {
		return percentChange7d;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public static TickerDto from(final Ticker ticker) {
		return new TickerDto(ticker.getId(), CurrencyDto.from(ticker.getCurrency1()), CurrencyDto.from(ticker.getCurrency2()), ticker.getPrice(), ticker.getExchange(), ticker.getVolume24h(), ticker.getMarketCap(), ticker.getPercentChange1h(), ticker.getPercentChange24h(), ticker.getPercentChange7d(), ticker.getLastUpdated());
	}
}
