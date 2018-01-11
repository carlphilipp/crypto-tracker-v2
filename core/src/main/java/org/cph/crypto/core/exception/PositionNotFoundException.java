package org.cph.crypto.core.exception;

public class PositionNotFoundException extends NotFoundException {
	public PositionNotFoundException(String positionId) {
		super("Position id [" + positionId + "] not found");
	}
}
