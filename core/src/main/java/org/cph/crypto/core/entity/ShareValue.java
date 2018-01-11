package org.cph.crypto.core.entity;

public class ShareValue {
	private String id;
	private long timestamp;
	private User user;
	private double shareQuantity;
	private double shareValue;
	private double portfolioValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getShareQuantity() {
		return shareQuantity;
	}

	public void setShareQuantity(double shareQuantity) {
		this.shareQuantity = shareQuantity;
	}

	public double getShareValue() {
		return shareValue;
	}

	public void setShareValue(double shareValue) {
		this.shareValue = shareValue;
	}

	public double getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(double portfolioValue) {
		this.portfolioValue = portfolioValue;
	}
}
