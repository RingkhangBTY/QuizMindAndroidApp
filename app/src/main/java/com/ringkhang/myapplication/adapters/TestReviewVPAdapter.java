package com.ringkhang.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.DTO.QuestionsSubmitDTO;
import com.ringkhang.myapplication.R;

import java.util.ArrayList;

public class TestReviewVPAdapter extends RecyclerView.Adapter<TestReviewVPAdapter.MyTestReviewViewHolder> {

    private static final String GREEN = "#4CAF50";
    private static final String RED   = "#F44336";
    private static final String GRAY  = "#333333";

    private final ArrayList<QuestionsSubmitDTO> questions;
    private final Context context;

    public TestReviewVPAdapter(Context context, ArrayList<QuestionsSubmitDTO> questions) {
        this.context   = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public MyTestReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_review_test_row, parent, false);
        return new MyTestReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTestReviewViewHolder holder, int position) {
        QuestionsSubmitDTO q = questions.get(position);

        String correctAnswer = q.getAnswer();
        String userAnswer    = q.getUserAnswer(); // String directly from backend

        // ── Question ──────────────────────────────────────────
        holder.tvQuestion.setText("Q" + (position + 1) + ": " + q.getQuestion());

        // ── Options ───────────────────────────────────────────
        String[] opts = { q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD() };
        TextView[] views = { holder.tvOpA, holder.tvOpB, holder.tvOpC, holder.tvOpD };
        String[] labels  = { "A: ", "B: ", "C: ", "D: " };

        // Reset all to gray first
        for (int i = 0; i < views.length; i++) {
            views[i].setText(labels[i] + opts[i]);
            views[i].setTextColor(Color.parseColor(GRAY));
        }

        // Highlight correct answer → green
        for (int i = 0; i < opts.length; i++) {
            if (opts[i].equals(correctAnswer)) {
                views[i].setTextColor(Color.parseColor(GREEN));
                break;
            }
        }

        // ── User answer ───────────────────────────────────────
        boolean skipped = userAnswer == null
                || userAnswer.isEmpty()
                || userAnswer.equalsIgnoreCase("skipped");

        if (skipped) {
            // No option highlighted red — correct stays green, rest gray
            holder.tvCorrectAns.setText("Correct Answer: " + correctAnswer);
            holder.tvCorrectAns.setTextColor(Color.parseColor(GREEN));
        } else {
            boolean correct = userAnswer.equals(correctAnswer);

            if (!correct) {
                // Highlight user's wrong choice → red
                for (int i = 0; i < opts.length; i++) {
                    if (opts[i].equals(userAnswer)) {
                        views[i].setTextColor(Color.parseColor(RED));
                        break;
                    }
                }
            }

            holder.tvCorrectAns.setText("Correct Answer: " + correctAnswer);
            holder.tvCorrectAns.setTextColor(Color.parseColor(GREEN));
        }

        // ── Explanation ───────────────────────────────────────
        holder.tvExplanation.setText("Explanation: " + q.getExplanation());
    }

    @Override
    public int getItemCount() { return questions.size(); }

    // ── ViewHolder ────────────────────────────────────────────

    public static class MyTestReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvOpA, tvOpB, tvOpC, tvOpD;
        TextView tvCorrectAns, tvExplanation;

        public MyTestReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion    = itemView.findViewById(R.id.textView);
            tvOpA         = itemView.findViewById(R.id.textView3);
            tvOpB         = itemView.findViewById(R.id.textView4);
            tvOpC         = itemView.findViewById(R.id.textView5);
            tvOpD         = itemView.findViewById(R.id.textView6);
            tvCorrectAns  = itemView.findViewById(R.id.textView8);
            tvExplanation = itemView.findViewById(R.id.textView9);
        }
    }
}