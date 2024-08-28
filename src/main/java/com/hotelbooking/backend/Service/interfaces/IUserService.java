package com.hotelbooking.backend.Service.interfaces;




import com.hotelbooking.backend.Entity.User;
import com.hotelbooking.backend.dto.LoginRequest;
import com.hotelbooking.backend.dto.Response;
import com.hotelbooking.backend.dto.UserDto;

public interface IUserService {

	Response register(User user);
	
	Response login(LoginRequest loginRequest );
	
	Response getAllUsers();
	
	Response getUserBookingHistory(String userId);
	
	Response deleteUser(String userId);
	
	Response getUserById(String userId);
	
	Response getMyInfo(String email);


//	Response updateProfile(Long userId,String fname, String lname, Integer age, String phoneNumber, String email);


	Response updateProfile(Long userId, UserDto userDto);
	
}
