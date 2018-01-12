package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.exception.UserNotFoundException;
import org.cph.crypto.core.spi.TickerRepository;
import org.cph.crypto.core.spi.UserRepository;
import org.cph.crypto.core.utils.UserUtils;
import java.util.List;
import java.util.stream.Collectors;

public final class FindUser {
	private final UserRepository userRepository;
	private final TickerRepository tickerRepository;

	public FindUser(final UserRepository userRepository, TickerRepository tickerRepository) {
		this.userRepository = userRepository;
		this.tickerRepository = tickerRepository;
	}

	public final User findOne(final String id) {
		final User user = userRepository.findOneUserById(id).orElseThrow(() -> new UserNotFoundException(id));
		final List<String> ids = user.getPositions()
			.stream()
			.map(position -> position.getCurrency1().getCode() + "-" + position.getCurrency2().getCode())
			.collect(Collectors.toList());
		final List<Ticker> tickers = tickerRepository.findAllById(ids);
		return UserUtils.enrichUser(user, tickers);
	}

	public final List<User> findAll() {
		return userRepository.findAllUsers()
			.stream()
			.map(user -> {
				List<String> ids = user.getPositions().stream().map(position -> position.getCurrency1().getCode() + "-" + position.getCurrency2().getCode()).collect(Collectors.toList());
				final List<Ticker> tickers = tickerRepository.findAllById(ids);
				return UserUtils.enrichUser(user, tickers);
			})
			.collect(Collectors.toList());
	}
}
