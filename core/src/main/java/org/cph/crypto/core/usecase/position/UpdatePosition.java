package org.cph.crypto.core.usecase.position;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.PositionNotFoundException;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.UserRepository;
import java.util.Comparator;

public final class UpdatePosition {
	private final UserRepository userRepository;

	public UpdatePosition(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public final void updatePosition(final String userId, final Position position, final Double transactionQuantity, final Double transactionUnitCostPrice) {
		User user = this.userRepository.findOneUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		if (transactionQuantity != null && transactionUnitCostPrice != null) {
			this.updatePositionSmart(user, position, transactionQuantity, transactionUnitCostPrice);
		} else {
			this.updatePositionManual(user, position);
		}
	}

	public final void addFeeToPosition(final String userId, final String positionId, final double fee) {
		final User user = this.userRepository.findOneUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		final Position position = user.getPositions()
			.stream()
			.filter(p -> positionId.equals(p.getId()))
			.findFirst()
			.orElseThrow(() -> new PositionNotFoundException(positionId));

		final Position newPosition = new Position();
		newPosition.setId(position.getId());
		newPosition.setCurrency1(position.getCurrency1());
		newPosition.setCurrency2(position.getCurrency2());
		newPosition.setQuantity(position.getQuantity() - fee);
		newPosition.setUnitCostPrice(position.getUnitCostPrice());

		user.getPositions().remove(position);
		user.getPositions().add(newPosition);
		user.getPositions().sort(Comparator.comparing(p -> p.getCurrency1().getName()));
		this.userRepository.savePosition(user, newPosition);
	}

	private void updatePositionManual(final User user, final Position position) {
		final Position positionFound = user.getPositions()
			.stream().filter(p -> p.getId().equals(position.getId()))
			.findFirst()
			.orElseThrow(() -> new PositionNotFoundException(position.getId()));

		user.setLiquidityMovement(user.getLiquidityMovement() + (position.getUnitCostPrice() * position.getQuantity() - (positionFound.getUnitCostPrice() * positionFound.getQuantity())));
		userRepository.savePosition(user, position);
	}

	private void updatePositionSmart(final User user, final Position position, final double transactionQuantity, final double transactionUnitCostPrice) {
		user.getPositions()
			.stream().filter(p -> p.getId().equals(position.getId()))
			.findFirst()
			.orElseThrow(() -> new PositionNotFoundException(position.getId()));

		user.setLiquidityMovement(user.getLiquidityMovement() + transactionUnitCostPrice * transactionQuantity);
		this.userRepository.savePosition(user, position);
	}
}
