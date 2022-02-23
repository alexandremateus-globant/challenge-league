package com.league.challenge.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.league.challenge.dto.AvailabilityRoomDTO;
import com.league.challenge.dto.RoomDTO;
import com.league.challenge.enums.EnumTrueFalse;
import com.league.challenge.model.BookingEntity;
import com.league.challenge.model.BuildingEntity;
import com.league.challenge.model.RoomEntity;
import com.league.challenge.service.repository.BookingRepository;
import com.league.challenge.service.repository.RoomRepository;
import com.league.challenge.sort.SortAvailability;
import com.league.challenge.sort.SortBooking;
import com.league.challenge.sort.SortEfficiency;

@Service
public class AvailabilityService {
	
	private BookingRepository bookingRepository;
	private RoomRepository roomRepository;
	
	private final Long CLEANUP_BASE = 5L;
	
	private final LocalTime HOUR_DAY_BEGIN = LocalTime.parse("08:00:00");
	private final LocalTime HOUR_DAY_END = LocalTime.parse("18:00:00");
	
	
	public AvailabilityService(BookingRepository bookingRepository, RoomRepository roomRepository) {
		this.bookingRepository = bookingRepository;
		this.roomRepository = roomRepository;
	}

	public List<AvailabilityRoomDTO> getAllAvailableRooms(LocalDate date) {
		
		LocalDateTime start = LocalDateTime.of(date, HOUR_DAY_BEGIN);
		LocalDateTime end = LocalDateTime.of(date, HOUR_DAY_END);
		
		Set<AvailabilityRoomDTO> availabilitySet = new HashSet<AvailabilityRoomDTO>();
		AvailabilityRoomDTO availability = new AvailabilityRoomDTO();
		
		List<BookingEntity> bookingList = bookingRepository.findAll();
		List<RoomEntity> roomList = roomRepository.findAll();

		Collections.sort(bookingList, new SortBooking());
		
		for (RoomEntity r : roomList) {
			LocalDateTime lastBooking = null;
			
			for (BookingEntity b : bookingList) {
				
				if (r.getId().equals(b.getRoom().getId()) && date.isEqual(convert(b.getStartDate()).toLocalDate()) ) {
				
					if ( convert(b.getStartDate()).isEqual(start) ) {
						availability.setRoom(RoomDTO.converter(r));
						availability.setFrom(convert(b.getEndDate()));
					} else if (availability.getFrom() == null && lastBooking != null
							&& convert(b.getStartDate()).isEqual(lastBooking) ) {
						availability.setFrom(convert(b.getEndDate()));
						availability.setRoom(RoomDTO.converter(r));
						lastBooking = null;
					} else if (availability.getFrom() == null && convert(b.getStartDate()).isAfter(start) ) {
						availability.setFrom(start);
						lastBooking = convert(b.getEndDate());
						availability.setRoom(RoomDTO.converter(r));
						availability.setTo(convert(b.getStartDate()).minusMinutes(CLEANUP_BASE + r.getCapacity()));
						availabilitySet.add(availability);
						availability = new AvailabilityRoomDTO();
					} else if(availability.getFrom() != null && availability.getTo() == null) {
						if (availability.getFrom().isEqual(convert(b.getStartDate()))) {
							availability.setFrom(convert(b.getEndDate()));
						} else {
							availability.setTo(convert(b.getStartDate()).minusMinutes(CLEANUP_BASE + r.getCapacity()));
							lastBooking = convert(b.getEndDate());
							availability.setRoom(RoomDTO.converter(r));
							availabilitySet.add(availability);
							availability = new AvailabilityRoomDTO();
						}
					} else if(availability.getFrom() == null) {
						availability.setRoom(RoomDTO.converter(r));
						availability.setFrom(convert(b.getEndDate()));
					}
				}
			}
			
			if (availability.getRoom() == null && lastBooking == null) {
				availability.setFrom(start);
				availability.setTo(end.minusMinutes(CLEANUP_BASE + r.getCapacity()));
				availability.setRoom(RoomDTO.converter(r));
				availabilitySet.add(availability);
				availability = new AvailabilityRoomDTO();
			} else if (lastBooking == null && availability.getTo() == null) {
				availability.setTo(end.minusMinutes(CLEANUP_BASE + r.getCapacity()));
				availabilitySet.add(availability);
				availability = new AvailabilityRoomDTO();
			} else if (lastBooking != null && lastBooking.isBefore(end.minusMinutes(CLEANUP_BASE + r.getCapacity()))) {
				AvailabilityRoomDTO lastAvailability = new AvailabilityRoomDTO();
				lastAvailability.setFrom(lastBooking);
				lastAvailability.setTo(end.minusMinutes(CLEANUP_BASE + r.getCapacity()));
				lastAvailability.setRoom(RoomDTO.converter(r));
				availabilitySet.add(lastAvailability);
				availability = new AvailabilityRoomDTO();
			}
			
		}
		
		List<AvailabilityRoomDTO> availabilityList = new ArrayList<AvailabilityRoomDTO>(availabilitySet);
		
		Collections.sort(availabilityList, new SortAvailability());
		
		availabilityList.stream()
		.forEach(p -> System.out.println(p.getRoom().getRoomNumber() + " " + p.getFrom() + " - " + p.getTo())); 
		
		return availabilityList;
		
	}
	
