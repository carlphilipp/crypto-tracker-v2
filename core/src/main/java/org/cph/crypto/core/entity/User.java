package org.cph.crypto.core.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String id;
	private String email;
	private String password;
	private Role role = Role.USER;
	private Currency currency;
	private double liquidityMovement;
	private boolean allowed = false;
	private double value;
	private double originalValue;
	private double gain;
	private double gainPercentage;
	private List<Position> positions = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getLiquidityMovement() {
		return liquidityMovement;
	}

	public void setLiquidityMovement(double liquidityMovement) {
		this.liquidityMovement = liquidityMovement;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(double originalValue) {
		this.originalValue = originalValue;
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

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
