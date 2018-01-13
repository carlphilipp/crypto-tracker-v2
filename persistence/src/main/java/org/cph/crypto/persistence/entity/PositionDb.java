package org.cph.crypto.persistence.entity;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Position;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "position")
public final class PositionDb {
	@Id
	private String id;
	private Currency currency1;
	private Currency currency2;
	private double quantity;
	private double unitCostPrice;

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

	public final Position toPosition() {
		final Position position = new Position();
		position.setId(id);
		position.setCurrency1(currency1);
		position.setCurrency2(currency2);
		position.setQuantity(quantity);
		position.setUnitCostPrice(unitCostPrice);
		return position;
	}


	public static PositionDb from(final Position position) {
		final PositionDb PositionDb = new PositionDb();
		position.setId(position.getId());
		position.setCurrency1(position.getCurrency1());
		position.setCurrency2(position.getCurrency2());
		position.setQuantity(position.getQuantity());
		position.setUnitCostPrice(position.getUnitCostPrice());
		return PositionDb;
	}
}
