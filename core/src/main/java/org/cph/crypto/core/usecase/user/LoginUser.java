package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.NotAllowedException;
import org.cph.crypto.core.spi.UserRepository;

public final class LoginUser {
	private final UserRepository userRepository;

	public LoginUser(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public final User login(final String username) {
		final User user = this.userRepository.findOneUserByEmail(username).orElseThrow(NotAllowedException::new);
		if (!user.isAllowed()) throw new NotAllowedException();
		return user;
	}
}
