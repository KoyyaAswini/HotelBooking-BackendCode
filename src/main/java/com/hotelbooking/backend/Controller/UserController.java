package com.hotelbooking.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.hotelbooking.backend.Service.interfaces.IUserService;
import com.hotelbooking.backend.dto.Response;
import com.hotelbooking.backend.dto.UserDto;



@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private IUserService userService;


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId) {
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
        Response response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<Response> getLoggedInUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable("userId") String userId) {
        Response response = userService.getUserBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

//    @PutMapping("/update/{userId}")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<Response> updateProfile(@PathVariable Long id,
//                                               @RequestParam(value = "fname", required = false)String fname,
//                                               @RequestParam(value = "lname", required = false) String lname,
//                                               @RequestParam(value = "age", required = false) Integer age,
//                                               @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
//                                               @RequestParam(value = "email", required = false)String email
//
//
//    ) {
//        Response response = userService.updateProfile(id,fname,lname,age,phoneNumber,email);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
    
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> updateProfile(@PathVariable Long userId,
                                                   @RequestBody UserDto userDto) {
        Response response = userService.updateProfile(userId, userDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



}
