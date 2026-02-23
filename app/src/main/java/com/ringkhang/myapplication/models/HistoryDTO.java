package com.ringkhang.myapplication.models;

import java.time.LocalDateTime;

public class HistoryDTO {
    private String username;
    private int total_question;
    private int correct_ans;
    private int test_score;
    private String feedback;
    private String topic_sub;
    private  String level;
    private  String short_des;
    private LocalDateTime time_stamp;

    public HistoryDTO(String username, int total_question, int correct_ans, int test_score, String feedback, String topic_sub, String level, String short_des, LocalDateTime time_stamp) {
        this.username = username;
        this.total_question = total_question;
        this.correct_ans = correct_ans;
        this.test_score = test_score;
        this.feedback = feedback;
        this.topic_sub = topic_sub;
        this.level = level;
        this.short_des = short_des;
        this.time_stamp = time_stamp;
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

    public LocalDateTime getTime_stamp() {
        return time_stamp;
    }
}
