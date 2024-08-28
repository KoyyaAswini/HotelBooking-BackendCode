package com.hotelbooking.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//import com.hotelbooking.backend.Configuration.AwsS3Properties;

@SpringBootApplication
//@EnableConfigurationProperties(AwsS3Properties.class)
public class HotelBookingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingBackendApplication.class, args);
	}

}
