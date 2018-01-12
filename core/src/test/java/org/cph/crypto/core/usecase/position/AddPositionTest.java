package org.cph.crypto.core.usecase.position;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.Currency;
import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class AddPositionTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
	private AddPosition addPosition = new AddPosition(userRepository, idGenerator);

	@Test
	void testAddPosition() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position ethPosition = Utils.getEthPosition();
		user.getPositions().add(ethPosition);

		Position btcPosition = Utils.getBtcPosition();
		given(userRepository.findOneUserById("id")).willReturn(Optional.of(user));
		given(idGenerator.getNewId()).willReturn(("positionId"));

		// when
		addPosition.addPositionToUser("id", btcPosition);

		// then
		then(userRepository).should().findOneUserById("id");
		then(idGenerator).should().getNewId();
		then(userRepository).should().savePosition(userCaptor.capture(), eq(btcPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(5000.0, actualUser.getLiquidityMovement());
		assertEquals(Currency.BTC, actualUser.getPositions().get(0).getCurrency1());
		assertEquals("positionId", actualUser.getPositions().get(0).getId());
		assertEquals(Currency.ETH, actualUser.getPositions().get(1).getCurrency1());
	}

	@Test
	void testAddPositionUserNotFound() {
		// given
		Position btcPosition = Utils.getBtcPosition();
		given(userRepository.findOneUserById("id")).willReturn(Optional.empty());

		// when
		Executable actualExecutable = () -> addPosition.addPositionToUser("id", btcPosition);

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [id] not found");
		then(userRepository).should().findOneUserById("id");
	}
}
