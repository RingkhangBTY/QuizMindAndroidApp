package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;

public class UserDetailsDTO {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    public int getId(){return id;}
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
}
