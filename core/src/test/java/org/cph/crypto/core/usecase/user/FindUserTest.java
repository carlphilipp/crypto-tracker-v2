package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.spi.TickerRepository;
import org.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class FindUserTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private FindUser findUser = new FindUser(userRepository, tickerRepository);

	@Test
	void testFindOneUserWithPositions() {
		// given
		Ticker btcTicker = Utils.getBtcTicker();
		btcTicker.setPrice(10000);
		btcTicker.setLastUpdated(1L);
		Ticker ethTicker = Utils.getEthTicker();
		ethTicker.setPrice(100);
		ethTicker.setLastUpdated(2L);
		Position btcPosition = Utils.getBtcPosition();
		Position ethPosition = Utils.getEthPosition();
		List<Position> positions = Arrays.asList(btcPosition, ethPosition);
		User user = Utils.getUser();
		user.setPositions(positions);
		given(userRepository.findOneUserById("id")).willReturn(Optional.of(user));
		given(tickerRepository.findAllById(Arrays.asList("BTC-USD", "ETH-USD"))).willReturn(Arrays.asList(btcTicker, ethTicker));

		// when
		User actual = findUser.findOne("id");

		// then
		then(userRepository).should().findOneUserById("id");
		then(tickerRepository).should().findAllById(Arrays.asList("BTC-USD", "ETH-USD"));
		assertAll("User",
			() -> assertEquals(user, actual),
			() -> assertEquals(11000.0, actual.getValue()),
			() -> assertEquals(7000.0, actual.getOriginalValue()),
			() -> assertEquals(4000.0, actual.getGain()),
			() -> assertEquals(0.5714285714285714, actual.getGainPercentage()));
		assertAll("Position BTC",
			() -> assertEquals(10000.0, actual.getPositions().get(0).getValue()),
			() -> assertEquals(5000.0, actual.getPositions().get(0).getOriginalValue()),
			() -> assertEquals(5000.0, actual.getPositions().get(0).getGain()),
			() -> assertEquals(1.0, actual.getPositions().get(0).getGainPercentage()),
			() -> assertEquals(1L, actual.getPositions().get(0).getLastUpdated()));
		assertAll("Position ETH",
			() -> assertEquals(1000.0, actual.getPositions().get(1).getValue()),
			() -> assertEquals(2000.0, actual.getPositions().get(1).getOriginalValue()),
			() -> assertEquals(-1000.0, actual.getPositions().get(1).getGain()),
			() -> assertEquals(-0.5, actual.getPositions().get(1).getGainPercentage()),
			() -> assertEquals(2L, actual.getPositions().get(1).getLastUpdated()));
	}

	@Test
	void testFindOneUserNoPositions() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("id")).willReturn(Optional.of(user));

		// when
		User actual = findUser.findOne("id");

		// then
		then(userRepository).should().findOneUserById("id");
		assertAll("User",
			() -> assertEquals(user, actual),
			() -> assertEquals(0.0, actual.getValue()),
			() -> assertEquals(0.0, actual.getOriginalValue()),
			() -> assertEquals(0.0, actual.getGain()),
			() -> assertEquals(0.0, actual.getGainPercentage()));
	}

	@Test
	void testFindAll() {
		// given
		User user = Utils.getUser();
		given(userRepository.findAllUsers()).willReturn(Collections.singletonList(user));

		// when
		List<User> actual = findUser.findAll();

		// then
		then(userRepository).should().findAllUsers();
		assertAll("User",
			() -> assertEquals(user, actual.get(0)),
			() -> assertEquals(0.0, actual.get(0).getValue()),
			() -> assertEquals(0.0, actual.get(0).getOriginalValue()),
			() -> assertEquals(0.0, actual.get(0).getGain()),
			() -> assertEquals(0.0, actual.get(0).getGainPercentage()));
	}
}

