package com.hotelbooking.backend.Controller;

import org.springframework.web.bind.annotation.RestController;
import com.hotelbooking.backend.Service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hotelbooking.backend.dto.LoginRequest;
import com.hotelbooking.backend.dto.Response;
import com.hotelbooking.backend.Entity.User;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody User user){
		Response response=userService.register(user);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
		Response response=userService.login(loginRequest);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
