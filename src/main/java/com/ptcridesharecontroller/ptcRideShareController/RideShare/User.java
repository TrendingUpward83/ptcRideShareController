package com.ptcridesharecontroller.ptcRideShareController.RideShare;



    public class User {

        //dbo.AspNetUsers
        String userEmail;
        String  userName;
        String userID;  //key to connect to
        //dbo.UserProfile
        String userFName;
        String userLName;
        float uDriverScore;
        float uRiderScore;
        byte isDriver;
        Integer uStudID;
        //profile image? //TODO: handle profile image association & display

            //Car details (only have if is driver)

        Integer carID;
        String carMake;
        String carModel;
        String carColor;
        String carPlateNum;
        Byte CarIsActive;

        public User(){
            
        }
    
        public Byte getCarIsActive() {
            return CarIsActive;
        }
        public void setCarIsActive(Byte carIsActive) {
            CarIsActive = carIsActive;
        }

        public Integer getCarID() {
            return carID;
        }
    
        public void setCarID(Integer carID) {
            this.carID = carID;
        }
    
        public String getCarMake() {
            return carMake;
        }
    
        public void setCarMake(String carMake) {
            this.carMake = carMake;
        }
    
        public String getCarModel() {
            return carModel;
        }
    
        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }
    
        public String getCarColor() {
            return carColor;
        }
    
        public void setCarColor(String carColor) {
            this.carColor = carColor;
        }
    
        public String getCarPlateNum() {
            return carPlateNum;
        }
    
        public void setCarPlateNum(String carPlateNum) {
            this.carPlateNum = carPlateNum;
        }
        

        public User(String userEmail){
            this.userEmail = userEmail;
        }

    
        public String getUserEmail() {
            return userEmail;
        }
    
        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }
    
        public String getUserName() {
            return userName;
        }
    
        public void setUserName(String userName) {
            this.userName = userName;
        }
    
        public String getUserID() {
            return userID;
        }
    
        public void setUserID(String userID) {
            this.userID = userID;
        }
    
        public String getUserFName() {
            return userFName;
        }
    
        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }
    
        public String getUserLName() {
            return userLName;
        }
    
        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }
    
        public float getuDriverScore() {
            return uDriverScore;
        }
    
        public void setuDriverScore(float uDriverScore) {
            this.uDriverScore = uDriverScore;
        }
    
        public float getuRiderScore() {
            return uRiderScore;
        }
    
        public void setuRiderScore(float uRiderScore) {
            this.uRiderScore = uRiderScore;
        }
    
        public byte getIsDriver() {
            return isDriver;
        }
    
        public void setIsDriver(byte isDriver) {
            this.isDriver = isDriver;
        }
    
        public Integer getuStudID() {
            return uStudID;
        }
    
        public void setuStudID(Integer uStudID) {
            this.uStudID = uStudID;
        }
}
