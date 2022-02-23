package com.league.challenge.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.league.challenge.dto.BookingDTO;
import com.league.challenge.model.BookingEntity;
import com.league.challenge.service.repository.BookingRepository;

@Service
@EnableJpaRepositories
public class BookingService {
	
	private BookingRepository repository;
	
	public BookingService(BookingRepository repository) {
		super();
		this.repository = repository;
	}

	public List<BookingEntity> findTakenRoom(BookingDTO dto) {
		return repository.findTakenRoom(Date.from(dto.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()), 
				dto.getRoom().getCapacity(), 
				dto.getRoom().getHasMultimedia().getOneZero(), 
				dto.getRoom().getBuilding().getId());
	}
	
	public Optional<List<BookingDTO>> getReservedRooms(LocalDate date) {
		
		List<BookingEntity> entityList = repository.getReservedRooms(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		Optional<List<BookingDTO>> bookingListOpt = Optional.ofNullable(entityList.stream()
				.map(BookingDTO::converter)
				.collect(Collectors.toList()));
		
		return bookingListOpt;
		
	}
	
}
