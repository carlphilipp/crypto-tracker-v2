package org.cph.crypto.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.cph.crypto.core.entity.User;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "email", "currency", "value", "originalValue", "gain", "gainPercentage", "positions"})
public class UserDto {
	private String id;
	private String email;
	@JsonIgnore
	private String password;
	private CurrencyDto currency;
	private Double value;
	private Double originalValue;
	private Double gain;
	private Double gainPercentage;
	private List positions;

	public final User toUser() {
		final User user = new User();
		user.setId(this.id);
		user.setEmail(email);
		user.setPassword(password);
		user.setCurrency(currency.toCurrency());
		return user;
	}

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

	public CurrencyDto getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDto currency) {
		this.currency = currency;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Double originalValue) {
		this.originalValue = originalValue;
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

	public List getPositions() {
		return positions;
	}

	public void setPositions(List positions) {
		this.positions = positions;
	}

	public static UserDto from(final User user) {
		final UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setValue(user.getValue());
		userDto.setOriginalValue(user.getOriginalValue());
		userDto.setGain(user.getGain());
		userDto.setGainPercentage(user.getGainPercentage());
		userDto.setPositions(user.getPositions().stream().map(PositionDto::from).collect(Collectors.toList()));
		userDto.setCurrency(CurrencyDto.from(user.getCurrency()));
		return userDto;
	}
}
