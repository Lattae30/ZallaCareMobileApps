package com.example.zalla.model;

import java.io.Serializable;

public class Users implements Serializable {
    private String userId;
    private String fullname;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String userImgProfile;

    public Users(String userId, String fullname, String username, String email,
                String phone, String address, String password, String userImgProfile){
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.userImgProfile = userImgProfile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImgProfile() {
        return userImgProfile;
    }

    public void setUserImgProfile(String userImgProfile) {
        this.userImgProfile = userImgProfile;
    }
}
