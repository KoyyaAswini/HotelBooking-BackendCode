package com.hotelbooking.backend.Service.interfaces;

import com.hotelbooking.backend.Entity.Booking;
import com.hotelbooking.backend.Entity.BookingStatus;
import com.hotelbooking.backend.dto.Response;

public interface IBookingService {

	    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

	    Response findBookingByConfirmationCode(String bookingConfirmationCode);

	    Response getAllBookings();

    Response cancelBooking(Long bookingId);

	Response updateBookingStatus(Long bookingId, BookingStatus status);
	    
	    

	}

