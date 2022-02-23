package com.league.challenge.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.league.challenge.service.repository.BookingRepository;

@Service
public class RoomService {
	
	private BookingRepository bookingRepository;
	
	LocalTime dayStart = LocalTime.parse("08:00:00");
	LocalTime dayEnd = LocalTime.parse("18:00:00");

	
	public RoomService(BookingRepository bookingRepository) {
		super();
		this.bookingRepository = bookingRepository;
	}
	
	

//		
}
