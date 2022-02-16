package com.league.challenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.league.challenge.dto.BuildingResponse;
import com.league.challenge.model.Building;
import com.league.challenge.repository.BuildingRepository;

@RestController
@EnableAutoConfiguration
public class BuildingController {

	private BuildingRepository buildingRepository;
	
	public BuildingController(BuildingRepository buildingRepository) {
		this.buildingRepository = buildingRepository;
	}
	
	@RequestMapping("/get")
	public List<BuildingResponse> findAll() {
		List<Building> buildingList = buildingRepository.findAll();
		return buildingList.stream()
				.map(BuildingResponse::converter)
				.collect(Collectors.toList());
	}
	
}
