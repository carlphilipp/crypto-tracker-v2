package org.cph.crypto.core;

import org.cph.crypto.core.entity.*;

public class Utils {
	public static User getUser() {
		final User user = new User();
		user.setEmail("email");
		user.setPassword("password");
		user.setRole(Role.USER);
		user.setCurrency(Currency.USD);
		user.setLiquidityMovement(0);
		user.setAllowed(true);
		return user;
	}

	public static Position getEthPosition() {
		final Position position = new Position();
		position.setId("ETH-USD");
		position.setCurrency1(Currency.ETH);
		position.setCurrency2(Currency.USD);
		position.setQuantity(10.0);
		position.setUnitCostPrice(200.0);
		return position;
	}

	public static Position getBtcPosition() {
		final Position position = new Position();
		position.setId("BTC-USD");
		position.setCurrency1(Currency.BTC);
		position.setCurrency2(Currency.USD);
		position.setQuantity(1.0);
		position.setUnitCostPrice(5000.0);
		return position;
	}

	public static Position getGrsPosition() {
		final Position position = new Position();
		position.setId("GRS-USD");
		position.setCurrency1(Currency.GRS);
		position.setCurrency2(Currency.USD);
		position.setQuantity(200);
		position.setUnitCostPrice(1);
		return position;
	}

	public static ShareValue getShareValue() {
		final ShareValue shareValue = new ShareValue();
		shareValue.setId("shareValueId");
		shareValue.setPortfolioValue(0);
		shareValue.setUser(getUser());
		shareValue.setShareQuantity(10);
		shareValue.setPortfolioValue(100);
		shareValue.setTimestamp(1000);
		return shareValue;
	}

	public static Ticker getEthTicker() {
		final Ticker ticker = new Ticker();
		ticker.setId("ETH-USD");
		ticker.setCurrency1(Currency.ETH);
		ticker.setCurrency2(Currency.USD);
		ticker.setPrice(1000);
		ticker.setExchange("exchange");
		return ticker;
	}

	public static Ticker getBtcTicker() {
		final Ticker ticker = new Ticker();
		ticker.setId("BTC-USD");
		ticker.setCurrency1(Currency.BTC);
		ticker.setCurrency2(Currency.USD);
		ticker.setPrice(1000);
		ticker.setExchange("exchange");
		return ticker;
	}

	public static Ticker getGrsTicker() {
		final Ticker ticker = new Ticker();
		ticker.setId("GRS-USD");
		ticker.setCurrency1(Currency.GRS);
		ticker.setCurrency2(Currency.USD);
		ticker.setPrice(1);
		ticker.setExchange("exchange");
		return ticker;
	}
}
