package com.ringkhang.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME    = "AppSession";
    private static final String KEY_TOKEN    = "jwt_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL    = "email";

    private static SessionManager instance;
    private final SharedPreferences prefs;

    private SessionManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) instance = new SessionManager(context);
        return instance;
    }

    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public void saveUser(String username, String email) {
        prefs.edit()
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    public String getUsername() { return prefs.getString(KEY_USERNAME, ""); }
    public String getEmail()    { return prefs.getString(KEY_EMAIL, ""); }

    public boolean isLoggedIn() { return getToken() != null; }

    public void logout() { prefs.edit().clear().apply(); }
}