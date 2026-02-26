package com.ringkhang.myapplication.data;

import android.content.Context;

import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;
import com.ringkhang.myapplication.network.HistoryApiService;
import com.ringkhang.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryUiDatasource {
    private Context context;
    private HistoryApiService historyApiService;

    public HistoryUiDatasource(Context context) {
        this.context = context;
        historyApiService = RetrofitClient.getInstance(context).create(HistoryApiService.class);
    }

    public void getAllTestScoreHistory(MyDataResponseCallBack<ArrayList<ScoreHistoryDisplay>> callBack){
        historyApiService.getAllTestScoreHistory()
                .enqueue(new Callback<List<ScoreHistoryDisplay>>() {
                    @Override
                    public void onResponse(Call<List<ScoreHistoryDisplay>> call, Response<List<ScoreHistoryDisplay>> response) {
                        if (response.isSuccessful() && response.body()!= null){
                            ArrayList<ScoreHistoryDisplay> scoreHistoryDisplays = new ArrayList<>(response.body());
                            callBack.onSuccess(scoreHistoryDisplays);
                        }else if (response.code() == 401){
                            callBack.onError(response.code(),response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ScoreHistoryDisplay>> call, Throwable t) {
                        callBack.onFailure(t);
                        Logger.getLogger(HistoryApiService.class.getName())
                                .log(
                                        Level.SEVERE,"Failed to load data from SERVER"
                                );
                    }
                });
    }
}
