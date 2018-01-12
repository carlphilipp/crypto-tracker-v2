package org.cph.crypto.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.cph.crypto.core.entity.ShareValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "timestamp", "shareQuantity", "shareValue", "originalValue", "gain", "gainPercentage", "positions"})
public class ShareValueDto {
	private String id;
	private long timestamp;
	private double shareQuantity;
	private double shareValue;
	private double portfolioValue;

	public ShareValueDto(final String id, final long timestamp, final double shareQuantity, final double shareValue, final double portfolioValue) {
		this.id = id;
		this.timestamp = timestamp;
		this.shareQuantity = shareQuantity;
		this.shareValue = shareValue;
		this.portfolioValue = portfolioValue;
	}

	public String getId() {
		return id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public double getShareQuantity() {
		return shareQuantity;
	}

	public double getShareValue() {
		return shareValue;
	}

	public double getPortfolioValue() {
		return portfolioValue;
	}

	public static ShareValueDto from(ShareValue shareValue) {
		return new ShareValueDto(shareValue.getId(), shareValue.getTimestamp(), shareValue.getShareQuantity(), shareValue.getShareValue(), shareValue.getPortfolioValue());
	}
}
