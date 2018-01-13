package org.cph.crypto.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "share_value")
public final class ShareValueDb {
	@Id
	private String id;
	private Long timestamp;
	@Indexed @DBRef UserDb user;
	private Double shareQuantity;
	private Double shareValue;
	private Double portfolioValue;
}
