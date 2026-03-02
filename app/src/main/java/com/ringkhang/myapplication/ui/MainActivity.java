package com.ringkhang.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.ringkhang.myapplication.data.HistoryUiDatasource;
import com.ringkhang.myapplication.data.HomeUiDatasource;
import com.ringkhang.myapplication.data.MyDataResponseCallBack;
import com.ringkhang.myapplication.models_DTO.InitialAppPayload;
import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;
import com.ringkhang.myapplication.models_DTO.TestReview;
import com.ringkhang.myapplication.utils.SessionManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private Button testStartBtn, historyDisBtn, histReview;
    private ImageButton logoutBtn;
    private TextView welcomeText, avgScore, totalQuiz, levelEasy, levelMedium, levelHard, levelMaxPro,
            hisTopic, hisDescription, hisDateTime, histTotalQuestion, hisCorrectAns, hisTestScore,
            histQLevel, hisTestFeedback;

    private HomeUiDatasource homeUiDatasource ;
    private HistoryUiDatasource historyUiDatasource;
    private InitialAppPayload initialAppPayload;

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

        homeUiDatasource = new HomeUiDatasource(MainActivity.this);
        historyUiDatasource = new HistoryUiDatasource(MainActivity.this);

        setUpViews();
        setInitialListeners();
        getInitialAppData();

    }

    private void getInitialAppData(){
        homeUiDatasource.getInitialMainData(false, new MyDataResponseCallBack<InitialAppPayload>() {
            @Override
            public void onSuccess(InitialAppPayload data) {
                initialAppPayload = data;
                setInitialMainData();
            }

            @Override
            public void onError(int errorCode, String massage) {
                if (errorCode == 401){
                    Toast.makeText(MainActivity.this,"Fails to authenticate user",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,massage,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.getLogger(MainActivity.class.getName()).log(
                        Level.SEVERE,t.getCause().toString()
                );
            }
        });
    }

    private void setInitialMainData() {

        welcomeText.setText("Welcome to Quiz Mind\nMr/Ms " + initialAppPayload.getUserDetailsDTO().getUsername());

        totalQuiz.setText("Total quiz: " + initialAppPayload.getTotalQuizAttempts());
        avgScore.setText("Avg Score: " + initialAppPayload.getAvgScore());
        levelEasy.setText("Easy: " + initialAppPayload.getEasyQuizCount());
        levelMedium.setText("Medium: " + initialAppPayload.getMediumQuizCount());
        levelHard.setText("Hard: " + initialAppPayload.getHardQuizCount());
        levelMaxPro.setText("Pro+: " + initialAppPayload.getHardPlusQuizCount());

        ScoreHistoryDisplay historyDisplay = initialAppPayload.getScoreHistoryDisplay();
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


    private void getTestReviewData(){
        historyUiDatasource.getTestReviewData(new MyDataResponseCallBack<TestReview>() {
            @Override
            public void onSuccess(TestReview data) {
                Intent intent = new Intent(MainActivity.this,TestReviewActivity.class);
                intent.putExtra("testReview",data);
                startActivity(intent);

                finish();
            }

            @Override
            public void onError(int errorCode, String massage) {
                Toast.makeText(MainActivity.this,
                        "Error: "+errorCode+" "+massage,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this,
                        t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }, initialAppPayload.getScoreHistoryDisplay().getQuizHistoryId());
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

        histReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTestReviewData();
            }
        });
    }

    // ── Views ─────────────────────────────────────────────────

    private void setUpViews() {
        testStartBtn      = findViewById(R.id.testInitiateBtn);
        historyDisBtn     = findViewById(R.id.historyBtn);
        logoutBtn         = findViewById(R.id.logoutBtn);
        histReview        = findViewById(R.id.histReview);

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