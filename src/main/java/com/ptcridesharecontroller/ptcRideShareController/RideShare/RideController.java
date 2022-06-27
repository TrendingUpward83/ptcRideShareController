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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
                    loginUser.setProfileImgFile(rslt.getString("profile_image"));
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
    public ResponseEntity<List<Ride>> AllRides(){
        Ride allRides = new Ride();
        Map<Integer, Ride> ViewAllRides = new HashMap<Integer, Ride>();
        List response = new ArrayList<Ride>();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();
        String sql="";

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();

   
            sql = "SELECT * FROM [dbo].[Ride] WHERE isCompleted = 0 ORDER BY pickUpLocation;";
            

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

    @RequestMapping(value = "/car", method = RequestMethod.GET) 
    public ResponseEntity<Car> carInfo(@RequestParam(value = "userID", defaultValue ="none") String uID){

        Car carInfo = new Car();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();
            //the sort DESC in the below query isn't working..
            String sql = "SELECT * FROM [dbo].[car] WHERE driverID = '"+uID+"' ;";
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

    @RequestMapping(value = "/user", method = RequestMethod.GET) //creating response LIST with max value of 1 user so no more than 1 user can login
    public ResponseEntity<User> userInfo(@RequestParam(value = "User", defaultValue ="none") String userID){

        User aUser = new User();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();
            //the sort DESC in the below query isn't working..
            String sql = "SELECT * FROM [dbo].[UserProfile] WHERE userID = '"+userID+"';";
            ResultSet rslt = stmnt.executeQuery(sql);
        

            if (!rslt.wasNull()){

                while(rslt.next()){ //get all user data from query
                    aUser.setUserFName(rslt.getString("name_first"));
                    aUser.setUserLName(rslt.getString("name_last"));
                    aUser.setuDriverScore(rslt.getFloat("driverRateScore"));
                    aUser.setuRiderScore(rslt.getFloat("riderRatingScore"));
                    aUser.setIsDriver(rslt.getByte("active_driver"));    
                    aUser.setProfileImgFile(rslt.getString("profile_image"));
                }
            
            con.close();

            return new ResponseEntity<>(aUser,HttpStatus.OK); // if email found, return status code 200
                             
            }
            else {
                aUser.setUserFName("No user with this ID exists.");
                return new ResponseEntity<>(aUser,HttpStatus.NO_CONTENT);
            }
        }
        catch (SQLException e) {
            aUser.setUserFName("SQL Error  "+  e.toString());
            return new ResponseEntity<>(aUser,HttpStatus.BAD_REQUEST);
        }  
      
    }
    @RequestMapping(value = "/viewMyRides", method = RequestMethod.GET) 
        
    public ResponseEntity<List<Ride>> MyRides(@RequestParam(value = "User") String user){
        Ride allRides = new Ride();
        Map<Integer, Ride> ViewMyRides = new HashMap<Integer, Ride>();
        List myRidesResponse = new ArrayList<Ride>();
        String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
        ObjectMapper mapper = new ObjectMapper();
        String sql="";

        try {   
                
            Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
            Statement stmnt = con.createStatement();

            sql = "SELECT * FROM [dbo].[Ride] WHERE driverID ='"+user+"' OR riderID = '"+user+"' AND isCompleted = 0 ORDER BY pickUpLocation;";

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
                    ViewMyRides.put(allRides.getRideID(), allRides);
                }
                con.close();
            
            myRidesResponse = new ArrayList<>(ViewMyRides.values());
        }
        catch (SQLException e) {
            allRides.setRiderID("SQL Error  "+  e.toString());
            return new ResponseEntity(allRides,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Ride>>(myRidesResponse, HttpStatus.OK);     
      
    }
    // PUT HERE
  @RequestMapping(value = "/acceptRide/Rider", method = RequestMethod.PUT) //posted already has driver, get rider details  
  public ResponseEntity<Ride> riderAccept(@RequestParam(value = "accRideId") String accRideId,
      @RequestParam(value = "riderID") String rider) {

    //Reponses to check to confirm ride data is posted- get the ride data posted

    Ride updtRide = new Ride();
    String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    String putSql;
    String rideSql;

    try {

      Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
      Statement stmnt = con.createStatement();

      putSql = "UPDATE Ride SET riderID ='" + rider + "', isTaken = 1 WHERE rideID = '" + accRideId + "';"; //send the update command with the parameters
      stmnt.executeUpdate(putSql);

      rideSql = "SELECT * FROM Ride WHERE rideID = " + accRideId;

      ResultSet rslt = stmnt.executeQuery(rideSql);

      while (rslt.next()) { //get the updated record
        updtRide = new Ride();
        updtRide.setRideID(rslt.getInt("rideID"));
        updtRide.setPickUpLoc(rslt.getString("pickUpLocation"));
        updtRide.setDest(rslt.getString("destination"));
        updtRide.setDuration(rslt.getFloat("duration"));
        updtRide.setDistance(rslt.getFloat("distance"));
        updtRide.setCost(rslt.getFloat("cost"));
        updtRide.setDriverID(rslt.getString("driverID"));
        updtRide.setRiderID(rslt.getString("riderID"));
        updtRide.setDriverScore(rslt.getFloat("driverRateScore"));
        updtRide.setRiderScore(rslt.getFloat("riderRatingScore"));
        updtRide.setRideDate(rslt.getString("rideDate"));
        updtRide.setSmoking(rslt.getByte("trait_smoking"));
        updtRide.setEating(rslt.getByte("trait_eating"));
        updtRide.setTalking(rslt.getByte("trait_talking"));
        updtRide.setCarseat(rslt.getByte("trait_carseat"));
        updtRide.setCarID(rslt.getInt("carID"));
        updtRide.setIsTaken(rslt.getByte("isTaken"));
        updtRide.setIsCompleted(rslt.getByte("isCompleted"));

      }
      con.close();

    } catch (SQLException e) {
      updtRide.setRiderID("SQL Error  " + e.toString());
      return new ResponseEntity<>(updtRide, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updtRide, HttpStatus.OK);

  }

  @RequestMapping(value = "/acceptRide/Driver", method = RequestMethod.PUT) //posted already has rider, get driver details
  public ResponseEntity<Ride> driverAccept(@RequestParam(value = "accRideId") String accRideId,
      @RequestParam(value = "driverID") String driver) {

    //Reponses to check to confirm ride data is posted- get the ride data posted

    Ride updtRide = new Ride();
    String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    String putSql;
    String rideSql;

    try {

      Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
      Statement stmnt = con.createStatement();

      putSql = "UPDATE Ride SET driverID ='" + driver + "', isTaken = 1 WHERE rideID = '" + accRideId + "';"; //send the update command with the parameters
      stmnt.executeUpdate(putSql);

      rideSql = "SELECT * FROM Ride WHERE rideID = " + accRideId;

      ResultSet rslt = stmnt.executeQuery(rideSql);

      while (rslt.next()) { //get the updated record
        updtRide = new Ride();
        updtRide.setRideID(rslt.getInt("rideID"));
        updtRide.setPickUpLoc(rslt.getString("pickUpLocation"));
        updtRide.setDest(rslt.getString("destination"));
        updtRide.setDuration(rslt.getFloat("duration"));
        updtRide.setDistance(rslt.getFloat("distance"));
        updtRide.setCost(rslt.getFloat("cost"));
        updtRide.setDriverID(rslt.getString("driverID"));
        updtRide.setRiderID(rslt.getString("riderID"));
        updtRide.setDriverScore(rslt.getFloat("driverRateScore"));
        updtRide.setRiderScore(rslt.getFloat("riderRatingScore"));
        updtRide.setRideDate(rslt.getString("rideDate"));
        updtRide.setSmoking(rslt.getByte("trait_smoking"));
        updtRide.setEating(rslt.getByte("trait_eating"));
        updtRide.setTalking(rslt.getByte("trait_talking"));
        updtRide.setCarseat(rslt.getByte("trait_carseat"));
        updtRide.setCarID(rslt.getInt("carID"));
        updtRide.setIsTaken(rslt.getByte("isTaken"));
        updtRide.setIsCompleted(rslt.getByte("isCompleted"));

      }
      con.close();

    } catch (SQLException e) {
      updtRide.setRiderID("SQL Error  " + e.toString());
      return new ResponseEntity<>(updtRide, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updtRide, HttpStatus.OK);

  }

  @RequestMapping(value = "/rateRide/Driver", method = RequestMethod.PUT) //rider rate driver and complete ride if driver already rated(will send 'complete' if so)
  public ResponseEntity<Ride> rateDriver(@RequestParam(value = "accRideId") String accRideId,
      @RequestParam(value = "rating") float driversRating, @RequestParam(value = "complete", defaultValue = "0") byte completed) {

    //Reponses to check to confirm ride data is posted- get the ride data posted

    Ride updtRide = new Ride();
    String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    String putSql ="";
    String rideSql;

    try {

      Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
      Statement stmnt = con.createStatement();

      if(completed ==0){
        putSql = "UPDATE Ride SET driverRateScore ='" + driversRating + "' WHERE rideID = '" + accRideId + "';"; //send the update command with the parameters
      }
      else if (completed ==1){
        putSql = "UPDATE Ride SET driverRateScore ='" + driversRating + "', isCompleted = '"+1+"' WHERE rideID = '" + accRideId + "';";
      }
      
      stmnt.executeUpdate(putSql);

      rideSql = "SELECT * FROM Ride WHERE rideID = " + accRideId;

      ResultSet rslt = stmnt.executeQuery(rideSql);

      while (rslt.next()) { //get the updated record
        updtRide = new Ride();
        updtRide.setRideID(rslt.getInt("rideID"));
        updtRide.setPickUpLoc(rslt.getString("pickUpLocation"));
        updtRide.setDest(rslt.getString("destination"));
        updtRide.setDuration(rslt.getFloat("duration"));
        updtRide.setDistance(rslt.getFloat("distance"));
        updtRide.setCost(rslt.getFloat("cost"));
        updtRide.setDriverID(rslt.getString("driverID"));
        updtRide.setRiderID(rslt.getString("riderID"));
        updtRide.setDriverScore(rslt.getFloat("driverRateScore"));
        updtRide.setRiderScore(rslt.getFloat("riderRatingScore"));
        updtRide.setRideDate(rslt.getString("rideDate"));
        updtRide.setSmoking(rslt.getByte("trait_smoking"));
        updtRide.setEating(rslt.getByte("trait_eating"));
        updtRide.setTalking(rslt.getByte("trait_talking"));
        updtRide.setCarseat(rslt.getByte("trait_carseat"));
        updtRide.setCarID(rslt.getInt("carID"));
        updtRide.setIsTaken(rslt.getByte("isTaken"));
        updtRide.setIsCompleted(rslt.getByte("isCompleted"));

      }
      con.close();

    } catch (SQLException e) {
      updtRide.setRiderID("SQL Error  " + e.toString());
      return new ResponseEntity<>(updtRide, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updtRide, HttpStatus.OK);

  }
  @RequestMapping(value = "/rateRide/Rider", method = RequestMethod.PUT) //rider rate driver and complete ride if driver already rated(will send 'complete' if so)
  public ResponseEntity<Ride> rateRider(@RequestParam(value = "accRideId") String accRideId,
      @RequestParam(value = "rating") float ridersRating, @RequestParam(value = "complete", defaultValue = "0") byte completed) {

    //Reponses to check to confirm ride data is posted- get the ride data posted

    Ride updtRide = new Ride();
    String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    String putSql ="";
    String rideSql;

    try {

      Connection con = DriverManager.getConnection(connectionURL); //connect to the DB
      Statement stmnt = con.createStatement();

      if(completed ==0){
        putSql = "UPDATE Ride SET riderRatingScore ='" + ridersRating + "' WHERE rideID = '" + accRideId + "';"; //send the update command with the parameters
      }
      else if (completed ==1){
        putSql = "UPDATE Ride SET riderRatingScore ='" + ridersRating + "', isCompleted = '"+1+"' WHERE rideID = '" + accRideId + "';";
      }
      
      stmnt.executeUpdate(putSql);

      rideSql = "SELECT * FROM Ride WHERE rideID = " + accRideId;

      ResultSet rslt = stmnt.executeQuery(rideSql);

      while (rslt.next()) { //get the updated record
        updtRide = new Ride();
        updtRide.setRideID(rslt.getInt("rideID"));
        updtRide.setPickUpLoc(rslt.getString("pickUpLocation"));
        updtRide.setDest(rslt.getString("destination"));
        updtRide.setDuration(rslt.getFloat("duration"));
        updtRide.setDistance(rslt.getFloat("distance"));
        updtRide.setCost(rslt.getFloat("cost"));
        updtRide.setDriverID(rslt.getString("driverID"));
        updtRide.setRiderID(rslt.getString("riderID"));
        updtRide.setDriverScore(rslt.getFloat("driverRateScore"));
        updtRide.setRiderScore(rslt.getFloat("riderRatingScore"));
        updtRide.setRideDate(rslt.getString("rideDate"));
        updtRide.setSmoking(rslt.getByte("trait_smoking"));
        updtRide.setEating(rslt.getByte("trait_eating"));
        updtRide.setTalking(rslt.getByte("trait_talking"));
        updtRide.setCarseat(rslt.getByte("trait_carseat"));
        updtRide.setCarID(rslt.getInt("carID"));
        updtRide.setIsTaken(rslt.getByte("isTaken"));
        updtRide.setIsCompleted(rslt.getByte("isCompleted"));

      }
      con.close();

    } catch (SQLException e) {
      updtRide.setRiderID("SQL Error  " + e.toString());
      return new ResponseEntity<>(updtRide, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updtRide, HttpStatus.OK);

  }



}
