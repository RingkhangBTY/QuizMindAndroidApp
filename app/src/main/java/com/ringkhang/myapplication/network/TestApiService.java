package com.ringkhang.myapplication.network;

import com.ringkhang.myapplication.models_DTO.QuestionDetails;
import com.ringkhang.myapplication.models_DTO.SubmitQuizRequest;
import com.ringkhang.myapplication.models_DTO.TestReview;
import com.ringkhang.myapplication.models_DTO.UserInForQuizTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestApiService {
    @POST("/quiz/start")
    Call<List<QuestionDetails>> getQuizDetails(@Body UserInForQuizTest input);

    @POST("/quiz/submit")
    Call<TestReview> submitQuiz(@Body SubmitQuizRequest submitQuizRequest);
}
