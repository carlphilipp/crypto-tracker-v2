package org.cph.crypto.core.spi;

import org.cph.crypto.core.entity.Email;

public interface EmailService {
	void sendWelcomeEmail(Email email);
}
