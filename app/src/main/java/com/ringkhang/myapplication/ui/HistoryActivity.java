package com.ringkhang.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.HistoryDisplayAdapter;
import com.ringkhang.myapplication.data.HistoryUiDatasource;
import com.ringkhang.myapplication.data.MyDataResponseCallBack;
import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;
import com.ringkhang.myapplication.models_DTO.TestReview;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView hisRecyclerView;
    private HistoryUiDatasource historyUiDatasource;
    private TestReview testReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.historyPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hisRecyclerView     = findViewById(R.id.historyDisplayRV);
        historyUiDatasource = new HistoryUiDatasource(this);

        loadHistory();
    }

    // ── API ───────────────────────────────────────────────────

    private void loadHistory() {
        historyUiDatasource.getAllTestScoreHistory(new MyDataResponseCallBack<ArrayList<ScoreHistoryDisplay>>() {
            @Override
            public void onSuccess(ArrayList<ScoreHistoryDisplay> data) {
                showHistory(data);
            }

            @Override
            public void onError(int errorCode, String message) {
                String msg = errorCode == 401
                        ? "Session expired. Please login again."
                        : message;
                Toast.makeText(HistoryActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.getLogger(HistoryActivity.class.getName())
                        .log(Level.SEVERE, "History load failed: " + t.getMessage());
                Toast.makeText(HistoryActivity.this,
                        "Network error. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ── Bind ──────────────────────────────────────────────────

    private void showHistory(ArrayList<ScoreHistoryDisplay> data) {
        HistoryDisplayAdapter adapter = new HistoryDisplayAdapter(
                data,
                this,

                // Review button — open review screen for this history entry
                item -> {
                    getTestReviewData(item.getQuizHistoryId());
//                    Toast.makeText(this,
//                            "Review: " + item.getQuizHistoryId(), Toast.LENGTH_SHORT).show();
                }
        );

        hisRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hisRecyclerView.setAdapter(adapter);
    }

    private void getTestReviewData(int quizHistoryId) {
        historyUiDatasource.getTestReviewData(new MyDataResponseCallBack<TestReview>() {
            @Override
            public void onSuccess(TestReview data) {
                testReview = data;

                Intent intent = new Intent(HistoryActivity.this,TestReviewActivity.class);
                intent.putExtra("testReview",data);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int errorCode, String massage) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        },quizHistoryId);
    }
}