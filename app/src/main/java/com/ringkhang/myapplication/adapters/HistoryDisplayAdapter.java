package com.ringkhang.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;

import java.util.ArrayList;

public class HistoryDisplayAdapter extends RecyclerView.Adapter<HistoryDisplayAdapter.HistoryViewHolder> {

    public interface OnItemActionListener {
        void onAction(ScoreHistoryDisplay item);
    }

    private final ArrayList<ScoreHistoryDisplay> data;
    private final Context context;
    private final OnItemActionListener onReviewClick;
//    private final OnItemActionListener onTestAgainClick;

    public HistoryDisplayAdapter(ArrayList<ScoreHistoryDisplay> data,
                                 Context context,
                                 OnItemActionListener onReviewClick) {
        this.data            = data;
        this.context         = context;
        this.onReviewClick   = onReviewClick;
//        this.onTestAgainClick = onTestAgainClick;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_history_row, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        ScoreHistoryDisplay item = data.get(position);

        holder.hisTopic.setText(item.getTopic_sub());
        holder.hisDateTime.setText(item.getTime_stamp());
        holder.hisDescription.setText(item.getShort_des());
        holder.histTotalQuestion.setText(String.valueOf(item.getTotal_question()));
        holder.hisCorrectAns.setText(String.valueOf(item.getCorrect_ans()));
        holder.hisTestScore.setText(item.getTest_score() + "%");
        holder.histQLevel.setText(item.getLevel());
        holder.hisTestFeedback.setText(item.getFeedback());

        // ✅ Buttons are in the item layout — wire them here
        holder.btnReview.setOnClickListener(v -> {
            if (onReviewClick != null) onReviewClick.onAction(item);
        });
//
//        holder.btnTestAgain.setOnClickListener(v -> {
//            if (onTestAgainClick != null) onTestAgainClick.onAction(item);
//        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView hisTopic, hisDateTime, hisDescription;
        TextView histTotalQuestion, hisCorrectAns, hisTestScore, histQLevel, hisTestFeedback;
        Button btnReview, btnTestAgain;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            hisTopic          = itemView.findViewById(R.id.hisTopic);
            hisDateTime       = itemView.findViewById(R.id.hisDateTime);
            hisDescription    = itemView.findViewById(R.id.hisDescription);
            histTotalQuestion = itemView.findViewById(R.id.histTotalQuestion);
            hisCorrectAns     = itemView.findViewById(R.id.hisCorrectAns);
            hisTestScore      = itemView.findViewById(R.id.hisTestScore);
            histQLevel        = itemView.findViewById(R.id.histQLevel);
            hisTestFeedback   = itemView.findViewById(R.id.hisTestFeedback);
            btnReview         = itemView.findViewById(R.id.histReview);
//            btnTestAgain      = itemView.findViewById(R.id.hisReTest);
        }
    }
}