package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;
import com.ringkhang.myapplication.DTO.QuestionsSubmitDTO;

import java.io.Serializable;
import java.util.List;

public class SubmitQuizRequest implements Serializable {

    @SerializedName("questions")
    private List<QuestionsSubmitDTO> questionsSubmitDTO;
    @SerializedName("userInput")
    private UserInForQuizTest userInput;

    public List<QuestionsSubmitDTO> getQuestionsSubmitDTO() {
        return questionsSubmitDTO;
    }

    public void setQuestionsSubmitDTO(List<QuestionsSubmitDTO> questionsSubmitDTO) {
        this.questionsSubmitDTO = questionsSubmitDTO;
    }

    public UserInForQuizTest getUserInput() {
        return userInput;
    }

    public void setUserInput(UserInForQuizTest userInput) {
        this.userInput = userInput;
    }
}
