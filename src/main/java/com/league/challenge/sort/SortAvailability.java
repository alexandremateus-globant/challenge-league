package com.league.challenge.sort;

import java.util.Comparator;

import com.league.challenge.dto.AvailabilityRoomDTO;

public class SortAvailability implements Comparator<AvailabilityRoomDTO> {

	@Override
	public int compare(AvailabilityRoomDTO o1, AvailabilityRoomDTO o2) {
		int compareOne = o1.getRoom().getId().compareTo(o2.getRoom().getId());
		if (compareOne == 0) {
			return o1.getFrom().compareTo(o2.getFrom());
		}
		return compareOne;
	}
	
}
