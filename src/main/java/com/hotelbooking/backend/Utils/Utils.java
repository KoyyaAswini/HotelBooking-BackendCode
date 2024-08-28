package com.hotelbooking.backend.Utils;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.hotelbooking.backend.Entity.Booking;
import com.hotelbooking.backend.Entity.Room;
import com.hotelbooking.backend.Entity.User;
import com.hotelbooking.backend.dto.BookingDto;
import com.hotelbooking.backend.dto.RoomDto;
import com.hotelbooking.backend.dto.UserDto;

public class Utils {
	private static final String ALPHANUMERIC_STRING="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final SecureRandom secureRandom=new SecureRandom();
	
	public static String generateRandomConfirmationCode(int length) {
		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<length;i++) {
			int randomIndex=secureRandom.nextInt(ALPHANUMERIC_STRING.length());
			char randomChar=ALPHANUMERIC_STRING.charAt(randomIndex);
			stringBuilder.append(randomChar);	
		}
		return stringBuilder.toString();
		}
	
	public static UserDto mapUserEntityToUserDto(User user) {
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setFname(user.getFname());
		userDto.setLname(user.getLname());
		userDto.setAge(user.getAge());
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole());
		userDto.setPhoneNumber(user.getPhoneNumber());
		return userDto;
	}
	
	public static RoomDto mapRoomEntityToRoomDto(Room room) {
		RoomDto roomDto=new RoomDto();
		
		roomDto.setId(room.getId());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomPrice(room.getRoomPrice());
		roomDto.setRoomDescription(room.getRoomDescription());
		roomDto.setPhoto(room.getPhoto());
		return roomDto;
	}
	
	public static BookingDto mapBookingEntityToBookingDto(Booking booking) {
		BookingDto bookingDto=new BookingDto();
		bookingDto.setId(booking.getId());
		bookingDto.setCheckInDate(booking.getCheckInDate());
		bookingDto.setCheckOutDate(booking.getCheckOutDate());
		bookingDto.setNumOfAdults(booking.getNumOfAdults());
		bookingDto.setNumOfChildren(booking.getNumOfChildren());
		bookingDto.setTotalNumOfGuests(booking.getTotalNumOfGuests());
		bookingDto.setStatus(booking.getStatus()); 
		bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		return bookingDto;
	}
	
	
	public static RoomDto mapRoomEntityToRoomDtoPlusBookings(Room room) {
		RoomDto roomDto=new RoomDto();
		
		roomDto.setId(room.getId());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomPrice(room.getRoomPrice());
		roomDto.setRoomDescription(room.getRoomDescription());
		roomDto.setPhoto(room.getPhoto());
		
		 if (room.getBookings() != null) {
	            roomDto.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList()));
	        }
	        return roomDto;
	}
	
	public static BookingDto mapBookingEntityToBookingDtoPlusBookedRooms(Booking booking,boolean mapUser) {
		BookingDto bookingDto=new BookingDto();
		
		bookingDto.setId(booking.getId());
		bookingDto.setCheckInDate(booking.getCheckInDate());
		bookingDto.setCheckOutDate(booking.getCheckOutDate());
		bookingDto.setNumOfAdults(booking.getNumOfAdults());
		bookingDto.setNumOfChildren(booking.getNumOfChildren());
		bookingDto.setTotalNumOfGuests(booking.getTotalNumOfGuests());
		bookingDto.setStatus(booking.getStatus()); 
		bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		if(mapUser) {
			bookingDto.setUser(Utils.mapUserEntityToUserDto(booking.getUser()));
		}
		if(booking.getRoom()!=null) {
			RoomDto roomDto=new RoomDto();
			
			roomDto.setId(booking.getRoom().getId());
			roomDto.setRoomType(booking.getRoom().getRoomType());
			roomDto.setRoomPrice(booking.getRoom().getRoomPrice());
			roomDto.setRoomDescription(booking.getRoom().getRoomDescription());
			roomDto.setPhoto(booking.getRoom().getPhoto());
			bookingDto.setRoom(roomDto);
		}
		return bookingDto;
	}
	
	public static UserDto mapUserEntityToUserDtoPlusBookingsAndRoom(User user) {
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setFname(user.getFname());
		userDto.setLname(user.getLname());
		userDto.setAge(user.getAge());
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole());
		userDto.setPhoneNumber(user.getPhoneNumber());
		
		 if (!user.getBookings().isEmpty()) {
	            userDto.setBookings(user.getBookings().stream().map(booking -> Utils.mapBookingEntityToBookingDtoPlusBookedRooms(booking, false)).collect(Collectors.toList()));
	        }
	        return userDto;
	}
	
	
	public static List<UserDto> mapUserListEntityToUserListDto(List<User> userList){
		return userList.stream().map(Utils::mapUserEntityToUserDto).collect(Collectors.toList());
	}
	
	public static List<RoomDto> mapRoomListEntityToRoomListDto(List<Room> roomList) {
        return roomList.stream().map(Utils::mapRoomEntityToRoomDto).collect(Collectors.toList());
    }

    public static List<BookingDto> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList());
    }
	
}
