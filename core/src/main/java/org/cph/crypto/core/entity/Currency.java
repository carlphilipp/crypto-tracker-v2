package org.cph.crypto.core.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Currency {
	BTC("BTC", "Bitcoin", "฿", Type.CRYPTO),
	ETH("ETH", "Ethereum", "Ξ", Type.CRYPTO),
	GRS("GRS", "Groestlcoin", "GRS", Type.CRYPTO),
	LTC("LTC", "Litecoin", "Ł", Type.CRYPTO),
	VTC("VTC", "Vertcoin", "VTC", Type.CRYPTO),
	ETHOS("ETHOS", "Ethos", "ETHOS", Type.CRYPTO),
	CARDANO("ADA", "Cardano", "ADA", Type.CRYPTO),
	POWER_LEDGER("POWR", "Power Ledger", "POWR", Type.CRYPTO),
	ICON("ICX", "Icon", "ICX", Type.CRYPTO),
	MONERO("XMR", "Monero", "XMR", Type.CRYPTO),
	NEO("NEO", "NEO", "NEO", Type.CRYPTO),
	EOS("EOS", "EOS", "EOS", Type.CRYPTO),
	STEEM("STEEM", "Steem", "STEEM", Type.CRYPTO),
	KOMODO("KMD", "Komodo", "KMD", Type.CRYPTO),
	ARK("ARK", "Ark", "ARK", Type.CRYPTO),
	WALTON("WTC", "Walton", "WTC", Type.CRYPTO),
	NAV("NAV", "Nav Coin", "NAV", Type.CRYPTO),
	UTRUST("UTK", "Utrust", "UTK", Type.CRYPTO),

	USD("USD", "United States Dollar", "$", Type.FIAT),
	EUR("EUR", "Euro", "€", Type.FIAT),
	UNKNOWN("UNKNOWN", "Unknown", "U", Type.FIAT);

	private String code;
	private String name;
	private String symbol;
	private Type type;

	Currency(final String code, final String name, final String symbol, final Type type) {
		this.code = code;
		this.name = name;
		this.symbol = symbol;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public Type getType() {
		return type;
	}

	public static List<String> cryptoCurrenciesAsListOfString() {
		return Arrays.stream(Currency.values())
			.filter(currency -> currency.getType() == Type.CRYPTO)
			.map(currency -> currency.code)
			.collect(Collectors.toList());
	}

	public static Currency findCurrency(final String code) {
		return Arrays.stream(Currency.values())
			.filter(currency -> currency.code.equals(code))
			.findAny()
			.orElse(UNKNOWN);
	}

	public enum Type {
		FIAT,
		CRYPTO
	}
}
