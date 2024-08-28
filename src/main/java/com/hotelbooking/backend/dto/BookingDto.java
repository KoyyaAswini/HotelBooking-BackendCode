package com.hotelbooking.backend.dto;

import java.time.LocalDate;

import com.hotelbooking.backend.Entity.BookingStatus;





public class BookingDto {
	
	 private Long id;
	private LocalDate checkInDate;

	private LocalDate checkOutDate;

	private int numOfAdults;
	
	private int numOfChildren;
	
	private int totalNumOfGuests;
	
	private String bookingConfirmationCode;
	
	private UserDto user;
	
	private RoomDto room;
	 private BookingStatus status;
	
	

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus bookingStatus) {
		this.status = bookingStatus;
	}

	//getters and setters for external use
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getNumOfAdults() {
		return numOfAdults;
	}

	public void setNumOfAdults(int numOfAdults) {
		this.numOfAdults = numOfAdults;
	}

	public int getNumOfChildren() {
		return numOfChildren;
	}

	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
	}

	public int getTotalNumOfGuests() {
		return totalNumOfGuests;
	}

	public void setTotalNumOfGuests(int totalNumOfGuests) {
		this.totalNumOfGuests = totalNumOfGuests;
	}

	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}

	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public RoomDto getRoom() {
		return room;
	}

	public void setRoom(RoomDto room) {
		this.room = room;
	}
	
	
	
	

}
