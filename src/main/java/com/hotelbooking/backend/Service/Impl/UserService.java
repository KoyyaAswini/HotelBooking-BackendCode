package com.hotelbooking.backend.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotelbooking.backend.Entity.User;
import com.hotelbooking.backend.Exception.OurException;
import com.hotelbooking.backend.Repository.UserRepository;
import com.hotelbooking.backend.Service.interfaces.IUserService;
import com.hotelbooking.backend.Utils.JWTUtils;
import com.hotelbooking.backend.Utils.Utils;
import com.hotelbooking.backend.dto.LoginRequest;
import com.hotelbooking.backend.dto.Response;

import com.hotelbooking.backend.dto.UserDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{
 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public Response register(User user) {
		Response response=new Response();
		try {
			if(user.getRole()==null||user.getRole().isBlank()) {
				user.setRole("USER");
			}
			if(userRepository.existsByEmail(user.getEmail())) {
				throw new OurException(user.getEmail()+"User Already Exists");
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser=userRepository.save(user);
			UserDto userDto=Utils.mapUserEntityToUserDto(savedUser);
			response.setStatusCode(200);
			response.setUser(userDto);
		}catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during registration"+e.getMessage());
		}
		return response;
	}
	
	@Override
	public Response login(LoginRequest loginRequest) {
		Response response=new Response();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			var user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new OurException("User Not Found"));
			var token=jwtUtils.generateToken(user);
			response.setStatusCode(200);
			response.setToken(token);
			response.setRole(user.getRole());
			response.setExpirationDate("7 DAYS");
			response.setMessage("Login Successful");
		}catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during login"+e.getMessage());
		}
		return response;
	}
	
	@Override
	public Response getAllUsers() {
		Response response =new Response();
		try {
			List<User> userList=userRepository.findAll();
			List<UserDto> userDtoList=Utils.mapUserListEntityToUserListDto(userList);
			response.setStatusCode(200);
			response.setMessage("Successful");
			response.setUserList(userDtoList);
		}catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during retreiving data of all users"+e.getMessage());
		}
		return response;
	}
	
	@Override
	public Response getUserBookingHistory(String userId) {
		Response response=new Response();
		
		try {
			User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User Not Found"));
			UserDto userDto=Utils.mapUserEntityToUserDtoPlusBookingsAndRoom(user);
			response.setStatusCode(200);
			response.setMessage("Successful");
			response.setUser(userDto);
		}catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured to retreive booking history"+e.getMessage());
		}
		return response;
		}
	
	@Override
	public Response deleteUser(String userId) {
		Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
	}
	
	@Override
	public Response getUserById(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDTO = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

	@Override
	public Response getMyInfo(String email) {

        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDTO = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

//	@Override
//	public Response updateProfile(Long userId,String fname, String lname, Integer age, String phoneNumber, String email) {
//		Response response = new Response();
//
//        try {
//            
//            User userProfile = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
//            if (fname != null) userProfile.setFname(fname);
//            if (lname != null) userProfile.setLname(lname);
//            if (age != null) userProfile.setAge(age);
//            if (fname != null) userProfile.setFname(fname);
//            if (phoneNumber != null) userProfile.setPhoneNumber(phoneNumber);
//            if (email != null) userProfile.setEmail(email);
//            User updatedUser = userRepository.save(userProfile);
//            UserDto userDTO = Utils.mapUserEntityToUserDto(updatedUser);
//
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setUser(userDTO);
//
//        } catch (OurException e) {
//            response.setStatusCode(404);
//            response.setMessage(e.getMessage());
//        } catch (Exception e) {
//            response.setStatusCode(500);
//            response.setMessage("Error saving a room " + e.getMessage());
//        }
//        return response;
//	}

	@Override
	public Response updateProfile(Long userId, UserDto userDto) {
	    Response response = new Response();

	    try {
	        User userProfile = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

	        if (userDto.getFname() != null) userProfile.setFname(userDto.getFname());
	        if (userDto.getLname() != null) userProfile.setLname(userDto.getLname());
	        if (userDto.getAge() != null) userProfile.setAge(userDto.getAge());
	        if (userDto.getPhoneNumber() != null) userProfile.setPhoneNumber(userDto.getPhoneNumber());
	        if (userDto.getEmail() != null) userProfile.setEmail(userDto.getEmail());

	        User updatedUser = userRepository.save(userProfile);
	        UserDto updatedUserDto = Utils.mapUserEntityToUserDto(updatedUser);

	        response.setStatusCode(200);
	        response.setMessage("Profile updated successfully.");
	        response.setUser(updatedUserDto);

	    } catch (OurException e) {
	        response.setStatusCode(404);
	        response.setMessage(e.getMessage());
	    } catch (Exception e) {
	        response.setStatusCode(500);
	        response.setMessage("Error updating profile: " + e.getMessage());
	    }
	    return response;
	}

	
	
	
}

