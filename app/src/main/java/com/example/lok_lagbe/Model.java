package com.example.lok_lagbe;

public class Model {

    String Job,Full_Name,Permanent_Address,UserId,Charge,Completed_Job,Mobile_No,Date_Of_Birth,Email,Fathers_Name,GENDER,Nid_Number,CustomerUserId,Rating;

    Model(){}

    public Model(String job, String full_Name, String permanent_Address, String userId, String charge, String completed_Job, String mobile_No, String date_Of_Birth, String email, String fathers_Name, String GENDER, String nid_Number, String customerUserId, String rating) {
        Job = job;
        Full_Name = full_Name;
        Permanent_Address = permanent_Address;
        UserId = userId;
        Charge = charge;
        Completed_Job = completed_Job;
        Mobile_No = mobile_No;
        Date_Of_Birth = date_Of_Birth;
        Email = email;
        Fathers_Name = fathers_Name;
        this.GENDER = GENDER;
        Nid_Number = nid_Number;
        CustomerUserId = customerUserId;
        Rating = rating;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getPermanent_Address() {
        return Permanent_Address;
    }

    public void setPermanent_Address(String permanent_Address) {
        Permanent_Address = permanent_Address;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public String getCompleted_Job() {
        return Completed_Job;
    }

    public void setCompleted_Job(String completed_Job) {
        Completed_Job = completed_Job;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFathers_Name() {
        return Fathers_Name;
    }

    public void setFathers_Name(String fathers_Name) {
        Fathers_Name = fathers_Name;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getNid_Number() {
        return Nid_Number;
    }

    public void setNid_Number(String nid_Number) {
        Nid_Number = nid_Number;
    }

    public String getCustomerUserId() {
        return CustomerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        CustomerUserId = customerUserId;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}

