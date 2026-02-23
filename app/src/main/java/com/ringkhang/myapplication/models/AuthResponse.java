package com.ringkhang.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("token")      // match your Spring Boot JSON field name
    private String token;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("message")
    private String message;

    public String getToken()   { return token; }
    public String getUsername()    { return username; }
    public String getEmail()   { return email; }
    public String getMessage() { return message; }
}