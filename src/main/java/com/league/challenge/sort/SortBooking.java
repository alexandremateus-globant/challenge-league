package com.league.challenge.sort;

import java.util.Comparator;

import com.league.challenge.model.BookingEntity;

public class SortBooking implements Comparator<BookingEntity> {

	@Override
	public int compare(BookingEntity o1, BookingEntity o2) {
		int compareOne = o1.getRoom().getId().compareTo(o2.getRoom().getId());
		if (compareOne == 0) {
			return o1.getStartDate().compareTo(o2.getStartDate());
		}
		return compareOne;
	}
	
}
