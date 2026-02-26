package com.ringkhang.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models_DTO.QuestionDetails;

import java.util.ArrayList;

public class QuestionsVpAdapter extends RecyclerView.Adapter<QuestionsVpAdapter.MyQuestionViewHolder> {

    private final ArrayList<QuestionDetails> questions;
    private final Context context;
    private final OnAnswerChangedListener listener;

    // Stores selected option per question (-1 = unanswered)
    private final int[] userAnswers;

    public interface OnAnswerChangedListener {
        void onAnswerChanged();
    }

    public QuestionsVpAdapter(ArrayList<QuestionDetails> questions, Context context, OnAnswerChangedListener listener) {
        this.questions   = questions;
        this.context     = context;
        this.listener    = listener;
        this.userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;
    }

    @NonNull
    @Override
    public MyQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_quiz_row_vh, parent, false);
        return new MyQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuestionViewHolder holder, int position) {
        QuestionDetails q = questions.get(position);

        holder.question.setText("Q" + (position + 1) + ": " + q.getQuestion());
        holder.opA.setText("A: " + q.getOptionA());
        holder.opB.setText("B: " + q.getOptionB());
        holder.opC.setText("C: " + q.getOptionC());
        holder.opD.setText("D: " + q.getOptionD());

        // ── Restore saved answer without triggering the listener ──
        holder.radioGroup.setOnCheckedChangeListener(null);
        holder.radioGroup.clearCheck();

        int saved = userAnswers[position];
        if      (saved == 0) holder.opA.setChecked(true);
        else if (saved == 1) holder.opB.setChecked(true);
        else if (saved == 2) holder.opC.setChecked(true);
        else if (saved == 3) holder.opD.setChecked(true);

        // ── Set listener AFTER restoring state ──
        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if      (checkedId == R.id.optionA) userAnswers[position] = 0;
            else if (checkedId == R.id.optionB) userAnswers[position] = 1;
            else if (checkedId == R.id.optionC) userAnswers[position] = 2;
            else if (checkedId == R.id.optionD) userAnswers[position] = 3;

            if (listener != null) listener.onAnswerChanged();
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    /** Returns the userAnswers array — call this from TestActivity on submit */
    public int[] getUserAnswers() {
        return userAnswers;
    }

    /** Returns how many questions have been answered */
    public int getAnsweredCount() {
        int count = 0;
        for (int a : userAnswers) if (a != -1) count++;
        return count;
    }

    // ── ViewHolder ────────────────────────────────────────────

    public static class MyQuestionViewHolder extends RecyclerView.ViewHolder {
        TextView    question;
        RadioGroup  radioGroup;
        RadioButton opA, opB, opC, opD;

        public MyQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            question   = itemView.findViewById(R.id.question);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            opA        = itemView.findViewById(R.id.optionA);
            opB        = itemView.findViewById(R.id.optionB);
            opC        = itemView.findViewById(R.id.optionC);
            opD        = itemView.findViewById(R.id.optionD);
        }
    }
}