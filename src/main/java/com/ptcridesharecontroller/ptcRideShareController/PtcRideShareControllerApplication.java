package com.ptcridesharecontroller.ptcRideShareController;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.Date;
import java.util.Locale.Category;
import java.text.*;
import java.util.Calendar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@SpringBootApplication
public class PtcRideShareControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtcRideShareControllerApplication.class, args);

		//store logged in user information here : rider & driver ID, login & use through out. Create as a User or Custom object of some kind
		//(an object that holds various parts of the user's data acquired by different queries & store them here)
		//and reference the information on the various other app activities as needed.
		//Thinking this will be holding an array/list of this object with a max size of 1; when user Logs in, their user object
		//here is created & is added to the list. 
		//if another user tries to login, first the app checks this list if already size ==1; user is denied login
		//make sure to create a logout button/function on top of each activity so user can logout on all screens or where it makes sense
	}

}
