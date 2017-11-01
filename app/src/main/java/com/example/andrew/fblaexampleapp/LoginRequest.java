package com.example.andrew.fblaexampleapp;

/**
 * Created by Andrew on 11/1/2017.
 */

public class LoginRequest {
    public String UserName;
    public String Password;

    public LoginRequest(String username, String password) {
        UserName = username;
        Password = password;
    }
}
