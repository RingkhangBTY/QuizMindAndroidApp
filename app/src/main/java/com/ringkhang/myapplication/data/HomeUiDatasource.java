package com.ringkhang.myapplication.data;

import android.content.Context;
import android.widget.Toast;

import com.ringkhang.myapplication.models_DTO.InitialAppPayload;
import com.ringkhang.myapplication.network.HomeApiService;
import com.ringkhang.myapplication.network.RetrofitClient;
import com.ringkhang.myapplication.ui.MainActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUiDatasource {

    private Context context;
    private HomeApiService homeApiService;

    public HomeUiDatasource(Context context) {
        this.context = context;
        homeApiService = RetrofitClient.getInstance(context).create(HomeApiService.class);

    }

    public void getInitialMainData(boolean isRetry, MyDataResponseCallBack callBack) {
        homeApiService.getInitialData()
                .enqueue(new Callback<InitialAppPayload>() {
            @Override
            public void onResponse(Call<InitialAppPayload> call, Response<InitialAppPayload> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 401) {
                    Toast.makeText(context,
                            "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    callBack.onError(response.code(), response.message());
                } else {
                    Toast.makeText(context,
                            "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InitialAppPayload> call, Throwable t) {
                if (!isRetry) {
                    RetrofitClient.reset(context);
                    Toast.makeText(context, "Reconnecting...", Toast.LENGTH_SHORT).show();
                    getInitialMainData(true, callBack);
                } else {
                    callBack.onFailure(t);
                    Toast.makeText(context,
                            "Failed to fetch user data!!", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(MainActivity.class.getName())
                            .log(Level.SEVERE, "Failed to fetch user data from SERVER");
                }
            }
        });

    }
}
