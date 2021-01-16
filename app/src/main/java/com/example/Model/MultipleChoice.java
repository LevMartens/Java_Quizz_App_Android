package com.example.Model;

public class MultipleChoice {

    private String mQuestion;
    private String mAnswer;
    private String mOptionADescription;
    private String mOptionBDescription;
    private String mOptionCDescription;

public MultipleChoice(String mQuestion, String mAnswer, String mOptionADescription, String mOptionBDescription, String mOptionCDescription){
    this.mQuestion = mQuestion;
    this.mAnswer = mAnswer;
    this.mOptionADescription = mOptionADescription;
    this.mOptionBDescription = mOptionBDescription;
    this.mOptionCDescription = mOptionCDescription;
}

    public String getQuestion() {
        return mQuestion;
    }
    public String isAnswer() {return mAnswer;}
}
