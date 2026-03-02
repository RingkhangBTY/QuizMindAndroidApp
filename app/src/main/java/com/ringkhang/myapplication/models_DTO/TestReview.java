package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;
import com.ringkhang.myapplication.DTO.QuestionsSubmitDTO;

import java.io.Serializable;
import java.util.List;

public class TestReview implements Serializable {

    @SerializedName("scoreHistoryDisplay")
    private ScoreHistoryDisplay scoreHistoryDisplay;
    @SerializedName("questions")
    private List<QuestionsSubmitDTO> questionDetails;

    public ScoreHistoryDisplay getScoreHistoryDisplay() {
        return scoreHistoryDisplay;
    }

    public void setScoreHistoryDisplay(ScoreHistoryDisplay scoreHistoryDisplay) {
        this.scoreHistoryDisplay = scoreHistoryDisplay;
    }

    public List<QuestionsSubmitDTO> getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(List<QuestionsSubmitDTO> questionDetails) {
        this.questionDetails = questionDetails;
    }
}
