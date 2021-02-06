package com.melbournequizzapp.Model;

public class TrueFalse {



    private String mQuestion;
    private boolean mAnswer;

    public TrueFalse(String questionString, boolean trueOrFalse) {

        mQuestion = questionString;
        mAnswer = trueOrFalse;

    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setmQuestionID(String mQuestionID) {
        this.mQuestion = mQuestionID;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}


