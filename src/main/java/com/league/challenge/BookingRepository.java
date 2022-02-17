package com.league.challenge;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.league.challenge.model.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long>{

	@Query("select b2 from BookingEntity b2 "
			+ "inner join RoomEntity r      "
			+ "   on r.id = b2.id           "
			+ "inner join BuildingEntity b1 "
			+ "   on b1.id = r.id           "
			+ "where date(start)     = ?1    "
			+ "  and r.capacity     >= ?2    "
			+ "  and r.hasMultimedia = ?3    "
			+ "  and (?4 is null or b1.id = ?4)    ")
	List<BookingEntity> findTakenRoom(Date date, Long capacity, Long multimedia, Long idBuilding);

}
