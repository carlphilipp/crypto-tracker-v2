package org.cph.crypto.core.usecase.position;

import org.cph.crypto.core.entity.ShareValue;
import org.cph.crypto.core.entity.Ticker;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.spi.IdGenerator;
import org.cph.crypto.core.spi.ShareValueRepository;
import org.cph.crypto.core.spi.TickerRepository;
import org.cph.crypto.core.spi.UserRepository;
import org.cph.crypto.core.utils.UserUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class UpdateShareValue {
	private final ShareValueRepository shareValueRepository;
	private final UserRepository userRepository;
	private final TickerRepository tickerRepository;
	private final IdGenerator idGenerator;

	public UpdateShareValue(final ShareValueRepository shareValueRepository, final UserRepository userRepository, final TickerRepository tickerRepository, final IdGenerator idGenerator) {
		this.shareValueRepository = shareValueRepository;
		this.userRepository = userRepository;
		this.tickerRepository = tickerRepository;
		this.idGenerator = idGenerator;
	}

	public final void updateAllUsersShareValue() {
		userRepository.findAllUsers().forEach(user -> {
			final List<String> ids = user.getPositions()
				.stream()
				.map(position -> position.getCurrency1().getCode() + "-" + position.getCurrency2().getCode())
				.collect(Collectors.toList());
			final List<Ticker> tickers = tickerRepository.findAllById(ids);
			addNewShareValue(UserUtils.enrichUser(user, tickers));
			user.setLiquidityMovement(0);
			userRepository.saveUser(user);
		});
	}

	private void addNewShareValue(final User user) {
		Optional<ShareValue> lastShareValue = this.shareValueRepository.findTop1ByUserOrderByTimestampDesc(user);
		if (lastShareValue.isPresent()) {
			double quantity = lastShareValue.get().getShareQuantity() + user.getLiquidityMovement() / ((user.getValue() - user.getLiquidityMovement()) / lastShareValue.get().getShareQuantity());
			double shareValue = user.getValue() / quantity;
			final ShareValue shareValueToSave = new ShareValue();
			shareValueToSave.setId(idGenerator.getNewId());
			shareValueToSave.setTimestamp(System.currentTimeMillis());
			shareValueToSave.setUser(user);
			shareValueToSave.setShareQuantity(quantity);
			shareValueToSave.setShareValue(shareValue);
			shareValueToSave.setPortfolioValue(user.getValue());
			this.shareValueRepository.save(shareValueToSave);
		} else {
			this.addFirstTimeShareValue(user);
		}
	}

	private void addFirstTimeShareValue(final User user) {

		final ShareValue yesterdayShareValue = new ShareValue();
		yesterdayShareValue.setId(idGenerator.getNewId());
		yesterdayShareValue.setTimestamp(System.currentTimeMillis() - 86400000);
		yesterdayShareValue.setUser(user);
		yesterdayShareValue.setShareQuantity(user.getOriginalValue() / 100);
		yesterdayShareValue.setShareValue(100);
		yesterdayShareValue.setPortfolioValue(user.getOriginalValue());
		shareValueRepository.save(yesterdayShareValue);

		double defaultShareValue = user.getGainPercentage() * 100 + 100;
		final ShareValue shareValueToSave = new ShareValue();
		shareValueToSave.setId(idGenerator.getNewId());
		shareValueToSave.setTimestamp(System.currentTimeMillis());
		shareValueToSave.setUser(user);
		shareValueToSave.setShareQuantity(user.getValue() / defaultShareValue);
		shareValueToSave.setShareValue(defaultShareValue);
		shareValueToSave.setPortfolioValue(user.getValue());
		this.shareValueRepository.save(shareValueToSave);
	}
}
