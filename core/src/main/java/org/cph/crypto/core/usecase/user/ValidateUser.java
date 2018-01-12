package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.NotFoundException;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.PasswordEncoder;
import org.cph.crypto.core.spi.UserRepository;

public final class ValidateUser {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ValidateUser(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public final void validateUser(final String userId, final String key) {
		final User user = this.userRepository.findOneUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		if (user.isAllowed()) throw new UserNotFoundException(userId);
		final String validKey = this.passwordEncoder.encode(user.getId() + user.getPassword());
		if (!validKey.equals(key)) throw new NotFoundException(userId);
		user.setAllowed(true);
		this.userRepository.saveUser(user);
	}
}
