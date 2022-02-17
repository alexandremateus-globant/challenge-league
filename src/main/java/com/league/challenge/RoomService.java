package com.league.challenge;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.league.challenge.dto.AvailabilityRoomDTO;
import com.league.challenge.dto.BookingDTO;
import com.league.challenge.dto.RoomDTO;
import com.league.challenge.model.BookingEntity;
import com.league.challenge.model.RoomEntity;

@Service
@EnableJpaRepositories
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository; 
	
	@Autowired
	private BookingService bookingService;
	
	public List<AvailabilityRoomDTO> checkAvailability(BookingDTO dto) {
		
		LocalTime dayStart = LocalTime.parse("08:00:00");
		LocalTime dayEnd = LocalTime.parse("18:00:00");

		Long cleanup = 5L;

		List<RoomEntity> filteredRoomList = findRoom(dto.getRoom());
		
		List<BookingEntity> takenRoomList = bookingService.findTakenRoom(dto);
		
		LocalDateTime start = LocalDateTime.of(dto.getDate(), dayStart);
		LocalDateTime end = LocalDateTime.of(dto.getDate(), dayEnd);
		
		List<AvailabilityRoomDTO> availabilityList = new ArrayList<AvailabilityRoomDTO>();
		LocalDateTime lastBooking = null;
		
		for (RoomEntity r : filteredRoomList) {
			
			AvailabilityRoomDTO availability = new AvailabilityRoomDTO();
			
			for (BookingEntity b : takenRoomList) {
				
				
				if (r.getId().equals(b.getRoom().getId())) {
					if ( convert(b.getStartDate()).isEqual(start) ) {
						availability.setFrom(convert(b.getEndDate()));
					} else if(availability.getFrom() == null) {
						availability.setFrom(start);  
						availability.setRoom(r);
						availability.setTo(convert(b.getStartDate()).minusMinutes(cleanup + r.getCapacity()));
						lastBooking = convert(b.getEndDate()); 
						availabilityList.add(availability);
					} else if(availability.getFrom() != null && availability.getTo() == null) {
						availability.setTo(convert(b.getStartDate()).minusMinutes(cleanup + r.getCapacity()));
						lastBooking = convert(b.getEndDate());
						availability.setRoom(r);
						availabilityList.add(availability);
					}
				}
				
			}
			
			if (availability.getTo()!= null && availability.getTo().isBefore(end)) {
				AvailabilityRoomDTO lastAvailability = new AvailabilityRoomDTO();
				lastAvailability.setFrom(lastBooking);
				lastAvailability.setTo(end.minusMinutes(cleanup + r.getCapacity()));
				lastAvailability.setRoom(r);
				availabilityList.add(lastAvailability);
				availability = new AvailabilityRoomDTO();
			}
			
			if(availability.getFrom() != null && availability.getTo() != null) {
				availability.setRoom(r);
				availabilityList.add(availability);
				availability = new AvailabilityRoomDTO();
			}

		}
		
		return availabilityList;
		
	}
	
	private static LocalDateTime convert(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private static Date convert(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public List<RoomEntity> findRoom(RoomDTO dto) {
		return roomRepository.findRoom(dto.getCapacity(), 
				dto.getHasMultimedia().getOneZero(), 
				dto.getBuilding().getId());
	}
	
}
