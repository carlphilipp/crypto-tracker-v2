package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.PasswordEncoder;
import org.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class ValidateUserTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
	private ValidateUser validateUser = new ValidateUser(userRepository, passwordEncoder);

	@Test
	void testValidateUser() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		user.setId("userId");
		user.setAllowed(false);
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));
		given(passwordEncoder.encode("userIdpassword")).willReturn("key");

		// when
		validateUser.validateUser("userId", "key");

		// then
		then(userRepository).should().findOneUserById("userId");
		then(userRepository).should().saveUser(userCaptor.capture());
		then(passwordEncoder).should().encode("userIdpassword");
		User actualUser = userCaptor.getValue();
		assertTrue(actualUser.isAllowed());
	}

	@Test
	void testValidateUserNotFound() {
		// given
		given(userRepository.findOneUserById("userId")).willReturn(Optional.empty());

		// when
		Executable actualExecutable = () -> validateUser.validateUser("userId", "key");

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [userId] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testValidateUserNotAllowed() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));

		// when
		Executable actualExecutable = () -> validateUser.validateUser("userId", "key");

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [userId] not found");
		then(userRepository).should().findOneUserById("userId");
	}
}
