package com.league.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.league.challenge.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long>{

}
