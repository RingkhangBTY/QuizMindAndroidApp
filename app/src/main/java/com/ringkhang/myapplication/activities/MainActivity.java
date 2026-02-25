package com.ringkhang.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models_DTO.InitialAppPayloadDTO;
import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;
import com.ringkhang.myapplication.network.HomeApiService;
import com.ringkhang.myapplication.network.RetrofitClient;
import com.ringkhang.myapplication.utils.SessionManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button testStartBtn, historyDisBtn;
    private ImageButton logoutBtn;
    private TextView welcomeText, avgScore, totalQuiz, levelEasy, levelMedium, levelHard, levelMaxPro,
            hisTopic, hisDescription, hisDateTime, histTotalQuestion, hisCorrectAns, hisTestScore,
            histQLevel, hisTestFeedback;

    private HomeApiService homeApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpViews();
        setInitialListeners();

        homeApiService = RetrofitClient.getInstance(MainActivity.this).create(HomeApiService.class);
        getInitialMainData(false);
    }

    // ── API ───────────────────────────────────────────────────

    private void getInitialMainData(boolean isRetry) {
        homeApiService.getInitialData().enqueue(new Callback<InitialAppPayloadDTO>() {
            @Override
            public void onResponse(Call<InitialAppPayloadDTO> call, Response<InitialAppPayloadDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setInitialMainData(response.body());
                } else if (response.code() == 401) {
                    Toast.makeText(MainActivity.this,
                            "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    goToLogin();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InitialAppPayloadDTO> call, Throwable t) {
                if (!isRetry) {
                    RetrofitClient.reset(MainActivity.this);
                    Toast.makeText(MainActivity.this, "Reconnecting...", Toast.LENGTH_SHORT).show();
                    getInitialMainData(true);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Failed to fetch user data!!", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(MainActivity.class.getName())
                            .log(Level.SEVERE, "Failed to fetch user data from DB");
                }
            }
        });
    }

    private void setInitialMainData(InitialAppPayloadDTO initialMainData) {
        welcomeText.setText("Welcome to Quiz Mind\nMr/Ms " + initialMainData.getUserDetailsDTO().getUsername());

        totalQuiz.setText("Total quiz: " + initialMainData.getTotalQuizAttempts());
        avgScore.setText("Avg Score: " + initialMainData.getAvgScore());
        levelEasy.setText("Easy: " + initialMainData.getEasyQuizCount());
        levelMedium.setText("Medium: " + initialMainData.getMediumQuizCount());
        levelHard.setText("Hard: " + initialMainData.getHardQuizCount());
        levelMaxPro.setText("Pro+: " + initialMainData.getHardPlusQuizCount());

        ScoreHistoryDisplay historyDisplay = initialMainData.getScoreHistoryDisplay();
        if (historyDisplay != null) {
            hisTopic.setText(historyDisplay.getTopic_sub());
            hisDescription.setText(historyDisplay.getShort_des());
            hisDateTime.setText(historyDisplay.getTime_stamp());
            histTotalQuestion.setText(String.valueOf(historyDisplay.getTotal_question()));
            hisCorrectAns.setText(String.valueOf(historyDisplay.getCorrect_ans()));
            hisTestScore.setText(String.valueOf(historyDisplay.getTest_score()));
            histQLevel.setText(historyDisplay.getLevel());
            hisTestFeedback.setText(historyDisplay.getFeedback());
        }
    }

    // ── Navigation ────────────────────────────────────────────

    private void goToLogin() {
        SessionManager sm = SessionManager.getInstance(MainActivity.this);
        sm.logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // ── Listeners ─────────────────────────────────────────────

    private void setInitialListeners() {
        testStartBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TestFormActivity.class))
        );

        historyDisBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoryActivity.class))
        );

        //Logout — confirm dialog before logging out
        logoutBtn.setOnClickListener(v ->
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Logout", (d, w) -> goToLogin())
                        .setNegativeButton("Cancel", null)
                        .show()
        );
    }

    // ── Views ─────────────────────────────────────────────────

    private void setUpViews() {
        testStartBtn      = findViewById(R.id.testInitiateBtn);
        historyDisBtn     = findViewById(R.id.historyBtn);
        logoutBtn         = findViewById(R.id.logoutBtn);

        welcomeText       = findViewById(R.id.welcomeText);
        totalQuiz         = findViewById(R.id.totalQuiz);
        avgScore          = findViewById(R.id.avgScore);
        levelEasy         = findViewById(R.id.levelEasy);
        levelMedium       = findViewById(R.id.levelMedium);
        levelHard         = findViewById(R.id.levelHard);
        levelMaxPro       = findViewById(R.id.levelMaxPro);

        hisTopic          = findViewById(R.id.hisTopic);
        hisDescription    = findViewById(R.id.hisDescription);
        hisDateTime       = findViewById(R.id.hisDateTime);
        histTotalQuestion = findViewById(R.id.histTotalQuestion);
        hisCorrectAns     = findViewById(R.id.hisCorrectAns);
        hisTestScore      = findViewById(R.id.hisTestScore);
        histQLevel        = findViewById(R.id.histQLevel);
        hisTestFeedback   = findViewById(R.id.hisTestFeedback);
    }
}