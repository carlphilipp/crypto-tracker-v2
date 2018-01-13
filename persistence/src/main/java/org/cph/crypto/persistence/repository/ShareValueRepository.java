package org.cph.crypto.persistence.repository;

import org.cph.crypto.persistence.entity.ShareValueDb;
import org.cph.crypto.persistence.entity.UserDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ShareValueRepository extends MongoRepository<ShareValueDb, String> {

	List<ShareValueDb> findAllByUser(UserDb user);

	Optional<ShareValueDb> findTop1ByUserOrderByTimestampDesc(UserDb user);
}
