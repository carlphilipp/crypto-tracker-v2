package org.cph.crypto.core.usecase.user;

import org.cph.crypto.core.Utils;
import org.cph.crypto.core.entity.Email;
import org.cph.crypto.core.entity.User;
import org.cph.crypto.core.spi.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class CreateUserTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
	private TemplateService templateService = Mockito.mock(TemplateService.class);
	private ContextService contextService = Mockito.mock(ContextService.class);
	private EmailService emailService = Mockito.mock(EmailService.class);
	private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
	private CreateUser createUser = new CreateUser(userRepository, idGenerator, passwordEncoder, templateService, contextService, emailService);

	@Test
	void testCreate() {
		// given
		ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
		User user = Utils.getUser();
		Email email = new Email();
		email.setTo("email");
		email.setSubject("Welcome to crypto tracker!");
		email.setContent("email content");
		given(idGenerator.getNewId()).willReturn("ID");
		given(passwordEncoder.encode(user.getPassword())).willReturn("encodedPassword");
		given(passwordEncoder.encode("IDencodedPassword")).willReturn("key");
		given(userRepository.saveUser(user)).willReturn(user);
		given(contextService.getBaseUrl()).willReturn("localhost");
		given(templateService.welcomeContentEmail("localhost", "ID", "key")).willReturn("email content");

		// when
		User actual = createUser.create(user);

		// then
		assertNotNull(actual);
		then(idGenerator).should().getNewId();
		then(passwordEncoder).should().encode("password");
		then(passwordEncoder).should().encode("IDencodedPassword");
		then(userRepository).should().saveUser(user);
		then(emailService).should().sendWelcomeEmail(emailCaptor.capture());
		then(contextService).should().getBaseUrl();
		then(templateService).should().welcomeContentEmail("localhost", "ID", "key");
		assertAll("Email verification",
			() -> assertEquals("email", emailCaptor.getValue().getTo()),
			() -> assertEquals("Welcome to crypto tracker!", emailCaptor.getValue().getSubject()),
			() -> assertEquals("email content", emailCaptor.getValue().getContent()));
		;
	}
}
