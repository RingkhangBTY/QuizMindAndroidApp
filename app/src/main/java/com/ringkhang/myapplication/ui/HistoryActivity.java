package com.ringkhang.myapplication.ui;

import android.os.Bundle;
import android.os.LimitExceededException;
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
import com.ringkhang.myapplication.DTO.HistoryDTO;
import com.ringkhang.myapplication.data.HistoryUiDatasource;
import com.ringkhang.myapplication.data.MyDataResponseCallBack;
import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryActivity extends AppCompatActivity implements InitialUISetUp {
    private RecyclerView hisRecyclerView;

    private HistoryUiDatasource historyUiDatasource;
    private HistoryDisplayAdapter adapter;

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
        setUpViews();

        historyUiDatasource = new HistoryUiDatasource(HistoryActivity.this);
        getAllTestScoreHistory();

    }

    private void getAllTestScoreHistory() {

        MyDataResponseCallBack<ArrayList<ScoreHistoryDisplay>> callBack = new MyDataResponseCallBack<ArrayList<ScoreHistoryDisplay>>() {
            @Override
            public void onSuccess(ArrayList<ScoreHistoryDisplay> data) {
                setTestScoreHistory(data);
            }

            @Override
            public void onError(int errorCode, String massage) {
                if (errorCode == 401){
                    Toast.makeText(HistoryActivity.this,
                            "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(HistoryActivity.this,
                            massage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.getLogger(HistoryActivity.class.getName())
                        .log(
                                Level.SEVERE
                                ,t.getCause().toString()
                        );
            }
        };

        historyUiDatasource.getAllTestScoreHistory(callBack);
    }

    private void setTestScoreHistory(ArrayList<ScoreHistoryDisplay> data) {

        adapter = new HistoryDisplayAdapter(data,HistoryActivity.this);

        hisRecyclerView.setAdapter(adapter);
        hisRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void setUpViews() {
        hisRecyclerView = findViewById(R.id.historyDisplayRV);
    }

    @Override
    public void setInitialListeners() {

    }
}