package org.cph.crypto.core.entity;

public class Position {
	private String id;
	private Currency currency1;
	private Currency currency2;
	private double quantity;
	private double unitCostPrice;
	private double originalValue;
	private double value;
	private double gain;
	private double gainPercentage;
	private long lastUpdated;

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

	public double getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(double originalValue) {
		this.originalValue = originalValue;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public double getGainPercentage() {
		return gainPercentage;
	}

	public void setGainPercentage(double gainPercentage) {
		this.gainPercentage = gainPercentage;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
