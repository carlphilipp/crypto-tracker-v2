package org.cph.crypto.core.spi;

import org.cph.crypto.core.entity.Position;
import org.cph.crypto.core.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

	Optional<User> findOneUserByEmail(String email);

	Optional<User> findOneUserById(String id);

	List<User> findAllUsers();

	User saveUser(User user);

	void deleteAllUsers();

	Position savePosition(User user, Position position);

	void deletePosition(User user, Position position);

	void deleteAllPositions();
}
