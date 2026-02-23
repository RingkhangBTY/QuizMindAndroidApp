package com.ringkhang.myapplication.network;

import android.content.Context;

import com.ringkhang.myapplication.utils.SessionManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    // Emulator  → 10.0.2.2
    // Real device on same WiFi → your PC local IP e.g. 192.168.1.5
    private static final String BASE_URL = "http://192.168.1.3:8080/";
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        String token = SessionManager.getInstance(context).getToken();
                        Request original = chain.request();

                        // No token yet (login/register) → send as-is
                        if (token == null) return chain.proceed(original);

                        // Attach JWT to every request
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    // ScalarsConverter FIRST — handles plain String response from /login/auth
                    .addConverterFactory(ScalarsConverterFactory.create())
                    // GsonConverter SECOND — handles JSON responses
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}