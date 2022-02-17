package com.league.challenge.dto;

import com.league.challenge.model.BuildingEntity;

public class BuildingDTO {

	private Long id;
	private String name;
	
	public static BuildingDTO converter(BuildingEntity entity) {
		BuildingDTO dto = new BuildingDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
