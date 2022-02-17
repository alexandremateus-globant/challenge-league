package com.league.challenge;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.league.challenge.dto.BookingDTO;
import com.league.challenge.model.BookingEntity;

@Service
@EnableJpaRepositories
public class BookingService {
	
	@Autowired
	private BookingRepository repository; 

	public List<BookingEntity> findTakenRoom(BookingDTO dto) {
		return repository.findTakenRoom(Date.from(dto.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()), 
				dto.getRoom().getCapacity(), 
				dto.getRoom().getHasMultimedia().getOneZero(), 
				dto.getRoom().getBuilding().getId());
	}
	
}
