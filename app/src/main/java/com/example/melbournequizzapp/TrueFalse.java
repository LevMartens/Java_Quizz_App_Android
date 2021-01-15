package com.example.melbournequizzapp;

public class TrueFalse {


    private String mQuestionID;
    private boolean mAnswer;

    public TrueFalse(String questionResourceID, boolean trueOrFalse) {

        mQuestionID = questionResourceID;
        mAnswer = trueOrFalse;

    }

    public String getmQuestionID() {
        return mQuestionID;
    }

    public void setmQuestionID(String mQuestionID) {
        this.mQuestionID = mQuestionID;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}


