package com.ringkhang.myapplication.models_DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInForQuizTest implements Serializable {
    @SerializedName("programmingLanguage_Subject")
    private String programmingLanguage_Subject;
    @SerializedName("shortDes_Topic_Concepts")
    private String shortDes_Topic_Concepts;
    @SerializedName("level")
    private String level;
    @SerializedName("noOfQ")
    private int noOfQ;

    public String getProgrammingLanguage_Subject() {
        return programmingLanguage_Subject;
    }

    public String getShortDes_Topic_Concepts() {
        return shortDes_Topic_Concepts;
    }

    public String getLevel() {
        return level;
    }

    public int getNoOfQ() {
        return noOfQ;
    }

    public void setProgrammingLanguage_Subject(String programmingLanguage_Subject) {
        this.programmingLanguage_Subject = programmingLanguage_Subject;
    }

    public void setShortDes_Topic_Concepts(String shortDes_Topic_Concepts) {
        this.shortDes_Topic_Concepts = shortDes_Topic_Concepts;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setNoOfQ(int noOfQ) {
        this.noOfQ = noOfQ;
    }
}
