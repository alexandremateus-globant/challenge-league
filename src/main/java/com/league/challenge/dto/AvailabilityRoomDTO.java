package com.league.challenge.dto;

import java.time.LocalDateTime;

import com.league.challenge.model.RoomEntity;

public class AvailabilityRoomDTO {
	
	private RoomDTO room;
	private LocalDateTime from;
	private LocalDateTime to;
	
	public RoomDTO getRoom() {
		return room;
	}
	public void setRoom(RoomDTO room) {
		this.room = room;
	}
	public LocalDateTime getFrom() {
		return from;
	}
	public void setFrom(LocalDateTime from) {
		this.from = from;
	}
	public LocalDateTime getTo() {
		return to;
	}
	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	
}
