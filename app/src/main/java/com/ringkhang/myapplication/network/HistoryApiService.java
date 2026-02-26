package com.ringkhang.myapplication.network;

import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface HistoryApiService {
    @GET("history/scoreHistory")
    Call<List<ScoreHistoryDisplay>> getAllTestScoreHistory();
}
