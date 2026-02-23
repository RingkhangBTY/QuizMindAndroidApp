package com.ringkhang.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class UserDetailsDTO {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    public String getUsername() { return username; }
    public String getEmail()    { return email; }
}
