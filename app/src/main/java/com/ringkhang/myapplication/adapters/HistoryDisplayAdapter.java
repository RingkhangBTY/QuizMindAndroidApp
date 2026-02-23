package com.ringkhang.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models.HistoryDTO;

import java.util.ArrayList;

public class HistoryDisplayAdapter extends RecyclerView.Adapter<HistoryDisplayAdapter.MyHistoryDisViewHolder> {

    private ArrayList<HistoryDTO> historyArrayList;
    private Context context;

    public HistoryDisplayAdapter(ArrayList<HistoryDTO> historyArrayList, Context context) {
        this.historyArrayList = historyArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHistoryDisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_history_row,parent,false);
        return new MyHistoryDisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistoryDisViewHolder holder, int position) {
        HistoryDTO history = historyArrayList.get(position);

        holder.topic.setText(history.getTopic_sub());
        holder.description.setText(history.getShort_des());
        holder.feedback.setText(history.getFeedback());
        holder.level.setText(history.getLevel());

        holder.total.setText(String.valueOf(history.getTotal_question()));
        holder.correct.setText(String.valueOf(history.getCorrect_ans()));
        holder.score.setText(String.valueOf(history.getTest_score()));

        holder.dateTime.setText(history.getTime_stamp().toString());
    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }

    public static class MyHistoryDisViewHolder extends RecyclerView.ViewHolder{
        TextView topic,description,dateTime,total,correct,score,level,feedback;
        public MyHistoryDisViewHolder(@NonNull View itemView) {
            super(itemView);

            topic = itemView.findViewById(R.id.hisTopic);
            description = itemView.findViewById(R.id.hisDescription);
            feedback = itemView.findViewById(R.id.hisTestFeedback);
            level = itemView.findViewById(R.id.histQLevel);

            total = itemView.findViewById(R.id.histTotalQuestion);
            score = itemView.findViewById(R.id.hisTestScore);
            correct = itemView.findViewById(R.id.hisCorrectAns);

            dateTime = itemView.findViewById(R.id.hisDateTime);
        }
    }
}
