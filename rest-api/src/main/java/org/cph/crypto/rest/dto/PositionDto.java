package org.cph.crypto.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Position;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated"})
public final class PositionDto {

	private String id;
	private CurrencyDto currency1;
	private CurrencyDto currency2;
	private double quantity;
	private double unitCostPrice;
	private Double originalValue;
	private Double value;
	private Double gain;
	private Double gainPercentage;
	private Long lastUpdated;

	public PositionDto(String id, CurrencyDto currency1, CurrencyDto currency2, double quantity, double unitCostPrice, Double originalValue, Double value, Double gain, Double gainPercentage, Long lastUpdated) {
		this.id = id;
		this.currency1 = currency1;
		this.currency2 = currency2;
		this.quantity = quantity;
		this.unitCostPrice = unitCostPrice;
		this.originalValue = originalValue;
		this.value = value;
		this.gain = gain;
		this.gainPercentage = gainPercentage;
		this.lastUpdated = lastUpdated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CurrencyDto getCurrency1() {
		return currency1;
	}

	public void setCurrency1(CurrencyDto currency1) {
		this.currency1 = currency1;
	}

	public CurrencyDto getCurrency2() {
		return currency2;
	}

	public void setCurrency2(CurrencyDto currency2) {
		this.currency2 = currency2;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getUnitCostPrice() {
		return unitCostPrice;
	}

	public void setUnitCostPrice(double unitCostPrice) {
		this.unitCostPrice = unitCostPrice;
	}

	public Double getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Double originalValue) {
		this.originalValue = originalValue;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	public Double getGainPercentage() {
		return gainPercentage;
	}

	public void setGainPercentage(Double gainPercentage) {
		this.gainPercentage = gainPercentage;
	}

	public Long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public final Position toPosition() {
		Position position = new Position();
		position.setId(id);
		position.setCurrency1(Currency.findCurrency(currency1.getCode()));
		position.setQuantity(quantity);
		position.setUnitCostPrice(unitCostPrice);
		return position;
	}

	public static PositionDto from(final Position position) {
		return new PositionDto(
			position.getId(),
			CurrencyDto.from(position.getCurrency1()),
			CurrencyDto.from(position.getCurrency2()),
			position.getQuantity(),
			position.getUnitCostPrice(),
			position.getOriginalValue(),
			position.getValue(),
			position.getGain(),
			position.getGainPercentage(),
			position.getLastUpdated());
	}
}
