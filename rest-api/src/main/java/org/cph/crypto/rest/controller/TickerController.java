package org.cph.crypto.rest.controller;

import org.cph.crypto.core.usecase.ticker.FindTicker;
import org.cph.crypto.rest.dto.TickerDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping({"/api/ticker"})
@RestController
public class TickerController {
	private final FindTicker findTicker;

	public TickerController(final FindTicker findTicker) {
		this.findTicker = findTicker;
	}

	// TODO add spring security
	//@PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
	@RequestMapping(method = {RequestMethod.GET}, produces = {"application/json"})
	public List<TickerDto> getAllTickers() {
		return findTicker.findAllOrderByMarketCapDesc()
			.stream()
			.map(TickerDto::from)
			.collect(Collectors.toList());
	}

	//@PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
	@RequestMapping(value = {"/{currency1}/{currency2}"}, method = {RequestMethod.GET}, produces = {"application/json"})
	public TickerDto getTicker(@PathVariable("currency1") final String currencyCode1,
							   @PathVariable("currency2") final String currencyCode2) {
		return TickerDto.from(findTicker.findOne(currencyCode1 + "-" + currencyCode2));
	}
}
