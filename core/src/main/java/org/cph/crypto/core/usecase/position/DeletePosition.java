package org.cph.crypto.core.usecase.position;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.NotAllowedException;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public final class DeletePosition {

	private final UserRepository userRepository;

	public DeletePosition(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public final void deletePosition(final String userId, final String positionId, final double price) {
		final User user = this.userRepository.findOneUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		final List<Position> positions = user.getPositions().stream().filter(position -> positionId.equals(position.getId())).collect(Collectors.toList());
		if (positions.size() == 1) {
			user.setLiquidityMovement(user.getLiquidityMovement() - price);
			this.userRepository.deletePosition(user, positions.get(0));
		} else {
			throw new NotAllowedException();
		}
	}
}
