package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.entity.Email;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.spi.*;

public final class CreateUser {
	private final UserRepository userRepository;
	private final IdGenerator idGenerator;
	private final PasswordEncoder passwordEncoder;
	private final TemplateService templateService;
	private final ContextService contextService;
	private final EmailService emailService;

	public CreateUser(final UserRepository userRepository,
					  final IdGenerator idGenerator,
					  final PasswordEncoder passwordEncoder,
					  final TemplateService templateService,
					  final ContextService contextService,
					  final EmailService emailService) {
		this.userRepository = userRepository;
		this.idGenerator = idGenerator;
		this.passwordEncoder = passwordEncoder;
		this.templateService = templateService;
		this.contextService = contextService;
		this.emailService = emailService;
	}


	public final User create(final User user) {
		user.setId(idGenerator.getNewId());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.saveUser(user);
		String key = this.passwordEncoder.encode(user.getId() + user.getPassword());
		Email email = new Email();
		email.setTo(savedUser.getEmail());
		email.setSubject("Welcome to crypto tracker!");
		email.setContent(templateService.welcomeContentEmail(contextService.getBaseUrl(), savedUser.getId(), key));
		emailService.sendWelcomeEmail(email);
		return savedUser;
	}
}
