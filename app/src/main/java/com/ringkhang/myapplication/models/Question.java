package com.ringkhang.myapplication.models;

public class Question {

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String explanation;

    public Question(String question, String optionA, String optionB,
                    String optionC, String optionD,
                    String answer, String explanation) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.explanation = explanation;
    }

    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getAnswer() { return answer; }
    public String getExplanation() { return explanation; }
}
