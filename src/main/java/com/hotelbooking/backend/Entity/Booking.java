package com.hotelbooking.backend.Entity;



import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name="bookings")
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;
	
	

	@NotNull(message="check in date is required")
	private LocalDate checkInDate;
	
	@Future(message="check out must be in future")
	private LocalDate checkOutDate;
	
	@Min(value=1,message="number of adults should not be less than 1")
	private int numOfAdults;
	
	@Min(value=0,message="number of children should not be less than 0")	
	private int numOfChildren;
	
	
	
	private int totalNumOfGuests;
	
	
	private String bookingConfirmationCode;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="room_id")
	private Room room;
	

	public void calculateTotalNumOfGuests() {
		this.totalNumOfGuests=this.numOfAdults+this.numOfChildren;
	}
	
	public void setNumOfAdults(int numOfAdults) {
		this.numOfAdults=numOfAdults;
		calculateTotalNumOfGuests();
	}
	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren=numOfChildren;
		calculateTotalNumOfGuests();
	}
	
	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode=bookingConfirmationCode;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
				+ ", numOfAdults=" + numOfAdults + ", numOfChildren=" + numOfChildren + ", totalNumOfGuests="
				+ totalNumOfGuests + ", bookingConfirmationCode=" + bookingConfirmationCode + ", user=" + user
				+ ", room=" + room + "]";
	}
	
	//setters exclude child,total and adults

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}
//only getters included
	public int getNumOfAdults() {
		return numOfAdults;
	}

	public int getNumOfChildren() {
		return numOfChildren;
	}

	public int getTotalNumOfGuests() {
		return totalNumOfGuests;
	}


	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	

}


