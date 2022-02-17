package com.league.challenge.dto;

import com.league.challenge.enums.EnumTrueFalse;
import com.league.challenge.model.RoomEntity;

public class RoomDTO {

	private Long roomNumber;
	private Long floor;
	private Long capacity;
	private EnumTrueFalse hasMultimedia;
	private BuildingDTO building;

	
	public static RoomDTO converter(RoomEntity entity) {
		RoomDTO dto = new RoomDTO();

		dto.setBuilding(BuildingDTO.converter(entity.getBuilding()));
		dto.setFloor(entity.getFloor());
		dto.setRoomNumber(entity.getRoomNumber());
		dto.setCapacity(entity.getCapacity());
		dto.setHasMultimedia(EnumTrueFalse.getInstance(entity.getHasMultimedia()));
		
		return dto;
	}


	public Long getFloor() {
		return floor;
	}


	public void setFloor(Long floor) {
		this.floor = floor;
	}


	public Long getCapacity() {
		return capacity;
	}


	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}


	public EnumTrueFalse getHasMultimedia() {
		return hasMultimedia;
	}


	public void setHasMultimedia(EnumTrueFalse hasMultimedia) {
		this.hasMultimedia = hasMultimedia;
	}


	public BuildingDTO getBuilding() {
		return building;
	}


	public void setBuilding(BuildingDTO buildingDto) {
		this.building = buildingDto;
	}


	public Long getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

}
