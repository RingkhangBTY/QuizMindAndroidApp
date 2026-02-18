package com.ringkhang.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models.Question;

import java.util.ArrayList;

public class TestReviewVPAdapter extends RecyclerView.Adapter<TestReviewVPAdapter.MyTestReviewViewHolder> {

    private final ArrayList<Question> questions;
    private final int[] userAnswers;   // 0–3 = selected option, -1 = unanswered
    private final Context context;

    public TestReviewVPAdapter(Context context, ArrayList<Question> questions, int[] userAnswers) {
        this.context     = context;
        this.questions   = questions;
        this.userAnswers = userAnswers;
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
        Question q          = questions.get(position);
        int      userChoice = userAnswers[position];   // -1 if skipped

        // Question
        holder.tvQuestion.setText("Q" + (position + 1) + ": " + q.getQuestion());

        // Options — store in array for easy looping
        String[] opts = {
                "A: " + q.getOptionA(),
                "B: " + q.getOptionB(),
                "C: " + q.getOptionC(),
                "D: " + q.getOptionD()
        };
        holder.tvOpA.setText(opts[0]);
        holder.tvOpB.setText(opts[1]);
        holder.tvOpC.setText(opts[2]);
        holder.tvOpD.setText(opts[3]);

        // Reset colors first
        holder.tvOpA.setTextColor(Color.parseColor("#333333"));
        holder.tvOpB.setTextColor(Color.parseColor("#333333"));
        holder.tvOpC.setTextColor(Color.parseColor("#333333"));
        holder.tvOpD.setTextColor(Color.parseColor("#333333"));

        // Figure out which index is the correct answer
        int correctIndex = getCorrectIndex(q);

        // Highlight correct answer green
        highlightOption(holder, correctIndex, Color.parseColor("#4CAF50"));

        // Highlight user's wrong answer red (if they got it wrong or skipped)
        if (userChoice == -1) {
            holder.tvYourAns.setText("Your Answer: Skipped");
            holder.tvYourAns.setTextColor(Color.parseColor("#F44336"));
        } else {
            String[] rawOpts = {q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD()};
            holder.tvYourAns.setText("Your Answer: " + rawOpts[userChoice]);
            if (userChoice == correctIndex) {
                holder.tvYourAns.setTextColor(Color.parseColor("#4CAF50"));
            } else {
                highlightOption(holder, userChoice, Color.parseColor("#F44336"));
                holder.tvYourAns.setTextColor(Color.parseColor("#F44336"));
            }
        }

        // Correct answer label
        String[] rawOpts = {q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD()};
        holder.tvCorrectAns.setText("Correct Answer: " + rawOpts[correctIndex]);
        holder.tvCorrectAns.setTextColor(Color.parseColor("#4CAF50"));

        // Explanation
        holder.tvExplanation.setText("Explanation: " + q.getExplanation());
    }

    /** Returns the 0-based index of the correct option by matching the correct answer string */
    private int getCorrectIndex(Question q) {
        String correct = q.getAnswer();
        if (correct.equals(q.getOptionA())) return 0;
        if (correct.equals(q.getOptionB())) return 1;
        if (correct.equals(q.getOptionC())) return 2;
        return 3;
    }

    private void highlightOption(MyTestReviewViewHolder holder, int index, int color) {
        switch (index) {
            case 0: holder.tvOpA.setTextColor(color); break;
            case 1: holder.tvOpB.setTextColor(color); break;
            case 2: holder.tvOpC.setTextColor(color); break;
            case 3: holder.tvOpD.setTextColor(color); break;
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    // ── ViewHolder ────────────────────────────────────────────

    public static class MyTestReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvOpA, tvOpB, tvOpC, tvOpD;
        TextView tvYourAns, tvCorrectAns, tvExplanation;

        public MyTestReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion   = itemView.findViewById(R.id.textView);
            tvOpA        = itemView.findViewById(R.id.textView3);
            tvOpB        = itemView.findViewById(R.id.textView4);
            tvOpC        = itemView.findViewById(R.id.textView5);
            tvOpD        = itemView.findViewById(R.id.textView6);
            tvYourAns    = itemView.findViewById(R.id.textView7);
            tvCorrectAns = itemView.findViewById(R.id.textView8);
            tvExplanation= itemView.findViewById(R.id.textView9);
        }
    }
}