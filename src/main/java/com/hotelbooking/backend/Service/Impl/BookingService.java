package com.hotelbooking.backend.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotelbooking.backend.Entity.Booking;
import com.hotelbooking.backend.Entity.BookingStatus; // Import the enum
import com.hotelbooking.backend.Entity.Room;
import com.hotelbooking.backend.Entity.User;
import com.hotelbooking.backend.Exception.OurException;
import com.hotelbooking.backend.Repository.BookingRepository;
import com.hotelbooking.backend.Repository.RoomRepository;
import com.hotelbooking.backend.Repository.UserRepository;
import com.hotelbooking.backend.Service.interfaces.IBookingService;
import com.hotelbooking.backend.Utils.Utils;
import com.hotelbooking.backend.dto.BookingDto;
import com.hotelbooking.backend.dto.Response;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try {
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Check in date must come after check out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            List<Booking> existingBookings = room.getBookings();

            if (!roomIsAvailable(bookingRequest, existingBookings)) {
                throw new OurException("Room not Available for selected date range");
            }

            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            bookingRequest.setStatus(BookingStatus.PENDING); // Set initial status to PENDING
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("Booking successful, pending admin approval");
            response.setBookingConfirmationCode(bookingConfirmationCode);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Saving booking: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String bookingConfirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(bookingConfirmationCode)
                    .orElseThrow(() -> new OurException("Booking Not Found"));
            BookingDto bookingDTO = Utils.mapBookingEntityToBookingDtoPlusBookedRooms(booking, true);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBooking(bookingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Finding booking: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDto> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBookingList(bookingDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response updateBookingStatus(Long bookingId, BookingStatus status) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new OurException("Booking Not Found"));

            
            if (status == BookingStatus.ACCEPTED) {
                booking.setBookingConfirmationCode(Utils.generateRandomConfirmationCode(10));
            }
            booking.setStatus(status);
            bookingRepository.save(booking);

            response.setStatusCode(200);
            response.setMessage("Booking status updated successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Updating booking status: " + e.getMessage());

        }
        return response;
    }

    @Transactional
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isPresent()) {
            try {
                Booking existingBooking = booking.get();
                existingBooking.setStatus(BookingStatus.REJECTED); // Set status to REJECTED instead of deleting
                bookingRepository.save(existingBooking);
                response.setStatusCode(200);
                response.setMessage("Booking rejected successfully.");
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("Error occurred while rejecting booking: " + e.getMessage());
            }
        } else {
            response.setStatusCode(404);
            response.setMessage("Booking not found.");
        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
