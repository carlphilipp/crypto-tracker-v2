package org.cph.crypto.core.usecase.sharevalue;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.ShareValueRepository;
import org.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class GetShareValueTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private ShareValueRepository shareValueRepository = Mockito.mock(ShareValueRepository.class);
	private GetShareValue getShareValue = new GetShareValue(userRepository, shareValueRepository);

	@Test
	void testFindAllShareValue() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		user.setId("userId");
		given(userRepository.findOneUserById("userId")).willReturn(Optional.of(user));
		given(shareValueRepository.findAllByUser(user)).willReturn((new ArrayList<>()));

		// when
		getShareValue.findAllShareValue("userId");

		// then
		then(userRepository).should().findOneUserById("userId");
		then(shareValueRepository).should().findAllByUser(userCaptor.capture());
		assertEquals("userId", userCaptor.getValue().getId());
	}

	@Test
	void testFindAllShareValueUserNotFound() {
		// given
		User user = Utils.getUser();
		user.setId("userId");
		given(userRepository.findOneUserById("userId")).willReturn(Optional.empty());

		// when
		Executable actualExecutable = () -> getShareValue.findAllShareValue("userId");

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [userId] not found");
		then(userRepository).should().findOneUserById("userId");
	}
}
