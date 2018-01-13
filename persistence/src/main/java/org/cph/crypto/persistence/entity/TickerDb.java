package org.cph.crypto.persistence.entity;

import org.cph.crypto.core.entity.Currency;
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
}
