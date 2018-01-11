package org.cph.crypto.core.usecase.position;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.core.spi.UserRepository;
import java.util.Comparator;

public final class AddPosition {
	private final UserRepository userRepository;
	private final IdGenerator idGenerator;

	public AddPosition(final UserRepository userRepository, final IdGenerator idGenerator) {
		this.userRepository = userRepository;
		this.idGenerator = idGenerator;
	}

	public final void addPositionToUser(final String userId, final Position position) {
		User user = userRepository.findOneUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		user.setLiquidityMovement(user.getLiquidityMovement() + position.getQuantity() * position.getUnitCostPrice());
		position.setId(idGenerator.getNewId());
		user.getPositions().add(position);
		user.getPositions().sort(Comparator.comparing(p -> p.getCurrency1().getName()));
		userRepository.savePosition(user, position);
	}
}
