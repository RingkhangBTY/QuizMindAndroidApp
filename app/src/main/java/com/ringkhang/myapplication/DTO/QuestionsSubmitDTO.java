package com.ringkhang.myapplication.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuestionsSubmitDTO implements Serializable {
    @SerializedName("question")
    private String question;
    @SerializedName("optionA")
    private String optionA;
    @SerializedName("optionB")
    private String optionB;
    @SerializedName("optionC")
    private String optionC;
    @SerializedName("optionD")
    private String optionD;
    @SerializedName("answer")
    private String answer;
    @SerializedName("explanation")
    private String explanation;
    @SerializedName("userAnswer")
    private String userAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
