package core.usecase.position;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.PositionNotFoundException;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.UserRepository;
import org.cph.crypto.core.usecase.position.UpdatePosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Optional;
import core.Utils;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UpdatePositionTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private UpdatePosition updatePosition = new UpdatePosition(userRepository);

	@Test
	void testUpdatePositionManualUserNotFound() {
		// given
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position newPosition = Utils.getEthPosition();
		given(userRepository.findOneUserById("userId")).willReturn(Optional.empty());

		// when
		Executable actualExecutable = () -> updatePosition.updatePosition("userId", newPosition, null, null);

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [userId] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testUpdatePositionManual() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position oldPosition = Utils.getEthPosition();
		oldPosition.setQuantity(10);
		oldPosition.setUnitCostPrice(200);
		user.getPositions().add(oldPosition);
		user.setLiquidityMovement(1000);
		Position newPosition = Utils.getEthPosition();
		newPosition.setQuantity(20);
		newPosition.setUnitCostPrice(250);
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		updatePosition.updatePosition("userId", newPosition, null, null);

		// then
		then(userRepository).should().findOneUserById("userId");
		then(userRepository).should().savePosition(userCaptor.capture(), eq(newPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(4000.0, actualUser.getLiquidityMovement());
	}

	@Test
	void testUpdatePositionManualNotFound() {
		// given
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position newPosition = Utils.getEthPosition();
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		Executable actualExecutable = () -> updatePosition.updatePosition("userId", newPosition, null, null);

		// then
		assertThrows(PositionNotFoundException.class, actualExecutable, "Position id [ETH-USD] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testUpdatePositionSmart() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position oldPosition = Utils.getEthPosition();
		user.getPositions().add(oldPosition);
		user.setLiquidityMovement(1000);
		Position newPosition = Utils.getEthPosition();
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		updatePosition.updatePosition("userId", newPosition, 20.0, 250.0);

		// then
		then(userRepository).should().findOneUserById("userId");
		then(userRepository).should().savePosition(userCaptor.capture(), eq(newPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(6000.0, actualUser.getLiquidityMovement());
	}

	@Test
	void testUpdatePositionSmartNotFound() {
		// given
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position newPosition = Utils.getEthPosition();
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		Executable actualExecutable = () -> updatePosition.updatePosition("userId", newPosition, 20.0, 150.0);

		// then
		assertThrows(PositionNotFoundException.class, actualExecutable, "Position id [ETH-USD] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testAddFeeToPosition() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		ArgumentCaptor<Position> positionCaptor = ArgumentCaptor.forClass(Position.class);
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position positionBtc = Utils.getBtcPosition();
		positionBtc.setQuantity(1);
		positionBtc.setUnitCostPrice(4000);
		Position positionEth = Utils.getEthPosition();
		positionEth.setQuantity(20);
		positionEth.setUnitCostPrice(250);
		user.getPositions().add(positionBtc);
		user.getPositions().add(positionEth);
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		updatePosition.addFeeToPosition("userId", "BTC-USD", 0.1);

		// then
		then(userRepository).should().findOneUserById("userId");
		then(userRepository).should().savePosition(userCaptor.capture(), positionCaptor.capture());
		User actualUser = userCaptor.getValue();
		assertAll("user positions",
			() -> assertEquals("BTC-USD", actualUser.getPositions().get(0).getId()),
			() -> assertEquals(0.9, actualUser.getPositions().get(0).getQuantity()),
			() -> assertEquals(4000, actualUser.getPositions().get(0).getUnitCostPrice()),
			() -> assertEquals("ETH-USD", actualUser.getPositions().get(1).getId()));

		Position actualPosition = positionCaptor.getValue();
		assertAll("position to save",
			() -> assertEquals(0.9, actualPosition.getQuantity()),
			() -> assertEquals(4000, actualPosition.getUnitCostPrice()));
	}

	@Test
	void testAddFeeToPositionUserNotFound() {
		// given
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position positionBtc = Utils.getBtcPosition();
		Position positionEth = Utils.getEthPosition();
		user.getPositions().add(positionBtc);
		user.getPositions().add(positionEth);
		given(userRepository.findOneUserById("userId")).willReturn(null);

		// when
		Executable actualExecutable = () -> updatePosition.addFeeToPosition("userId", "BTC-USD", 0.1);

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [userId] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testAddFeeToPositionNotFound() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		Executable actualExecutable = () -> updatePosition.addFeeToPosition("userId", "BTC-USD", 0.1);

		// then
		assertThrows(PositionNotFoundException.class, actualExecutable, "Position id [BTC-USD] not found");
		then(userRepository).should().findOneUserById("userId");
	}
}
