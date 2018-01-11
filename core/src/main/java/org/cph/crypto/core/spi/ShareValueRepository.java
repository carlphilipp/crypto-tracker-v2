package org.cph.crypto.core.spi;

import org.cph.crypto.core.entity.ShareValue;
import org.cph.crypto.core.entity.User;
import java.util.List;
import java.util.Optional;

public interface ShareValueRepository {

	ShareValue save(ShareValue shareValue);

	List<ShareValue> findAllByUser(User user);

	Optional<ShareValue> findTop1ByUserOrderByTimestampDesc(User user);
}
