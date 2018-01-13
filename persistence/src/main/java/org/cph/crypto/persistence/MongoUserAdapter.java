package org.cph.crypto.persistence;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.persistence.entity.UserDb;
import org.cph.crypto.persistence.repository.PositionRepository;
import org.cph.crypto.persistence.repository.UserRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class MongoUserAdapter implements org.cph.crypto.core.spi.UserRepository {
	private final UserRepository userRepository;
	private final PositionRepository positionRepository;

	public MongoUserAdapter(UserRepository userRepository, PositionRepository positionRepository) {
		this.userRepository = userRepository;
		this.positionRepository = positionRepository;
	}

	public Optional<User> findOneUserByEmail(String email) {
		return userRepository.findOneByEmail(email).map(UserDb::toUser);
	}

	public Optional<User> findOneUserById(String id) {
		return userRepository.findById(id).map(UserDb::toUser);
	}

	public List<User> findAllUsers() {
		List<UserDb> users = this.userRepository.findAll();
		Iterable $receiver$iv = (Iterable) var10000;
		Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
		Iterator var4 = $receiver$iv.iterator();

		while (var4.hasNext()) {
			Object item$iv$iv = var4.next();
			UserDB user = (UserDB) item$iv$iv;
			User var11 = user.toUser();
			destination$iv$iv.add(var11);
		}

		return (List) destination$iv$iv;
	}

	public User saveUser(User user) {
		return ((UserDB) this.userRepository.save(UserDB.Companion.from(user))).toUser();
	}

	public Position savePosition(User user, Position position) {
		Position savedPosition = ((PositionDB) this.positionRepository.save(PositionDB.Companion.from(position))).toPosition();
		this.saveUser(user);
		return savedPosition;
	}

	public void deletePosition(User user, Position position) {
		user.getPositions().remove(position);
		this.positionRepository.delete((Serializable) position.getId());
		this.userRepository.save(UserDB.Companion.from(user));
	}

	public void deleteAllPositions() {
		this.positionRepository.deleteAll();
	}

	public void deleteAllUsers() {
		this.userRepository.deleteAll();
	}
}
