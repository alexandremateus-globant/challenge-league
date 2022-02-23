package com.league.challenge.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.league.challenge.dto.AvailabilityRoomDTO;
import com.league.challenge.enums.EnumTrueFalse;
import com.league.challenge.service.AvailabilityService;

@RestController
@EnableAutoConfiguration
public class RoomController {

	private AvailabilityService avalService;
	
	public RoomController(AvailabilityService service) {
		this.avalService = service;
	}
	
	
	@RequestMapping("/allRoomsAvailable")
	public ResponseEntity<List<AvailabilityRoomDTO>> getRoomsAvailable (@RequestParam(value="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)  {
		
		List<AvailabilityRoomDTO> entityList = avalService.getAllAvailableRooms(date);
		
		if (entityList.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok().body(entityList);
		}
		
	}
	
	@RequestMapping("/availableRoomsByEfficiency")
	public ResponseEntity<List<AvailabilityRoomDTO>> getAvailableRoomsByEfficiency(@RequestParam(value="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			  @RequestParam(value="attendeesNumber") Long attendeesNumber,
			  @RequestParam(value="timeSpan") Long timeSpan,
			  @RequestParam(value="multimedia") boolean multimedia,
			  @RequestParam(value="buildingId", required = false) Long buildingId)  {
				  
		List<AvailabilityRoomDTO> entityList = avalService.getAvailableRoomsByEfficiency(date, attendeesNumber, timeSpan, EnumTrueFalse.getInstance(multimedia).getOneZero(), buildingId);
		
		if (entityList.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok().body(entityList);
		}
	}
			  

	@RequestMapping("/reserveRoom")
	public ResponseEntity<AvailabilityRoomDTO> reserveRoom(@RequestParam(value="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(value="attendeesNumber") Long attendeesNumber,
			@RequestParam(value="timeSpan") Long timeSpan,
			@RequestParam(value="multimedia") boolean multimedia,
			@RequestParam(value="buildingId", required = false) Long buildingId)  {
		
		Optional<AvailabilityRoomDTO> availabilityOpt = avalService.reserveRoom(date, attendeesNumber, timeSpan, EnumTrueFalse.getInstance(multimedia).getOneZero(), buildingId);
		
		if (availabilityOpt.isPresent()) {
			return ResponseEntity.ok().body(availabilityOpt.get());
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}
			
}
