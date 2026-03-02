package com.ringkhang.myapplication.network;

import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;
import com.ringkhang.myapplication.models_DTO.TestReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface HistoryApiService {
    @GET("history/scoreHistory")
    Call<List<ScoreHistoryDisplay>> getAllTestScoreHistory();

    @GET("history/review_test")
    Call<TestReview> getTestReviewDetails(@Query("testHisId") int testHisId);
}
