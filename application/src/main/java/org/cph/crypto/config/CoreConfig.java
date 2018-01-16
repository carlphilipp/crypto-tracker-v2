package org.cph.crypto.config;

import org.cph.crypto.core.spi.*;
import org.cph.crypto.core.usecase.position.AddPosition;
import org.cph.crypto.core.usecase.position.DeletePosition;
import org.cph.crypto.core.usecase.position.UpdatePosition;
import org.cph.crypto.core.usecase.sharevalue.GetShareValue;
import org.cph.crypto.core.usecase.sharevalue.UpdateShareValue;
import org.cph.crypto.core.usecase.ticker.FindTicker;
import org.cph.crypto.core.usecase.ticker.UpdateTicker;
import org.cph.crypto.core.usecase.user.CreateUser;
import org.cph.crypto.core.usecase.user.FindUser;
import org.cph.crypto.core.usecase.user.LoginUser;
import org.cph.crypto.core.usecase.user.ValidateUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CoreConfig {

	@Bean
	public UpdateTicker updateTicker(TickerRepository tickerRepository, TickerClient tickerClient) {
		return new UpdateTicker(tickerRepository, tickerClient);
	}

	@Bean
	public FindTicker findTicker(TickerRepository tickerRepository) {
		return new FindTicker(tickerRepository);
	}

	@Bean
	public AddPosition addPosition(UserRepository userRepository, IdGenerator idGenerator) {
		return new AddPosition(userRepository, idGenerator);
	}

	@Bean
	public DeletePosition deletePosition(UserRepository userRepository) {
		return new DeletePosition(userRepository);
	}

	@Bean
	public UpdatePosition updatePosition(UserRepository userRepository) {
		return new UpdatePosition(userRepository);
	}

	@Bean
	public GetShareValue getShareValue(UserRepository userRepository, ShareValueRepository shareValueRepository) {
		return new GetShareValue(userRepository, shareValueRepository);
	}

	@Bean
	public UpdateShareValue updateShareValue(ShareValueRepository shareValueRepository,
											 UserRepository userRepository,
											 TickerRepository tickerRepository,
											 IdGenerator idGenerator) {
		return new UpdateShareValue(shareValueRepository, userRepository, tickerRepository, idGenerator);
	}

	@Bean
	public CreateUser createUser(UserRepository userRepository,
								 IdGenerator idGenerator,
								 PasswordEncoder passwordEncoder,
								 TemplateService templateService,
								 ContextService contextService,
								 EmailService emailService) {
		return new CreateUser(userRepository, idGenerator, passwordEncoder, templateService, contextService, emailService);
	}

	@Bean
	public FindUser findUser(UserRepository userRepository, TickerRepository tickerRepository) {
		return new FindUser(userRepository, tickerRepository);
	}

	@Bean
	public LoginUser loginUser(UserRepository userRepository) {
		return new LoginUser(userRepository);
	}

	@Bean
	public ValidateUser validateUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return new ValidateUser(userRepository, passwordEncoder);
	}
}
