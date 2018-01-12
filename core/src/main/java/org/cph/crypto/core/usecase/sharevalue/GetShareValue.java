package org.cph.crypto.core.usecase.sharevalue;

import org.cph.crypto.core.entity.ShareValue;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.ShareValueRepository;
import org.cph.crypto.core.spi.UserRepository;
import java.util.List;

public final class GetShareValue {
	private final UserRepository userRepository;
	private final ShareValueRepository shareValueRepository;

	public GetShareValue(final UserRepository userRepository, final ShareValueRepository shareValueRepository) {
		this.userRepository = userRepository;
		this.shareValueRepository = shareValueRepository;
	}

	public final List<ShareValue> findAllShareValue(final String userId) {
		User user = userRepository.findOneUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		return shareValueRepository.findAllByUser(user);
	}
}
