package core;

import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.Role;
import org.cph.crypto.core.entity.User;

public class Utils {
	public static User getUser() {
		final User user = new User();
		//user.setId("userId");
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
}
