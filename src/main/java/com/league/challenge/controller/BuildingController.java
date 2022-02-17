package com.league.challenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.league.challenge.BookingRepository;
import com.league.challenge.BuildingRepository;
import com.league.challenge.RoomRepository;
import com.league.challenge.dto.BookingDTO;
import com.league.challenge.dto.BuildingDTO;
import com.league.challenge.dto.RoomDTO;
import com.league.challenge.model.BookingEntity;
import com.league.challenge.model.BuildingEntity;
import com.league.challenge.model.RoomEntity;

@RestController
@EnableAutoConfiguration
public class BuildingController {

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BookingRepository bookingRepository;
	
	@RequestMapping("/buildings")
	public List<BuildingDTO> findAll() {
		List<BuildingEntity> buildingList = buildingRepository.findAll();
		return buildingList.stream()
				.map(BuildingDTO::converter)
				.collect(Collectors.toList());
	}
	
	@RequestMapping("/rooms")
	public List<RoomDTO> findAllRooms() {
		List<RoomEntity> roomList = roomRepository.findAll();
		return roomList.stream()
				.map(RoomDTO::converter)
				.collect(Collectors.toList());
	}

	@RequestMapping("/bookings")
	public List<BookingDTO> findAllBookings() {
		List<BookingEntity> bookingList = bookingRepository.findAll();
		return bookingList.stream()
				.map(BookingDTO::converter)
				.collect(Collectors.toList());
	}
	
	@PostMapping("/building")
	public void saveBuilding(@RequestBody BuildingDTO dto) {
		BuildingEntity entity = new BuildingEntity();
		entity.setName(dto.getName());
	}
	
	
	
}
