package com.league.challenge.dto;

import com.league.challenge.model.Building;

public class BuildingResponse {

	private String name;
	
	public static BuildingResponse converter(Building building) {
		BuildingResponse response = new BuildingResponse();
		response.setName(building.getName());
		return response;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
