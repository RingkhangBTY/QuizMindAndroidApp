package com.ringkhang.myapplication.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.HistoryDisplayAdapter;
import com.ringkhang.myapplication.models_DTO.HistoryDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView hisRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hisRecyclerView = findViewById(R.id.historyDisplayRV);
        HistoryDisplayAdapter adapter = new HistoryDisplayAdapter(getDummyHistoryList(),HistoryActivity.this);
        hisRecyclerView.setAdapter(adapter);
        hisRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<HistoryDTO> getDummyHistoryList() {

        return new ArrayList<>(Arrays.asList(

                new HistoryDTO(
                        "john_doe",
                        20,
                        18,
                        90,
                        "Excellent performance! Keep it up.",
                        "Mathematics - Algebra",
                        "Easy",
                        "Basic algebraic equations test",
                        LocalDateTime.of(2026, 2, 20, 10, 15)
                ),

                new HistoryDTO(
                        "sarah_lee",
                        25,
                        20,
                        80,
                        "Good job. Review quadratic equations.",
                        "Mathematics - Quadratics",
                        "Medium",
                        "Quadratic equations and roots",
                        LocalDateTime.of(2026, 2, 19, 14, 30)
                ),

                new HistoryDTO(
                        "mike_ross",
                        30,
                        21,
                        70,
                        "Fair attempt. Practice more word problems.",
                        "Physics - Mechanics",
                        "Hard",
                        "Newton's laws and motion problems",
                        LocalDateTime.of(2026, 2, 18, 9, 45)
                ),

                new HistoryDTO(
                        "emma_watson",
                        15,
                        15,
                        100,
                        "Outstanding! Perfect score.",
                        "English - Grammar",
                        "Easy",
                        "Basic grammar and sentence structure",
                        LocalDateTime.of(2026, 2, 17, 16, 10)
                ),

                new HistoryDTO(
                        "alex_smith",
                        40,
                        28,
                        70,
                        "Needs improvement in advanced topics.",
                        "Computer Science - Data Structures",
                        "Hard",
                        "Stacks, queues, and linked lists",
                        LocalDateTime.of(2026, 2, 16, 11, 5)
                )
        ));

    }
}