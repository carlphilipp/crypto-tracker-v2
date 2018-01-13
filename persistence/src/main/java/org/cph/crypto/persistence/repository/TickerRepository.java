package org.cph.crypto.persistence.repository;

import org.cph.crypto.persistence.entity.TickerDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TickerRepository extends MongoRepository<TickerDb, String> {

	List<TickerDb> findAllByOrderByMarketCapDesc();

	List<TickerDb> findByIdIn(List<String> ids);
}
