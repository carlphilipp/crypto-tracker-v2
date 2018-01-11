package org.cph.crypto.core.exception;

public class UserNotFoundException extends NotFoundException {
	public UserNotFoundException(String userId) {
		super("User id [" + userId + "] not found");
	}
}
