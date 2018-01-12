package org.cph.crypto.core.utils;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.entity.User;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TestUserUtils {

	@Test
	void testEnrichUser() {
		// given
		Ticker tickerEth = Utils.getEthTicker();
		Ticker tickerGrs = Utils.getGrsTicker();
		tickerGrs.setPrice(5);
		User user = Utils.getUser();
		Position ethPosition = Utils.getEthPosition();
		ethPosition.setUnitCostPrice(500);
		ethPosition.setQuantity(2);
		Position grsPosition = Utils.getGrsPosition();
		grsPosition.setQuantity(200);
		grsPosition.setUnitCostPrice(1);
		user.getPositions().add(ethPosition);
		user.getPositions().add(grsPosition);

		// when
		User actual = UserUtils.enrichUser(user, Arrays.asList(tickerEth, tickerGrs));

		// then
		assertAll("Validate enrich user data",
			() -> assertNotNull(actual),
			() -> assertEquals(3000, actual.getValue()),
			() -> assertEquals(1200, actual.getOriginalValue()),
			() -> assertEquals(1800, actual.getGain()),
			() -> assertEquals(1.5, actual.getGainPercentage())
		);
		Position ethPositionModified = actual.getPositions().get(0);
		Position grsPositionModified = actual.getPositions().get(1);
		assertAll("Validate enrich user positions data",
			() -> assertEquals(1000, ethPositionModified.getOriginalValue()),
			() -> assertEquals(2000, ethPositionModified.getValue()),
			() -> assertEquals(1000, ethPositionModified.getGain()),
			() -> assertEquals(1, ethPositionModified.getGainPercentage()),
			() -> assertEquals(200, grsPositionModified.getOriginalValue()),
			() -> assertEquals(1000, grsPositionModified.getValue()),
			() -> assertEquals(800, grsPositionModified.getGain()),
			() -> assertEquals(4, grsPositionModified.getGainPercentage())
		);
	}
}
