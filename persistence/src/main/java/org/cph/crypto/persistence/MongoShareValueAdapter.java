package org.cph.crypto.persistence;

import org.cph.crypto.core.entity.ShareValue;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.persistence.entity.ShareValueDb;
import org.cph.crypto.persistence.entity.UserDb;
import org.cph.crypto.persistence.repository.ShareValueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class MongoShareValueAdapter implements org.cph.crypto.core.spi.ShareValueRepository {
	private final ShareValueRepository repository;

	public MongoShareValueAdapter(final ShareValueRepository repository) {
		this.repository = repository;
	}

	public List<ShareValue> findAllByUser(final User user) {
		return repository.findAllByUser(UserDb.from(user))
			.stream()
			.map(ShareValueDb::toShareValue)
			.collect(Collectors.toList());
	}

	public Optional<ShareValue> findTop1ByUserOrderByTimestampDesc(final User user) {
		return repository.findTop1ByUserOrderByTimestampDesc(UserDb.from(user)).map(ShareValueDb::toShareValue);
	}

	public ShareValue save(final ShareValue shareValue) {
		return this.repository.save(ShareValueDb.from(shareValue)).toShareValue();
	}
}
