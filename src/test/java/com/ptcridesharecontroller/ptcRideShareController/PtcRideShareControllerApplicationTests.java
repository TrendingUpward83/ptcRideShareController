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
	void carClassPresent() {
		//Arrange
		boolean carClassExists = false;

		//Act

		try {

			Car aCar = new Car(); //try to instanciate the car w/ default constructor
		}
		catch (ClassNotFoundException e) {

			System.out.println("Error with creating Car object: "+ e);
		}
		//Assert

		assertTrue(carClassExists, "Car class does not exist");
	}

	@Test
	void userClassPresent() {
		//Arrange
		boolean userClassExists = false;

		//Act
		try {

			User aUser = new User(); //try to instanciate user car w/ default constructor
		}
		catch (ClassNotFoundException e) {

			System.out.println("Error with creating User object: "+ e);
		}
		//Assert

		assertTrue(userClassExists, "User class does not exist");
	}

	@Test
	void rideClassPresent() {
		//Arrange
		boolean rideClassExists = false;

				//Act
		try {

			Ride aRide = new Ride(); //try to instanciate the user w/ default constructor
		}
		catch (ClassNotFoundException e) {

			System.out.println("Error with creating User object: "+ e);
		}
		//Assert

		assertTrue(rideClassExists, "ride class does not exist");
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
