package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ScoreHistoryDisplay implements Serializable {

    @SerializedName("quizHistoryId")
    private int quizHistoryId;
    @SerializedName("username")
    private String username;
    @SerializedName("total_question")
    private int total_question;

    @SerializedName("correct_ans")
    private int correct_ans;

    @SerializedName("test_score")
    private int test_score;

    @SerializedName("feedback")
    private String feedback;

    @SerializedName("topic_sub")
    private String topic_sub;

    @SerializedName("level")
    private  String level;
    @SerializedName("short_des")
    private  String short_des;
    @SerializedName("time_stamp")
    private String time_stamp;

    public int getQuizHistoryId() {
        return quizHistoryId;
    }

    public String getUsername() {
        return username;
    }

    public int getTotal_question() {
        return total_question;
    }

    public int getCorrect_ans() {
        return correct_ans;
    }

    public int getTest_score() {
        return test_score;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getTopic_sub() {
        return topic_sub;
    }

    public String getLevel() {
        return level;
    }

    public String getShort_des() {
        return short_des;
    }

    public String getTime_stamp() {
        return time_stamp;
    }
}
