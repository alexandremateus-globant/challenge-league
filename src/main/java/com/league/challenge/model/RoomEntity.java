package com.league.challenge.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_room")
	private Long id;
	
	@Column(name = "number")
	private Long roomNumber;

	@Column(name = "floor")
	private Long floor;
	
	@Column(name = "capacity")
	private Long capacity;
	
	@Column(name = "multimedia")
	private Long hasMultimedia;

	@ManyToOne
	@JoinColumn(name = "id_building", nullable = false)
	private BuildingEntity building;
	
	@OneToMany(mappedBy = "room")
	private Set<BookingEntity> bookings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
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

	public Long getHasMultimedia() {
		return hasMultimedia;
	}

	public void setHasMultimedia(Long hasMultimedia) {
		this.hasMultimedia = hasMultimedia;
	}

	public BuildingEntity getBuilding() {
		return building;
	}

	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}

	public Set<BookingEntity> getBookings() {
		return bookings;
	}

	public void setBookings(Set<BookingEntity> bookings) {
		this.bookings = bookings;
	}


}
