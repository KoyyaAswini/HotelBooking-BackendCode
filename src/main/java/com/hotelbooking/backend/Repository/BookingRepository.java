package com.hotelbooking.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotelbooking.backend.Entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findByRoomId(Long roomId);
	
	Optional<Booking> findByBookingConfirmationCode(String bookingConfirmationCode);
	
	
	
	List<Booking> findByUserId(Long userId);
	

}
