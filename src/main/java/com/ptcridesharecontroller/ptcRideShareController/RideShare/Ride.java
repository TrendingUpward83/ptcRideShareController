package com.ptcridesharecontroller.ptcRideShareController.RideShare;

import java.sql.Date;

public class Ride {
    int rideID;
    String pickUpLoc;
    String dest;
    float duration;
    float distance;
    float cost;
    String driverID;
    String riderID;
    float driverScore;
    float riderScore;
    String rideDate; //used java.sql date
    Byte smoking;
    Byte eating;
    Byte talking;
    Byte carseat;
    int carID;
    Byte isTaken;
    Byte isCompleted;

    

    public int getRideID() {
        return rideID;
    }


    public void setRideID(int rideID) {
        this.rideID = rideID;
    }


    public String getPickUpLoc() {
        return pickUpLoc;
    }


    public void setPickUpLoc(String pickUpLoc) {
        this.pickUpLoc = pickUpLoc;
    }


    public String getDest() {
        return dest;
    }


    public void setDest(String dest) {
        this.dest = dest;
    }


    public float getDuration() {
        return duration;
    }


    public void setDuration(float duration) {
        this.duration = duration;
    }


    public float getDistance() {
        return distance;
    }


    public void setDistance(float distance) {
        this.distance = distance;
    }


    public float getCost() {
        return cost;
    }


    public void setCost(float cost) {
        this.cost = cost;
    }


    public String getDriverID() {
        return driverID;
    }


    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }


    public String getRiderID() {
        return riderID;
    }


    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }


    public float getDriverScore() {
        return driverScore;
    }


    public void setDriverScore(float driverScore) {
        this.driverScore = driverScore;
    }


    public float getRiderScore() {
        return riderScore;
    }


    public void setRiderScore(float riderScore) {
        this.riderScore = riderScore;
    }


    public String getRideDate() {
        return rideDate;
    }


    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }


    public Byte getSmoking() {
        return smoking;
    }


    public void setSmoking(Byte smoking) {
        this.smoking = smoking;
    }


    public Byte getEating() {
        return eating;
    }


    public void setEating(Byte eating) {
        this.eating = eating;
    }


    public Byte getTalking() {
        return talking;
    }


    public void setTalking(Byte talking) {
        this.talking = talking;
    }
    public int getCarID() {
        return carID;
    }


    public void setCarID(int carID) {
        this.carID = carID;
    }

    public Byte getCarseat() {
        return carseat;
    }


    public void setCarseat(Byte carseat) {
        this.carseat = carseat;
    }


    public Byte getIsTaken() {
        return isTaken;
    }


    public void setIsTaken(Byte isTaken) {
        this.isTaken = isTaken;
    }


    public Byte getIsCompleted() {
        return isCompleted;
    }


    public void setIsCompleted(Byte isCompleted) {
        this.isCompleted = isCompleted;
    }


    public Ride(String pickUpLoc, String dest, String driverID,String rideDate, Byte smoking, Byte eating, Byte talking,
            Byte carseat) {
        
        this.pickUpLoc = pickUpLoc;
        this.dest = dest;
        this.driverID = driverID;
        this.rideDate = rideDate;
        this.smoking = smoking;
        this.eating = eating;
        this.talking = talking;
        this.carseat = carseat;

    }


    public Ride() {
    }


    @Override
    public String toString() {
        return "Ride [cost=" + cost + ", dest=" + dest + ", driverID=" + driverID + ", isCompleted=" + isCompleted
                + ", isTaken=" + isTaken + ", pickUpLoc=" + pickUpLoc + ", rideDate=" + rideDate + ", rideID=" + rideID
                + ", riderID=" + riderID + "]";
    }
    

}
