package com.league.challenge.sort;

import java.util.Comparator;

import com.league.challenge.dto.AvailabilityRoomDTO;

public class SortEfficiency implements Comparator<AvailabilityRoomDTO> {

	@Override
	public int compare(AvailabilityRoomDTO o1, AvailabilityRoomDTO o2) {
		return  o1.getRoom().getCapacity().compareTo(o2.getRoom().getCapacity());
	}
	
}
