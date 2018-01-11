package org.cph.crypto.core.exception;

public class TickerNotFoundException extends NotFoundException {
	public TickerNotFoundException(String tickerId) {
		super("Ticker id [" + tickerId + "] not found");
	}
}
