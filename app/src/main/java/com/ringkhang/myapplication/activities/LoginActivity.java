package com.ringkhang.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models_DTO.UserDetailsTable;
import com.ringkhang.myapplication.network.AuthApiService;
import com.ringkhang.myapplication.network.RetrofitClient;
import com.ringkhang.myapplication.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText    etUsername, etEmail, etPassword;
    private Button      btnSubmit;
    private TextView    tvToggle, tvTitle;
    private ProgressBar progressBar;

    private boolean isLoginMode = true;
    private AuthApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Skip login if already have token
        if (SessionManager.getInstance(this).isLoggedIn()) {
            goToMain();
            return;
        }

        initViews();
        api = RetrofitClient.getInstance(this).create(AuthApiService.class);
        updateUI();

        btnSubmit.setOnClickListener(v -> {
            if (isLoginMode) handleLogin();
            else             handleRegister();
        });

        tvToggle.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            updateUI();
        });
    }

    private void initViews() {
        etUsername  = findViewById(R.id.etUsername);
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        btnSubmit   = findViewById(R.id.btnSubmit);
        tvToggle    = findViewById(R.id.tvToggle);
        tvTitle     = findViewById(R.id.tvTitle);
        progressBar = findViewById(R.id.progressBar);
    }

    private void updateUI() {
        if (isLoginMode) {
            tvTitle.setText("Welcome Back");
            btnSubmit.setText("Login");
            tvToggle.setText("Don't have an account? Register");
            etEmail.setVisibility(View.GONE);   // login only needs username + pass
        } else {
            tvTitle.setText("Create Account");
            btnSubmit.setText("Register");
            tvToggle.setText("Already have an account? Login");
            etEmail.setVisibility(View.VISIBLE);
        }
    }

    // ── Login → POST /login/auth?username=x&pass=y ────────────

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String pass     = etPassword.getText().toString().trim();

        if (username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        api.login(username, pass).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    // Spring Boot returns raw JWT string
                    String token = response.body();
                    SessionManager.getInstance(LoginActivity.this).saveToken(token);
                    SessionManager.getInstance(LoginActivity.this).saveUser(username, "");
                    goToMain();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Login failed. Check credentials.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ── Register → POST /register body: { username, pass, email } ──

    private void handleRegister() {
        String username = etUsername.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();
        String pass     = etPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        UserDetailsTable newUser = new UserDetailsTable(username, pass, email);

        api.register(newUser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,
                            "Registered! Please login.", Toast.LENGTH_SHORT).show();
                    // Switch to login mode after successful register
                    isLoginMode = true;
                    updateUI();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Register failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnSubmit.setEnabled(!loading);
    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}