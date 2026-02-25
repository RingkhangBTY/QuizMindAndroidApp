package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;

public class InitialAppPayloadDTO {

    @SerializedName("userDetailsDTO")
    private UserDetailsDTO userDetailsDTO;

    @SerializedName("scoreHistoryDisplay")
    private ScoreHistoryDisplay scoreHistoryDisplay;

    @SerializedName("totalQuizAttempts")
    private int totalQuizAttempts;

    @SerializedName("avgScore")
    private float avgScore;

    @SerializedName("easyQuizCount")
    private int easyQuizCount;

    @SerializedName("mediumQuizCount")
    private int mediumQuizCount;

    @SerializedName("hardQuizCount")
    private int hardQuizCount;

    @SerializedName("hardPlusQuizCount")
    private int hardPlusQuizCount;

    public UserDetailsDTO getUserDetailsDTO()  { return userDetailsDTO; }

    public ScoreHistoryDisplay getScoreHistoryDisplay() { return scoreHistoryDisplay; }

    public int getTotalQuizAttempts()          { return totalQuizAttempts; }
    public float getAvgScore()                 { return avgScore; }
    public int getEasyQuizCount()              { return easyQuizCount; }
    public int getMediumQuizCount()            { return mediumQuizCount; }
    public int getHardQuizCount()              { return hardQuizCount; }
    public int getHardPlusQuizCount()          { return hardPlusQuizCount; }
}








