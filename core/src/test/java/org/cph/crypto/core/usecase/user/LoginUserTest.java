package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.NotAllowedException;
import org.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class LoginUserTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private LoginUser loginUser = new LoginUser(userRepository);

	@Test
	void testLoginUser() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserByEmail("username")).willReturn(Optional.of(user));

		// when
		User actual = loginUser.login("username");

		// then
		then(userRepository).should().findOneUserByEmail("username");
		assertNotNull(actual);
	}

	@Test
	void testLoginUserNotFound() {
		// given
		given(userRepository.findOneUserByEmail("username")).willReturn(Optional.empty());

		// when
		Executable actualExecutable = () -> loginUser.login("username");

		// then
		assertThrows(NotAllowedException.class, actualExecutable);
		then(userRepository).should().findOneUserByEmail("username");
	}

	@Test
	void testLoginUserNotAllowed() {
		// given
		User user = Utils.getUser();
		user.setAllowed(false);
		given(userRepository.findOneUserByEmail("username")).willReturn(Optional.of(user));

		// when
		Executable actualExecutable = () -> loginUser.login("username");

		// then
		assertThrows(NotAllowedException.class, actualExecutable);
		then(userRepository).should().findOneUserByEmail("username");
	}
}