	public List<AvailabilityRoomDTO> getAvailableRoomsByEfficiency(LocalDate date, Long capacity, Long span, Long multimedia, Long idBuilding) {
		
		List<AvailabilityRoomDTO> allAvailabilityList = getAllAvailableRooms(date);
		List<AvailabilityRoomDTO> efficiencyList = new ArrayList<AvailabilityRoomDTO>();
		
		efficiencyList = allAvailabilityList.stream()
			.filter(p -> (idBuilding == null || p.getRoom().getBuilding().getId().compareTo(idBuilding) == 0)
					  && (p.getRoom().getCapacity().compareTo(capacity) >= 0)
					  && ((multimedia.compareTo(1L) == 0 && p.getRoom().getHasMultimedia().equals(EnumTrueFalse.TRUE)) || (multimedia.compareTo(0L) == 0)) 
					  && (Long.valueOf(Duration.between(p.getFrom(), p.getTo()).toHours()).compareTo(span) >= 0)
					  )
			.collect(Collectors.toList());
		
		
		System.out.println("#################################");
		
		Collections.sort(efficiencyList, new SortEfficiency());
		
		efficiencyList.stream()
		.forEach(p -> System.out.println(p.getRoom().getRoomNumber() + " " + p.getFrom() + " - " + p.getTo())); 
		
		return efficiencyList;
		
	}
	
	public Optional<AvailabilityRoomDTO> reserveRoom(LocalDate date, Long capacity, Long span, Long multimedia, Long idBuilding) {
		
		List<AvailabilityRoomDTO> efficiencyList = getAvailableRoomsByEfficiency(date, capacity, span, multimedia, idBuilding);
		
		Optional<AvailabilityRoomDTO> availabilityOpt = efficiencyList.stream().findFirst();
		
		if (availabilityOpt.isPresent()) {
			BookingEntity bookingEntity = new BookingEntity();
			RoomEntity roomEntity = new RoomEntity();
			BuildingEntity buildingEntity = new BuildingEntity();
			
			buildingEntity.setId(availabilityOpt.get().getRoom().getBuilding().getId());
			roomEntity.setBuilding(buildingEntity);
			roomEntity.setId(availabilityOpt.get().getRoom().getId());
			
			bookingEntity.setStartDate(convert(availabilityOpt.get().getFrom()));
			bookingEntity.setEndDate(convert(availabilityOpt.get().getFrom().plusHours(span).plusMinutes(CLEANUP_BASE + availabilityOpt.get().getRoom().getCapacity())));
			bookingEntity.setRoom(roomEntity);
			
			bookingRepository.save(bookingEntity);
		}
		
		return availabilityOpt;
	}

	
	private static LocalDateTime convert(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private static Date convert(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	private static Date convert(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	

	
}
