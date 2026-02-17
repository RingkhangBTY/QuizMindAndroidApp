package com.ringkhang.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.models.Question;

import java.util.ArrayList;

public class QuestionsVpAdapter extends RecyclerView.Adapter<QuestionsVpAdapter.MyQuestionViewHolder> {
    ArrayList<Question> questions;
    Context context;

    public QuestionsVpAdapter(ArrayList<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public MyQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_quiz_row_vh,parent,false);
        return new MyQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuestionViewHolder holder, int position) {
        Question q = questions.get(position);

        holder.question.setText("Q"+(position+1)+": "+q.getQuestion());
        holder.opA.setText("A: "+q.getOptionA());
        holder.opB.setText("B: "+q.getOptionB());
        holder.opC.setText("C: "+q.getOptionC());
        holder.opD.setText("D: "+q.getOptionD());


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class MyQuestionViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        RadioButton opA, opB, opC, opD;
        public MyQuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            opA = itemView.findViewById(R.id.optionA);
            opB = itemView.findViewById(R.id.optionB);
            opC = itemView.findViewById(R.id.optionC);
            opD = itemView.findViewById(R.id.optionD);
        }
    }
}
