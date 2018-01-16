package org.cph.crypto.rest.controller;

import org.cph.crypto.core.usecase.ticker.UpdateTicker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RefreshController {
	private final UpdateTicker updateTicker;

	public RefreshController(final UpdateTicker updateTicker) {
		this.updateTicker = updateTicker;
	}

	// TODO add spring security
	//@PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
	@RequestMapping(
		method = {RequestMethod.GET},
		produces = {"application/json"},
		value = {"/api/refresh"}
	)
	public ResponseEntity<String> refreshAll() {
		this.updateTicker.updateAll();
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}
