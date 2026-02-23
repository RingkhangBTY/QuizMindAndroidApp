package com.ringkhang.myapplication.network;

import com.ringkhang.myapplication.models.InitialAppPayloadDTO;
import com.ringkhang.myapplication.models.UserDetailsDTO;
import com.ringkhang.myapplication.models.UserDetailsTable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApiService {

    // POST /register — body is UserDetailsTable { username, pass, email }
    @POST("register")
    Call<Void> register(@Body UserDetailsTable user);

    // POST /login/auth?username=x&pass=y — returns raw JWT string
    @POST("login/auth")
    Call<String> login(
            @Query("username") String username,
            @Query("pass") String pass
    );

    // GET /users — returns current user (JWT required)
    @GET("users")
    Call<UserDetailsDTO> getUserDetails();

    // GET /initial_data — full dashboard payload (JWT required)
    @GET("initial_data")
    Call<InitialAppPayloadDTO> getInitialData();
}