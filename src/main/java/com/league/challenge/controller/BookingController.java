package com.league.challenge.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.league.challenge.BookingService;
import com.league.challenge.dto.BookingDTO;
import com.league.challenge.dto.BuildingDTO;
import com.league.challenge.dto.RoomDTO;
import com.league.challenge.enums.EnumTrueFalse;
import com.league.challenge.model.BookingEntity;

@RestController
@EnableAutoConfiguration
public class BookingController {

	private BookingService service;
	
	public BookingController(BookingService service) {
		this.service = service;
	}
	
	@RequestMapping("/roomsAvailable")
	public ResponseEntity<List<BookingDTO>> getRoomsAvailable (@RequestParam(value="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
			  @RequestParam(value="timeSpan") Long timeSpan,
			  @RequestParam(value="attendeesNumber") Long attendeesNumber,
			  @RequestParam(value="buildingId", required = false) Long buildingId,
			  @RequestParam(value="multimedia") boolean multimedia)  {
		
		BuildingDTO building = new BuildingDTO();
		RoomDTO room = new RoomDTO();
		BookingDTO booking = new BookingDTO();
		
		room.setBuilding(building);
		booking.setRoom(room);
		
		booking.setTimeSpan(timeSpan);
		booking.setDate(date);
		room.setCapacity(attendeesNumber);
		building.setId(buildingId);
		room.setHasMultimedia(EnumTrueFalse.getInstance(multimedia));
		
		List<BookingEntity> entityList = service.findTakenRoom(booking);
		
		List<BookingDTO> dtoList = entityList.stream()
			.map(BookingDTO::converter)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(dtoList);
		
	}

	
	
}
