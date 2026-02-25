package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;

public class UserDetailsTable {

    @SerializedName("username")
    private String username;

    @SerializedName("pass")          // matches your field name in Spring Boot
    private String pass;

    @SerializedName("email")
    private String email;

    public UserDetailsTable(String username, String pass, String email) {
        this.username = username;
        this.pass     = pass;
        this.email    = email;
    }
}
