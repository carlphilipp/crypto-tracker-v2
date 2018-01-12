package org.cph.crypto.core.utils;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.TickerNotFoundException;
import java.util.List;

public final class UserUtils {

	public static User enrichUser(User user, List<Ticker> tickers) {
		double totalValue = 0.0D;
		double totalOriginalValue = 0.0D;

		for (Position position : user.getPositions()) {
			final Ticker ticker = tickers.stream()
				.filter(t -> t.getId().equals(position.getCurrency1().getCode() + "-" + position.getCurrency2().getCode()))
				.findFirst()
				.orElseThrow(() -> new TickerNotFoundException(position.getCurrency1().getCode() + "-" + position.getCurrency2().getCode()));

			double originalValue = position.getQuantity() * position.getUnitCostPrice();
			double value = position.getQuantity() * ticker.getPrice();
			double gain = value - originalValue;
			double gainPercentage = (value - originalValue) / originalValue;
			position.setOriginalValue(originalValue);
			position.setValue(value);
			position.setGain(gain);
			position.setGainPercentage(gainPercentage);
			position.setLastUpdated(ticker.getLastUpdated());

			totalValue += value;
			totalOriginalValue += originalValue;
		}

		user.setValue(totalValue);
		user.setOriginalValue(totalOriginalValue);
		user.setGain(totalValue - totalOriginalValue);
		user.setGainPercentage((user.getGain() != 0.0)
			? (totalValue - totalOriginalValue) / totalOriginalValue
			: 0.0
		);

		return user;
	}

	private UserUtils() {
	}
}
