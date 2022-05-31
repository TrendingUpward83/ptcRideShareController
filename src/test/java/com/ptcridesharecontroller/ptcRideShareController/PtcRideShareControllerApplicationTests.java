package com.ptcridesharecontroller.ptcRideShareController;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptcridesharecontroller.ptcRideShareController.RideShare.Car;
import com.ptcridesharecontroller.ptcRideShareController.RideShare.Ride;
import com.ptcridesharecontroller.ptcRideShareController.RideShare.RideShareController;
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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("unused")  //can comment this out later
@SpringBootTest
class PtcRideShareControllerApplicationTests {


	//@Autowired MockMvc mvc;
 //	@MockBean Ride ride;

	//@Autowired static MockMvc mvc;
	//@MockBean Ride ride;
	private static boolean needsCleaned = false; //checks to see if the database needs cleaned of any test Rides

	@BeforeAll
	public static void setup() throws Exception
	{
		//add setup here
	}

	@AfterAll
	public static void cleanup() throws Exception
	{
		if(needsCleaned)
		{
			//TODO code to delete entries
		}
	}

	@Test
	void Test_CarClassPresent() throws ClassNotFoundException {
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
	void Test_UserClassPresent() throws ClassNotFoundException {
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
	void Test_RideClassPresent() throws ClassNotFoundException {
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
	void Test_RequestARidePostsRidesUponSubmission() {
		//Arrange

	@ParameterizedTest
	@CsvSource({"Test Location, Dest Location, 2022-05-28, 0, 0, 0, 0"})
	void Test_RequestARidePostsRidesUponSubmission(String origin, String dest, Date rideDate, Byte smoke, Byte eat, Byte talk, Byte carseat) {
		//Arrange
		Ride newRide = new Ride();
		newRide.setPickUpLoc(origin);
		newRide.setDest(dest);
		newRide.setRideDate(rideDate);
		newRide.setSmoking(smoke);
		newRide.setEating(eat);
		newRide.setTalking(talk);
		newRide.setCarseat(carseat);

		String json = TestManager.asJsonString(newRide);

		//Act
		try {
			HttpClient client = HttpClient.newBuilder().build(); //arrange HttpClient
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://127.0.0.1:8080/driverpostaride")).headers("content-type", "text/plain;charset=UTF-8").POST(HttpRequest.BodyPublishers.ofString(json)).build();
			HttpResponse response = client.send(request, BodyHandlers.ofString());
			
			//Assert
			int expected = 200;
			int actual = response.statusCode();
			assertEquals(expected, actual, "Status code was not 200.\nReturned status code: " + actual);
		} catch (IOException e) {
			System.out.println("IO Exception in RequestARidePostsRides. Check Try-Catch");
			assertTrue(false, "IOException in test");
			//e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Request interrupted");
			assertTrue(false, "InterruptedException in test");
			//e.printStackTrace();
		}

		
	}

	//Would like to have login tests sucessful but don't know how to get login status data from the DB right now w/out
	//I am guessing it would show  up as an entry in the dbo.AspNetUserLogins if successful?

}
