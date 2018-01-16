package org.cph.crypto.rest.controller;

import org.cph.crypto.core.usecase.position.AddPosition;
import org.cph.crypto.core.usecase.position.DeletePosition;
import org.cph.crypto.core.usecase.position.UpdatePosition;
import org.cph.crypto.core.usecase.sharevalue.UpdateShareValue;
import org.cph.crypto.core.usecase.sharevalue.GetShareValue;
import org.cph.crypto.core.usecase.user.CreateUser;
import org.cph.crypto.core.usecase.user.FindUser;
import org.cph.crypto.core.usecase.user.ValidateUser;
import org.cph.crypto.rest.dto.PositionDto;
import org.cph.crypto.rest.dto.ShareValueDto;
import org.cph.crypto.rest.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping(value = {"/api/user"})
@RestController
public class UserController {

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private CreateUser createUser;
	private FindUser findUser;
	private AddPosition addPosition;
	private UpdatePosition updatePosition;
	private DeletePosition deletePosition;
	private GetShareValue getShareValue;
	private UpdateShareValue updateShareValue;
	private ValidateUser validateUser;

	public UserController(CreateUser createUser, FindUser findUser, AddPosition addPosition,
						  UpdatePosition updatePosition, DeletePosition deletePosition, GetShareValue
							  getShareValue, UpdateShareValue updateShareValue, ValidateUser validateUser) {
		this.createUser = createUser;
		this.findUser = findUser;
		this.addPosition = addPosition;
		this.updatePosition = updatePosition;
		this.deletePosition = deletePosition;
		this.getShareValue = getShareValue;
		this.updateShareValue = updateShareValue;
		this.validateUser = validateUser;
	}

	// TODO add spring security
	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(method = {RequestMethod.GET}, produces = {"application/json"})
	public List<UserDto> getAllUsers() {
		return findUser.findAll().stream().map(UserDto::from).collect(Collectors.toList());
	}

	//@PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
	@RequestMapping(method = {RequestMethod.POST}, produces = {"application/json"})
	public UserDto createUser(@RequestBody UserDto user) {
		LOGGER.debug("Create user {}", user);
		return UserDto.from(createUser.create(user.toUser()));
	}

	//@PostAuthorize("returnObject.email == authentication.name")
	@RequestMapping(value = {"/{id}"}, method = {RequestMethod.GET}, produces = {"application/json"})
	public UserDto getUser(@PathVariable("id") String id) {
		return UserDto.from(findUser.findOne(id));
	}

	//@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = {"/{id}/position"}, method = {RequestMethod.POST}, consumes = {"application/json"})
	public void addPosition(@PathVariable("id") String id, @RequestBody PositionDto position) {
		addPosition.addPositionToUser(id, position.toPosition());
	}

	//@PreAuthorize("#id == authentication.details.decodedDetails['id'] and #positionId == #position.id")
	@RequestMapping(value = {"/{id}/position/{positionId}"}, method = {RequestMethod.PUT})
	public void updatePosition(@PathVariable("id") String id,
							   @RequestBody PositionDto position,
							   @PathVariable("positionId") String positionId,
							   @RequestParam(name = "transactionQuantity", required = false) Double transactionQuantity,
							   @RequestParam(name = "transactionUnitCostPrice", required = false) Double transactionUnitCostPrice) {
		updatePosition.updatePosition(id, position.toPosition(), transactionQuantity, transactionUnitCostPrice);
	}

	//@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = {"/{id}/position/{positionId}/{price}"}, method = {RequestMethod.DELETE})
	public void deletePosition(@PathVariable("id") String id,
							   @PathVariable("positionId") String positionId,
							   @PathVariable("price") Double price) {
		deletePosition.deletePosition(id, positionId, price);
	}

	//@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = {"/{id}/position/{positionId}/fee/{feeAmount:.+}"}, method = {RequestMethod.POST})
	public void addFeeToPosition(@PathVariable("id") String id,
								 @PathVariable("positionId") String positionId,
								 @PathVariable("feeAmount") Double feeAmount) {
		updatePosition.addFeeToPosition(id, positionId, feeAmount);
	}

	//@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = {"/{id}/sharevalue"}, method = {RequestMethod.GET}, produces = {"application/json"})
	List<ShareValueDto> findAllShareValue(@PathVariable("id") String id) {
		return getShareValue.findAllShareValue(id)
			.stream()
			.map(ShareValueDto::from).collect(Collectors.toList());
	}

	// TODO: delete that endpoint when share value dev is done
	//@RequestMapping(value = {"/share"})
	public void updateAllUsersShareValue() {
		updateShareValue.updateAllUsersShareValue();
	}

	//@PreAuthorize("authentication.details.decodedDetails['id'] == null")
	@RequestMapping(value = {"{id}/validate/{key}"})
	public void validateUser(@PathVariable("id") String id, @PathVariable("key") String key) {
		validateUser.validateUser(id, key);
	}
}
