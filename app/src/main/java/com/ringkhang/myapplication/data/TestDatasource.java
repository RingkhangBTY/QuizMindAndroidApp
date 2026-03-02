package com.ringkhang.myapplication.data;

import android.content.Context;

import com.ringkhang.myapplication.models_DTO.QuestionDetails;
import com.ringkhang.myapplication.models_DTO.SubmitQuizRequest;
import com.ringkhang.myapplication.models_DTO.TestReview;
import com.ringkhang.myapplication.models_DTO.UserInForQuizTest;
import com.ringkhang.myapplication.network.RetrofitClient;
import com.ringkhang.myapplication.network.TestApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestDatasource {
    private Context context;
    private TestApiService testApiService;

    public TestDatasource(Context context) {
        this.context = context;
        testApiService = RetrofitClient.getInstance(context).create(TestApiService.class);
    }

    public void getTestQuestions(MyDataResponseCallBack<ArrayList<QuestionDetails>> callBack, UserInForQuizTest userInput){
        testApiService.getQuizDetails(userInput)
                .enqueue(new Callback<List<QuestionDetails>>() {
                    @Override
                    public void onResponse(Call<List<QuestionDetails>> call,
                                           Response<List<QuestionDetails>> response) {
                        if (response.isSuccessful() && response.body()!= null){
//                            ArrayList<QuestionDetails> questionDetails = new ArrayList<>(response.body());
                            callBack.onSuccess(new ArrayList<>(response.body()));
                            Logger.getLogger(TestDatasource.class.getName())
                                    .log(Level.INFO,"Get the quiz details from datasource");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<QuestionDetails>> call, Throwable t) {
                        callBack.onFailure(t);
                        Logger.getLogger(TestDatasource.class.getName())
                                .log(
                                        Level.SEVERE,"Failed to load data from SERVER"
                                );
                    }
                });
    }

    public void submitQuiz(MyDataResponseCallBack<TestReview> callBack, SubmitQuizRequest submitQuizRequest){
        testApiService.submitQuiz(submitQuizRequest)
                .enqueue(new Callback<TestReview>() {
                    @Override
                    public void onResponse(Call<TestReview> call, Response<TestReview> response) {
                        if (response.isSuccessful() || response.body() != null){
                            callBack.onSuccess(response.body());
                        }else {
                            callBack.onError(response.code(),"Fails to submit quiz!");
                        }
                    }

                    @Override
                    public void onFailure(Call<TestReview> call, Throwable t) {
                        callBack.onFailure(t);
                    }
                });
    }
}
