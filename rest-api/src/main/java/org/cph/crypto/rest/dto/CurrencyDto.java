package org.cph.crypto.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.cph.crypto.core.entity.Currency;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "currencyName", "symbol", "type"})
public class CurrencyDto {
	private String code;
	private String currencyName;
	private String symbol;
	private String type;

	public CurrencyDto(final String code, final String currencyName, final String symbol, final String type) {
		this.code = code;
		this.currencyName = currencyName;
		this.symbol = symbol;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public final Currency toCurrency() {
		return Currency.findCurrency(this.code);
	}

	public static CurrencyDto from(final Currency currency) {
		return new CurrencyDto(
			currency.getCode(),
			currency.getName(),
			currency.getSymbol(),
			currency.getType().toString());
	}
}
