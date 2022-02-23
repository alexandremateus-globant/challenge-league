package com.league.challenge.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.league.challenge.model.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long>{
	

//	@Query("select r RoomEntity r      "
//			+ "inner join BuildingEntity b1 "
//			+ "   on b1.id = r.id           "
//			+ "  and r.capacity     >= ?1    "
//			+ "  and r.hasMultimedia = ?2    "
//			+ "  and (?3 is null or b1.id = ?3)    ")
//	List<RoomEntity> findRoom(Long capacity, Long multimedia, Long idBuilding);


}
