package org.cph.crypto.persistence.repository;

import org.cph.crypto.persistence.entity.UserDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDb, String> {
	Optional<UserDb> findOneByEmail(String email);
}
