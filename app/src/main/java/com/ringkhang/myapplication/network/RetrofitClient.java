package com.ringkhang.myapplication.network;

import android.content.Context;

import com.ringkhang.myapplication.utils.SessionManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.2:8080/";
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {
            retrofit = buildRetrofit(context);
        }
        return retrofit;
    }

    /** Call this on connection failure to force a fresh client */
    public static void reset(Context context) {
        retrofit = buildRetrofit(context);
    }

    private static Retrofit buildRetrofit(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()

                // JWT interceptor
                .addInterceptor(chain -> {
                    String token = SessionManager.getInstance(context).getToken();
                    Request original = chain.request();
                    if (token == null) return chain.proceed(original);
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(request);
                })

                // This prevents stale connection issues after Tomcat restarts
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("Connection", "close")
                            .build();
                    return chain.proceed(request);
                })

                .addInterceptor(logging)

                //Timeouts — app won't hang forever waiting on dead server
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)

                //Auto retry on connection failure
                .retryOnConnectionFailure(true)

                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}