package org.cph.crypto.core.usecase.sharevalue;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.*;
import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.core.spi.ShareValueRepository;
import org.cph.crypto.core.spi.TickerRepository;
import org.cph.crypto.core.spi.UserRepository;
import org.cph.crypto.core.usecase.position.UpdateShareValue;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class UpdateShareValueTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private ShareValueRepository shareValueRepository = Mockito.mock(ShareValueRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
	private UpdateShareValue updateShareValue = new UpdateShareValue(shareValueRepository, userRepository, tickerRepository, idGenerator);

	@Test
	void testUpdateAllUsersShareValueScenario1() {
		// given
		ArgumentCaptor<ShareValue> shareValueCaptor = ArgumentCaptor.forClass(ShareValue.class);
		User user = Utils.getUser();
		ShareValue oldShareValue = Utils.getShareValue();
		Ticker ticker = new Ticker();
		ticker.setId("ETH-USD");
		ticker.setCurrency1(Currency.ETH);
		ticker.setCurrency2(Currency.USD);
		ticker.setExchange("exchange");
		ticker.setPrice(500.0);
		Position ethPosition = Utils.getEthPosition();
		ethPosition.setQuantity(2);
		ethPosition.setUnitCostPrice(500);
		user.getPositions().add(ethPosition);
		given(userRepository.findAllUsers()).willReturn(singletonList(user));
		given(tickerRepository.findAllById(singletonList("ETH-USD"))).willReturn(singletonList(ticker));
		given(shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)).willReturn(Optional.of(oldShareValue));
		given(idGenerator.getNewId()).willReturn("shareValueId");

		// when
		updateShareValue.updateAllUsersShareValue();

		// then
		then(userRepository).should().findAllUsers();
		then(tickerRepository).should().findAllById(singletonList("ETH-USD"));
		then(shareValueRepository).should().findTop1ByUserOrderByTimestampDesc(user);
		then(shareValueRepository).should().save(shareValueCaptor.capture());
		then(idGenerator).should().getNewId();
		ShareValue shareValueActual = shareValueCaptor.getValue();
		assertAll("share value saved",
			() -> assertNotNull(shareValueActual),
			() -> assertNotNull(shareValueActual.getId()),
			() -> assertEquals(user, shareValueActual.getUser()),
			() -> assertEquals(1000, shareValueActual.getPortfolioValue()),
			() -> assertEquals(100, shareValueActual.getShareValue()),
			() -> assertEquals(10, shareValueActual.getShareQuantity()));
	}

	@Test
	void testUpdateAllUsersShareValueScenario2() {
		// given
		ArgumentCaptor<ShareValue> shareValueCaptor = ArgumentCaptor.forClass(ShareValue.class);
		User user = Utils.getUser();
		ShareValue oldShareValue = Utils.getShareValue();
		Ticker ticker = Utils.getEthTicker();
		Position ethPosition = Utils.getEthPosition();
		ethPosition.setQuantity(2);
		ethPosition.setUnitCostPrice(500);
		user.getPositions().add(ethPosition);
		given(userRepository.findAllUsers()).willReturn(singletonList(user));
		given(tickerRepository.findAllById(singletonList("ETH-USD"))).willReturn(singletonList(ticker));
		given(shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)).willReturn(Optional.of(oldShareValue));
		given(idGenerator.getNewId()).willReturn("shareValueId");

		// when
		updateShareValue.updateAllUsersShareValue();

		// then
		then(userRepository).should().findAllUsers();
		then(tickerRepository).should().findAllById(singletonList("ETH-USD"));
		then(shareValueRepository).should().findTop1ByUserOrderByTimestampDesc(user);
		then(shareValueRepository).should().save(shareValueCaptor.capture());
		then(idGenerator).should().getNewId();
		ShareValue shareValueActual = shareValueCaptor.getValue();
		assertAll("share value saved",
			() -> assertNotNull(shareValueActual),
			() -> assertNotNull(shareValueActual.getId()),
			() -> assertEquals(user, shareValueActual.getUser()),
			() -> assertEquals(2000, shareValueActual.getPortfolioValue()),
			() -> assertEquals(200, shareValueActual.getShareValue()),
			() -> assertEquals(10, shareValueActual.getShareQuantity()));
	}

	@Test
	void testUpdateAllUsersShareValueScenario3() {
		// given
		ArgumentCaptor<ShareValue> shareValueCaptor = ArgumentCaptor.forClass(ShareValue.class);
		User user = Utils.getUser();
		user.setLiquidityMovement(200);
		ShareValue oldShareValue = Utils.getShareValue();
		Ticker tickerEth = Utils.getEthTicker();
		Ticker tickerGrs = Utils.getGrsTicker();
		Position ethPosition = Utils.getEthPosition();
		ethPosition.setQuantity(2);
		ethPosition.setUnitCostPrice(500);
		Position grsPosition = Utils.getGrsPosition();
		user.getPositions().add(ethPosition);
		user.getPositions().add(grsPosition);
		given(userRepository.findAllUsers()).willReturn(singletonList(user));
		given(tickerRepository.findAllById(asList("ETH-USD", "GRS-USD"))).willReturn(asList(tickerEth, tickerGrs));
		given(shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)).willReturn(Optional.of(oldShareValue));
		given(idGenerator.getNewId()).willReturn("shareValueId");

		// when
		updateShareValue.updateAllUsersShareValue();

		// then
		then(userRepository).should().findAllUsers();
		then(tickerRepository).should().findAllById(asList("ETH-USD", "GRS-USD"));
		then(shareValueRepository).should().findTop1ByUserOrderByTimestampDesc(user);
		then(shareValueRepository).should().save(shareValueCaptor.capture());
		then(idGenerator).should().getNewId();
		ShareValue shareValueActual = shareValueCaptor.getValue();
		assertAll("share value saved",
			() -> assertNotNull(shareValueActual),
			() -> assertNotNull(shareValueActual.getId()),
			() -> assertEquals(user, shareValueActual.getUser()),
			() -> assertEquals(2200, shareValueActual.getPortfolioValue()),
			() -> assertEquals(200, shareValueActual.getShareValue()),
			() -> assertEquals(11, shareValueActual.getShareQuantity()));
	}

	@Test
	void testUpdateAllUsersShareValueFirstTimeScenario1() {
		// given
		ArgumentCaptor<ShareValue> shareValueCaptor = ArgumentCaptor.forClass(ShareValue.class);
		User user = Utils.getUser();
		Ticker ticker = Utils.getEthTicker();
		Position ethPosition = Utils.getEthPosition();
		ethPosition.setQuantity(2);
		ethPosition.setUnitCostPrice(500);
		user.getPositions().add(ethPosition);
		given(userRepository.findAllUsers()).willReturn(singletonList(user));
		given(tickerRepository.findAllById(singletonList("ETH-USD"))).willReturn(singletonList(ticker));
		given(shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)).willReturn(Optional.empty());
		given(idGenerator.getNewId()).willReturn("shareValueId");

		// when
		updateShareValue.updateAllUsersShareValue();

		// then
		then(userRepository).should().findAllUsers();
		then(tickerRepository).should().findAllById(singletonList("ETH-USD"));
		then(shareValueRepository).should().findTop1ByUserOrderByTimestampDesc(user);
		then(idGenerator).should(times(2)).getNewId();
		then(shareValueRepository).should(times(2)).save(shareValueCaptor.capture());

		List<ShareValue> shareValueActuals = shareValueCaptor.getAllValues();
		ShareValue firstShareValue = shareValueActuals.get(0);
		assertAll("first share value saved",
			() -> assertNotNull(firstShareValue),
			() -> assertNotNull(firstShareValue.getId()),
			() -> assertEquals(user, firstShareValue.getUser()),
			() -> assertEquals(1000, firstShareValue.getPortfolioValue()),
			() -> assertEquals(100, firstShareValue.getShareValue()),
			() -> assertEquals(10, firstShareValue.getShareQuantity()));
		ShareValue secondShareValue = shareValueActuals.get(1);
		assertAll("second share value saved",
			() -> assertNotNull(secondShareValue),
			() -> assertNotNull(secondShareValue.getId()),
			() -> assertEquals(user, secondShareValue.getUser()),
			() -> assertEquals(2000, secondShareValue.getPortfolioValue()),
			() -> assertEquals(200, secondShareValue.getShareValue()),
			() -> assertEquals(10, secondShareValue.getShareQuantity()));
	}
}
