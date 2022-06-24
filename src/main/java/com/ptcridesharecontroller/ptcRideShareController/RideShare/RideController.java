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
    static Map<Integer, Ride> ViewAllRides = new HashMap<Integer, Ride>();
    Ride allRides = new Ride();
    @RequestMapping(value = "/driverpostaride", method = RequestMethod.POST)
    public ResponseEntity<Ride> postNewRide(@RequestBody String newRide){

        //Reponses to check to confirm ride data is posted- get the ride data posted
        Ride newRidePost = new Ride();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();

        String driverID = "";
        String riderID = "";
        String origin = "";
        String dest = "";
        String rideDateTime = "";
        Byte smoke = 0;
        Byte eat = 0;
        Byte talk = 0;
        Byte carseat = 0;
        float distance = 0f;
        float duration = 0f;
        float cost = 0f;
        float dScore = 0f;
        float rScore = 0f;
        Byte isTaken = 0;
        Byte isComplete = 0;
        int carID = 0;

        try {

            newRidePost = mapper.readValue(newRide, Ride.class);  //get the input values in the RequestBody as JSON & deserialize
            
                if (newRidePost.getDriverID()==null){
                    driverID = "NULL";
                }
                else {
                    driverID = "'"+newRidePost.getDriverID()+"'";
                }

                if (newRidePost.getRiderID() ==null){
                    riderID = "NULL";
                }
                else{
                    riderID = "'"+newRidePost.getRiderID()+"'";
                }
            origin = newRidePost.getPickUpLoc();
            dest = newRidePost.getDest();
            rideDateTime = newRidePost.getRideDate();
            smoke = newRidePost.getSmoking();
            eat = newRidePost.getEating();
            talk = newRidePost.getTalking();
            carseat = newRidePost.getCarseat();
            
            distance = newRidePost.getDistance();
            duration = newRidePost.getDuration();
            cost = newRidePost.getCost();
            dScore = newRidePost.getDriverScore();
            rScore = newRidePost.getRiderScore();
            isTaken = newRidePost.getIsTaken();
            isComplete = newRidePost.getIsCompleted();
            carID = newRidePost.getCarID();

            
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();
            String sql = "INSERT INTO [dbo].[Ride] " + 
            "([pickUpLocation],[destination],[driverID],[riderID],[rideDate],[trait_smoking],[trait_eating],[trait_talking],[trait_carseat]," + 
            "[distance],[duration],[cost],[driverRateScore],[riderRatingScore],[carID],[isTaken],[isCompleted])" +
            " VALUES ('"+origin+"','"+dest+"',"+driverID+","+riderID+", CAST('"+rideDateTime+"' AS DATETIME),'"+smoke+"','"+eat+"','"+talk+"','"+carseat+
            "','"+distance+"','"+duration+"','"+cost+"','"+dScore+"','"+rScore+"','"+carID+"','"+isTaken+"','"+isComplete+"')";
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

    @RequestMapping(value = "/viewRides", method = RequestMethod.GET) 
    public ResponseEntity<List<Ride>> AllRides(@RequestParam(value = "Origin", defaultValue ="") String pickup,@RequestParam(value = "Destination", defaultValue ="") String destination, @RequestParam(value = "DateTime", defaultValue ="") String date_Time,@RequestParam(value = "User", defaultValue ="") String user){
        List response = new ArrayList<Ride>();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();
        String sql="";

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();

            if (!user.isBlank()){ //rider get Rides
                sql = "SELECT * FROM [dbo].[Ride] WHERE riderID = '"+user+"' OR driverID = '"+user+"' ORDER BY rideDate DESC ";
            }


            else if (pickup.isBlank() && destination.isBlank() && date_Time.isBlank() && user.isBlank()){ //all rides

                sql = "SELECT * FROM [dbo].[Ride] ORDER BY rideDate Desc;";
            }
            //date can be sent as 5/26/2022
            else if (!pickup.isBlank() && !destination.isBlank() && !date_Time.isBlank()&& user.isBlank()){ //show specific ride details
                sql = "SELECT TOP (1) * FROM [dbo].[Ride] WHERE pickUpLocation LIKE '%"+pickup+"%' AND destination LIKE '%"+destination+"%' AND rideDate LIKE '%"+date_Time+"%'";
            }

            else {
                allRides.setRiderID("Invalid request sent.");
            return new ResponseEntity(allRides,HttpStatus.OK);

            }


            ResultSet rslt = stmnt.executeQuery(sql);

            
                while(rslt.next()){ //get all user data from query
                    allRides = new Ride();
                    allRides.setRideID(rslt.getInt("rideID"));
                    allRides.setPickUpLoc(rslt.getString("pickUpLocation"));
                    allRides.setDest(rslt.getString("destination"));
                    allRides.setDuration(rslt.getFloat("duration"));
                    allRides.setDistance(rslt.getFloat("distance"));
                    allRides.setCost(rslt.getFloat("cost"));
                    allRides.setDriverID(rslt.getString("driverID"));
                    allRides.setRiderID(rslt.getString("riderID"));
                    allRides.setDriverScore(rslt.getFloat("driverRateScore"));
                    allRides.setRiderScore(rslt.getFloat("riderRatingScore"));
                    allRides.setRideDate(rslt.getString("rideDate"));
                    allRides.setSmoking(rslt.getByte("trait_smoking"));
                    allRides.setEating(rslt.getByte("trait_eating"));
                    allRides.setTalking(rslt.getByte("trait_talking"));
                    allRides.setCarseat(rslt.getByte("trait_carseat"));
                    allRides.setCarID(rslt.getInt("carID"));
                    allRides.setIsTaken(rslt.getByte("isTaken"));
                    allRides.setIsCompleted(rslt.getByte("isCompleted"));
                    ViewAllRides.put(allRides.getRideID(), allRides);
                }
                con.close();
            
            response = new ArrayList<>(ViewAllRides.values());
        }
        catch (SQLException e) {
            allRides.setRiderID("SQL Error  "+  e.toString());
            return new ResponseEntity(allRides,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Ride>>(response, HttpStatus.OK);
        
      
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
