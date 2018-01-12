package org.cph.crypto.core.usecase.position;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.NotAllowedException;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import org.cph.crypto.core.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class DeletePositionTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private DeletePosition deletePosition = new DeletePosition(userRepository);

	@Test
	void testDeletePosition() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position position1 = Utils.getBtcPosition();
		position1.setId("positionId");
		user.getPositions().add(position1);
		user.getPositions().add(Utils.getBtcPosition());
		given(userRepository.findOneUserById("id")).willReturn(Optional.of(user));

		// when
		deletePosition.deletePosition("id", "positionId", 10);

		// then
		then(userRepository).should().findOneUserById("id");
		then(userRepository).should().deletePosition(userCaptor.capture(), eq(position1));
		assertEquals(-10, userCaptor.getValue().getLiquidityMovement());
	}

	@Test
	void testDeletePositionRunTime() {
		// given
		User user = Utils.getUser();
		Position position1 = Utils.getBtcPosition();
		position1.setId("positionId");
		user.getPositions().add(position1);
		user.getPositions().add(Utils.getBtcPosition());
		given(userRepository.findOneUserById("id")).willReturn(Optional.of(user));

		// when
		deletePosition.deletePosition("id", "positionId", 0);

		// then
		then(userRepository).should().findOneUserById("id");
		then(userRepository).should().deletePosition(user, position1);
	}

	@Test
	void testDeletePositionUserNotFound() {
		// given
		given(userRepository.findOneUserById("id")).willReturn(Optional.empty());

		// when
		Executable actualExecutable = () -> deletePosition.deletePosition("id", "positionId", 0);

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [id] not found");
		then(userRepository).should().findOneUserById("id");
	}

	@Test
	void testDeletePositionNotFound() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("id")).willReturn(Optional.of(user));

		// when
		Executable actualExecutable = () -> deletePosition.deletePosition("id", "positionId", 0);

		// then
		assertThrows(NotAllowedException.class, actualExecutable);
		then(userRepository).should().findOneUserById("id");
	}
}
