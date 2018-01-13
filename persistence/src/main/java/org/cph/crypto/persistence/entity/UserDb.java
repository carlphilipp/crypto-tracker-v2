package org.cph.crypto.persistence.entity;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Role;
import org.cph.crypto.core.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "user")
public final class UserDb {

	@Id
	private String id;
	@Indexed(unique = true)
	private String email;
	private String password;
	private Role role;
	private Currency currency;
	private Double liquidityMovement;
	private Boolean allowed = false;
	@DBRef
	private List<PositionDb> positions;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Double getLiquidityMovement() {
		return liquidityMovement;
	}

	public void setLiquidityMovement(Double liquidityMovement) {
		this.liquidityMovement = liquidityMovement;
	}

	public Boolean getAllowed() {
		return allowed;
	}

	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

	public List<PositionDb> getPositions() {
		return positions;
	}

	public void setPositions(List<PositionDb> positions) {
		this.positions = positions;
	}

	public User toUser() {
		final User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(role);
		user.setCurrency(currency);
		user.setLiquidityMovement(liquidityMovement);
		user.setAllowed(allowed);
		user.setPositions(positions.stream().map(PositionDb::toPosition).collect(Collectors.toList()));

		return user;
	}

	public static UserDb from(User user) {
		final UserDb userDb = new UserDb();
		userDb.setId(user.getId());
		userDb.setEmail(user.getEmail());
		userDb.setPassword(user.getPassword());
		userDb.setRole(user.getRole());
		userDb.setCurrency(user.getCurrency());
		userDb.setLiquidityMovement(user.getLiquidityMovement());
		userDb.setAllowed(user.isAllowed());
		userDb.setPositions(user.getPositions().stream().map(PositionDb::from).collect(Collectors.toList()));
		return userDb;
	}
}
