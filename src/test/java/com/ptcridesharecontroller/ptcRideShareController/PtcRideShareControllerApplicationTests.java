package com.ptcridesharecontroller.ptcRideShareController;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptcridesharecontroller.ptcRideShareController.RideShare.Car;
import com.ptcridesharecontroller.ptcRideShareController.RideShare.Ride;
import com.ptcridesharecontroller.ptcRideShareController.RideShare.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
class PtcRideShareControllerApplicationTests {

	@Test
	void carClassPresent() throws ClassNotFoundException {
		//Arrange
		boolean carClassExists = false;
		String carClassNameExpected = "Car";
		String carClassNameActual;

		//Act

		Car aCar = new Car(); //try to instanciate the car w/ default constructor
		carClassNameActual = aCar.getClass().getSimpleName();


		if (carClassNameActual.equals(carClassNameExpected)){
			carClassExists = true;
		}

		//Assert
		assertTrue(carClassExists, "Car class cannot instantiate");
	}

	@Test
	void userClassPresent() throws ClassNotFoundException {
		//Arrange
		boolean userClassExists = false;
		String userClassNameExpected = "User";
		String userClassNameActual;
		
		//Act

		User aUser = new User(); //try to instanciate the user w/ default constructor
		userClassNameActual = aUser.getClass().getSimpleName();
	
		if (userClassNameActual.equals(userClassNameExpected)){
			userClassExists = true;
		}

		//Assert	
		assertTrue(userClassExists, "User class cannot instantiate");
	}

	@Test
	void rideClassPresent() throws ClassNotFoundException {
		//Arrange
		boolean rideClassExists = false;
		String rideClassNameExpected = "Ride";
		String rideClassNameActual;
		
		//Act

		Ride aRide = new Ride(); //try to instanciate the user w/ default constructor
		rideClassNameActual = aRide.getClass().getSimpleName();
	
		if (rideClassNameActual.equals(rideClassNameExpected)){
			rideClassExists = true;
		}		

		//Assert
		assertTrue(rideClassExists, "Ride class cannot instantiate");
	}


	@Test
	void requestARidePagePresent() {  //Request A ride page exists
		//Arrange

		//Act

		//Assert

	}
	@Test
	void requestARidePostsRidesUponSubmission() {
		//Arrange
		//Act
		//Assert

	}

	//Would like to have login tests sucessful but don't know how to get login status data from the DB right now w/out
	//I am guessing it would show  up as an entry in the dbo.AspNetUserLogins if successful?

}
