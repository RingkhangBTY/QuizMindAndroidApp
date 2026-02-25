package com.ringkhang.myapplication.network;

import com.ringkhang.myapplication.models_DTO.InitialAppPayloadDTO;
import com.ringkhang.myapplication.models_DTO.UserDetailsDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeApiService {
    // GET /users — returns current user (JWT required)
    @GET("users")
    Call<UserDetailsDTO> getUserDetails();

    // GET /initial_data — full dashboard payload (JWT required)
    @GET("initial_data")
    Call<InitialAppPayloadDTO> getInitialData();
}
