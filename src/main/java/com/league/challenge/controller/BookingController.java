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

import com.league.challenge.dto.BookingDTO;
import com.league.challenge.service.BookingService;

@RestController
@EnableAutoConfiguration
public class BookingController {

	private BookingService bookingService;
	
	public BookingController(BookingService service) {
		this.bookingService = service;
	}
	
	/**
	 * Lists all reserved rooms for all buildings in date.
	 * @param date
	 * @return
	 */
	@RequestMapping("/reservedRooms")
	public ResponseEntity<List<BookingDTO>> getReservedRooms (@RequestParam(value="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)  {
		
		Optional<List<BookingDTO>> bookingListOpt = bookingService.getReservedRooms(date);
		
		if (bookingListOpt.isPresent()) {
			return ResponseEntity.ok().body(bookingListOpt.get());
		} else {
			return ResponseEntity.noContent().build();
		}
		
	}
	
}
