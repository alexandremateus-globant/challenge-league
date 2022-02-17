package com.league.challenge.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import com.league.challenge.model.BookingEntity;

public class BookingDTO {

	private RoomDTO room;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private Long timeSpan;
	
	public static BookingDTO converter(BookingEntity entity) {
		BookingDTO dto = new BookingDTO();
		
		dto.setRoom(RoomDTO.converter(entity.getRoom()));
		
		dto.setDate(entity.getStartDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
		
		dto.setStart(entity.getStartDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalTime());

		dto.setEnd(entity.getEndDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalTime());
		
		return dto;
	}


	public RoomDTO getRoom() {
		return room;
	}


	public void setRoom(RoomDTO room) {
		this.room = room;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getStart() {
		return start;
	}


	public void setStart(LocalTime start) {
		this.start = start;
	}


	public LocalTime getEnd() {
		return end;
	}


	public void setEnd(LocalTime end) {
		this.end = end;
	}


	public Long getTimeSpan() {
		return timeSpan;
	}


	public void setTimeSpan(Long timeSpan) {
		this.timeSpan = timeSpan;
	}

	
}
