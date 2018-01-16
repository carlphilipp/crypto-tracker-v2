package org.cph.crypto.persistence;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.persistence.entity.PositionDb;
import org.cph.crypto.persistence.entity.UserDb;
import org.cph.crypto.persistence.repository.PositionRepository;
import org.cph.crypto.persistence.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MongoUserAdapter implements org.cph.crypto.core.spi.UserRepository {
	private final UserRepository userRepository;
	private final PositionRepository positionRepository;

	public MongoUserAdapter(final UserRepository userRepository, final PositionRepository positionRepository) {
		this.userRepository = userRepository;
		this.positionRepository = positionRepository;
	}

	public Optional<User> findOneUserByEmail(final String email) {
		return userRepository.findOneByEmail(email).map(UserDb::toUser);
	}

	public Optional<User> findOneUserById(final String id) {
		return userRepository.findById(id).map(UserDb::toUser);
	}

	public List<User> findAllUsers() {
		return this.userRepository.findAll().stream().map(UserDb::toUser).collect(Collectors.toList());
	}

	public User saveUser(final User user) {
		return this.userRepository.save(UserDb.from(user)).toUser();
	}

	public Position savePosition(final User user, final Position position) {
		Position savedPosition = this.positionRepository.save(PositionDb.from(position)).toPosition();
		saveUser(user);
		return savedPosition;
	}

	public void deletePosition(final User user, final Position position) {
		user.getPositions().remove(position);
		this.positionRepository.deleteById(position.getId());
		this.userRepository.save(UserDb.from(user));
	}

	public void deleteAllPositions() {
		this.positionRepository.deleteAll();
	}

	public void deleteAllUsers() {
		this.userRepository.deleteAll();
	}
}
