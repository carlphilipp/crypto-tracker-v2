package org.cph.crypto.persistence.entity;

import org.cph.crypto.core.entity.ShareValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "share_value")
public final class ShareValueDb {
	@Id
	private String id;
	private Long timestamp;
	@Indexed
	@DBRef
	UserDb user;
	private Double shareQuantity;
	private Double shareValue;
	private Double portfolioValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public UserDb getUser() {
		return user;
	}

	public void setUser(UserDb user) {
		this.user = user;
	}

	public Double getShareQuantity() {
		return shareQuantity;
	}

	public void setShareQuantity(Double shareQuantity) {
		this.shareQuantity = shareQuantity;
	}

	public Double getShareValue() {
		return shareValue;
	}

	public void setShareValue(Double shareValue) {
		this.shareValue = shareValue;
	}

	public Double getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(Double portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public ShareValue toShareValue() {
		final ShareValue shareValue = new ShareValue();
		shareValue.setId(id);
		shareValue.setTimestamp(timestamp);
		shareValue.setUser(user.toUser());
		shareValue.setShareValue(this.shareValue);
		shareValue.setShareQuantity(shareQuantity);
		shareValue.setPortfolioValue(portfolioValue);
		return shareValue;
	}

	public static ShareValueDb from(final ShareValue shareValue) {
		final ShareValueDb shareValueDb = new ShareValueDb();
		shareValueDb.setId(shareValue.getId());
		shareValueDb.setTimestamp(shareValue.getTimestamp());
		shareValueDb.setUser(UserDb.from(shareValue.getUser()));
		shareValueDb.setShareValue(shareValue.getShareValue());
		shareValueDb.setPortfolioValue(shareValue.getPortfolioValue());
		return shareValueDb;
	}
}
