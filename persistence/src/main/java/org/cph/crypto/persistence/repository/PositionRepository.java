package org.cph.crypto.persistence.repository;

import org.cph.crypto.persistence.entity.PositionDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionRepository extends MongoRepository<PositionDb, String> {
}
