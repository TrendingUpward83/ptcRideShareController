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
        String profileImgFile;
        //profile image? //TODO: handle profile image association & display


        public String getProfileImgFile() {
            return profileImgFile;
        }

        public void setProfileImgFile(String profileImgFile) {
            this.profileImgFile = profileImgFile;
        }

        public User(){
            
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
