package com.ptcridesharecontroller.ptcRideShareController.RideShare;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;
import java.util.Locale.Category;
import java.text.*;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@SuppressWarnings("unused")
@RestController
public class RideController {
    

    @RequestMapping(value = "/driverpostaride", method = RequestMethod.POST)
    public ResponseEntity<Ride> postNewRide(@RequestBody String newRide){

        //Reponses to check to confirm ride data is posted- get the ride data posted
        Ride newRidePost = new Ride();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();


        String origin = "";
        String dest = "";
        String rideDateTime = "";
        Byte smoke = 0;
        Byte eat = 0;
        Byte talk = 0;
        Byte carseat = 0;

        try {

            newRidePost = mapper.readValue(newRide, Ride.class);  //get the input values in the RequestBody as JSON & deserialize
            String driverID = "457b6d41-47ec-4ab8-865e-6092e7f4f272";
            float distance = 34.5f;
            float duration =3000f;
            float cost = 99.99f;
            float dScore = 0f;
            float rScore = 0f;
            Byte isTaken = 0;
            Byte isComplete =0;
            int carID =99;
            newRidePost.setDriverID(driverID); //temporarily hardcoding a driverID 
            newRidePost.setDistance(distance); 
            newRidePost.setDuration(duration);
            newRidePost.setCost(cost);

            origin = newRidePost.getPickUpLoc();
            dest = newRidePost.getDest();
            rideDateTime = newRidePost.getRideDate();
            smoke = newRidePost.getSmoking();
            eat = newRidePost.getEating();
            talk = newRidePost.getTalking();
            carseat = newRidePost.getCarseat();
            //carID is new in the table, need to add it to the Car class also
            
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();
            String sql = "INSERT INTO [dbo].[Ride] ([pickUpLocation],[destination],[driverID],[rideDate],[trait_smoking],[trait_eating],[trait_talking],[trait_carseat],[carID],[isTaken],[isCompleted])" +" VALUES ('"+origin+"','"+dest+"','"+driverID+"', CAST('"+rideDateTime+"' AS DATETIME),'"+smoke+"','"+eat+"','"+talk+"','"+carseat+"','"+carID+"','"+isTaken+"','"+isComplete+"')";
            stmnt.executeUpdate(sql); //insert new record into the DB
        }
        catch (SQLException e) {
            newRidePost.setPickUpLoc(rideDateTime + "  "+  e.toString());
            
            return new ResponseEntity<>(newRidePost,HttpStatus.BAD_REQUEST);
        }
        
        catch (JsonMappingException e){  //mapping problem
            newRidePost.setPickUpLoc(origin + "   "+e.toString());
                return new ResponseEntity<>(newRidePost,HttpStatus.BAD_REQUEST);

        }
        catch (JsonProcessingException e) { // bad JSON data
            newRidePost.setPickUpLoc(origin + "   "+e.toString());
            return new ResponseEntity<>(newRidePost,HttpStatus.BAD_REQUEST);

        }


        return new ResponseEntity<>(newRidePost,HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET) //creating response LIST with max value of 1 user so no more than 1 user can login
    public ResponseEntity<User> userLogin(@RequestParam(value = "eMail", defaultValue ="none") String uEmail){

        User loginUser = new User();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();
            //the sort DESC in the below query isn't working..
            String sql = "SELECT * FROM [dbo].[AspNetUsers] u JOIN [dbo].[UserProfile] p ON u.Id = p.userID WHERE u.UserName = '"+uEmail+"';";
            ResultSet rslt = stmnt.executeQuery(sql);

            
                while(rslt.next()){ //get all user data from query
                    loginUser.setUserEmail(rslt.getString("Email"));
                    loginUser.setUserName(rslt.getString("UserName"));
                    loginUser.setUserID(rslt.getString("Id"));
                    loginUser.setUserFName(rslt.getString("name_first"));
                    loginUser.setUserLName(rslt.getString("name_last"));
                    loginUser.setuDriverScore(rslt.getFloat("driverRateScore"));
                    loginUser.setuRiderScore(rslt.getFloat("riderRatingScore"));
                    loginUser.setIsDriver(rslt.getByte("active_driver"));
                    loginUser.setuStudID(rslt.getInt("studentid_num"));
                }
                con.close();
                    if (loginUser.getUserEmail().isEmpty()){
                        loginUser.setUserEmail("No Registered User found with e-mail: "+ uEmail);
                        return new ResponseEntity<>(loginUser,HttpStatus.NO_CONTENT);
                        
                    }
                    else {
                        return new ResponseEntity<>(loginUser,HttpStatus.OK); // if email found, return status code 200
                    }
            
            
        }
        catch (SQLException e) {
            loginUser.setUserEmail("SQL Error  "+  e.toString());
            return new ResponseEntity<>(loginUser,HttpStatus.BAD_REQUEST);
        }
        
      
    }


    @RequestMapping(value = "/car", method = RequestMethod.GET) //creating response LIST with max value of 1 user so no more than 1 user can login
    public ResponseEntity<Car> carInfo(@RequestParam(value = "eMail", defaultValue ="none") String uEmail){

        Car carInfo = new Car();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();
            //the sort DESC in the below query isn't working..
            String sql = "SELECT TOP 1 * FROM [dbo].[AspNetUsers] u JOIN [dbo].[car]c ON u.id = c.driverID WHERE u.UserName = '"+uEmail+"' ORDER BY c.isActive DESC;";
            ResultSet rslt = stmnt.executeQuery(sql);
        

            if (!rslt.wasNull()){

                while(rslt.next()){ //get all user data from query
                    carInfo.setCarID(rslt.getInt("carID"));
                    carInfo.setCarColor(rslt.getString("carColor"));
                    carInfo.setCarMake(rslt.getString("carMake"));
                    carInfo.setCarModel(rslt.getString("carModel"));
                    carInfo.setCarPlateNum(rslt.getString("carPlateNumber"));
                    carInfo.setCarIsActive(rslt.getByte("isActive"));

                }
            
            con.close();

            return new ResponseEntity<>(carInfo,HttpStatus.OK); // if email found, return status code 200
                             
            }
            else {
                carInfo.setCarMake("No car exists for user.");
                return new ResponseEntity<>(carInfo,HttpStatus.NO_CONTENT);
            }
        }
        catch (SQLException e) {
            carInfo.setCarMake("SQL Error  "+  e.toString());
            return new ResponseEntity<>(carInfo,HttpStatus.BAD_REQUEST);
        }
        
      
    }

}
