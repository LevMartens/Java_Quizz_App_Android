package com.example.melbournequizzapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controller.FireBase;
import com.example.Model.ResultHandler;
import com.example.Model.MultipleChoice;
import com.example.Model.TrueFalse;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {


     Button mTrueButton;
     Button mFalseButton;
     Button mAButton;
     Button mBButton;
     Button mCButton;
     TextView mQuestionTextView;
     String mQuestion;
     TextView mScoreTextView;
     ProgressBar mProgressbar;
     List<TrueFalse> mTrueFalseQuestionBank;
     List<MultipleChoice> mMultipleChoiceQuestionBank;
     int PROGRESS_BAR_INCREMENT;
     int TRUE_FALSE_INDEX;
     int MULTIPLE_CHOICE_INDEX;
     int TOTAL_INDEX;
     int mScore;
     int TOTAL_QUESTIONS;
     boolean onTrueFalseQuestion;
     boolean outOfTrueFalseQuestions;
     boolean outOfMultipleChoiceQuestions;
     boolean onFirstQuestion;
     boolean mMultipleChoiceQuestionBankReady;
     boolean mTrueFalseQuestionBankReady;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getQuestions();
        setupView();




    }

    private void getQuestions() {
        FireBase firebase = new FireBase();
        mMultipleChoiceQuestionBank = firebase.getMultipleChoiceQuestions(new ResultHandler() {
            @Override
            public void onSuccess() {
                mMultipleChoiceQuestionBank = firebase.mMultipleChoiceQuestionBank;
                mMultipleChoiceQuestionBankReady = true;
                if(mMultipleChoiceQuestionBankReady == true) {


                displayScore();
                }
            }
        });
        mTrueFalseQuestionBank = firebase.getTrueFalseQuestions(new ResultHandler() {
            @Override
            public void onSuccess() {
                mTrueFalseQuestionBank = firebase.mTrueFalseQuestionBank;
                mTrueFalseQuestionBankReady = true;
                if(mMultipleChoiceQuestionBankReady == true) {
                    displayScore();
                }
                displayFirstQuestion();
            }
        });  //The result handler will switch the "loading..." text for the first question.



    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mAButton = (Button) findViewById(R.id.buttonA);
        mBButton = (Button) findViewById(R.id.buttonB);
        mCButton = (Button) findViewById(R.id.buttonC);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        TOTAL_QUESTIONS = mTrueFalseQuestionBank.size() + mMultipleChoiceQuestionBank.size();
        mTrueFalseQuestionBank = new ArrayList<TrueFalse>();
        mMultipleChoiceQuestionBank = new ArrayList<MultipleChoice>();


        putWidgetsInLoadingPosition();
        setupWidgetsFunctions();



    }

    private void putWidgetsInLoadingPosition() {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)mQuestionTextView.getLayoutParams();
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.topMargin = 350; // in PX
        mQuestionTextView.setLayoutParams(p);
        mAButton.setVisibility(View.GONE);
        mBButton.setVisibility(View.GONE);
        mCButton.setVisibility(View.GONE);
        mQuestionTextView.setText("   loading...");
        mQuestionTextView.setGravity(Gravity.CENTER);
        mTrueButton.setVisibility(View.GONE);
        mFalseButton.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.GONE);
        mScoreTextView.setVisibility(View.INVISIBLE);

    }

    private void setupWidgetsFunctions() {

        mTrueButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                trueButtonTapped();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               falseButtonTapped();
            }
        });

        mAButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                answerAButtonTapped();
            }
        });

        mBButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                answerBButtonTapped();
            }
        });

        mCButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                answerCButtonTapped();
            }
        });
    }

    private void trueButtonTapped() {
        onTrueFalseQuestion = false;
        checkTrueFalseAnswer(true);
        updateQuestion();
    }

    private void falseButtonTapped(){
        onTrueFalseQuestion = false;
        checkTrueFalseAnswer(false);
        updateQuestion();
    }

    private void answerAButtonTapped() {
        onTrueFalseQuestion = true;
        checkMultipleChoiceAnswer("A");
        updateQuestion();
    }

    private void answerBButtonTapped() {
        onTrueFalseQuestion = true;
        checkMultipleChoiceAnswer("B");
        updateQuestion();
        System.out.println("B TAPPED  ");
    }

    private void answerCButtonTapped() {
        onTrueFalseQuestion = true;
        checkMultipleChoiceAnswer("C");
        updateQuestion();
    }

    private void displayFirstQuestion() {
        onFirstQuestion = true;
        mQuestion = mTrueFalseQuestionBank.get(TRUE_FALSE_INDEX).getQuestion();
        System.out.println("TRUE_FALSE_INDEX  "+TRUE_FALSE_INDEX);
        mQuestionTextView.setText(mQuestion);
        mTrueButton.setVisibility(View.VISIBLE);
        mFalseButton.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.VISIBLE);
        onTrueFalseQuestion = true;
        outOfTrueFalseQuestions = false;
        outOfMultipleChoiceQuestions = false;


    }

    private void displayScore() {
        mScoreTextView.setVisibility(View.VISIBLE);
        TOTAL_QUESTIONS = mTrueFalseQuestionBank.size() + mMultipleChoiceQuestionBank.size();
        mScoreTextView.setText("Score " + mScore +"/"+ TOTAL_QUESTIONS);
        PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / TOTAL_QUESTIONS);
    }

    private void updateQuestion(){

        if(outOfMultipleChoiceQuestions == true && outOfTrueFalseQuestions == true) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(mScore == TOTAL_QUESTIONS ? "Perfect Score!" : mScore > (TOTAL_QUESTIONS/2) ? "Not Bad" : "Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points out of "+TOTAL_QUESTIONS+ " questions.");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();} else {


            if (onTrueFalseQuestion == true) {
                System.out.println("TF INDEX  "+TRUE_FALSE_INDEX);
                TRUE_FALSE_INDEX = ( TRUE_FALSE_INDEX +1 % mTrueFalseQuestionBank.size());
                if (TRUE_FALSE_INDEX == mTrueFalseQuestionBank.size()) {TRUE_FALSE_INDEX = 0;}
                System.out.println("TF INDEX AFTER SET "+TRUE_FALSE_INDEX);
                if(TRUE_FALSE_INDEX == 0){
                    outOfTrueFalseQuestions = true;
                    onTrueFalseQuestion = false;
                    updateQuestion();
                } else {

                    displayNextTrueFalseQuestion(); }

            }


            if (onTrueFalseQuestion == false) {
                MULTIPLE_CHOICE_INDEX = ( MULTIPLE_CHOICE_INDEX +1 % mMultipleChoiceQuestionBank.size());
                if (MULTIPLE_CHOICE_INDEX == mMultipleChoiceQuestionBank.size()) {MULTIPLE_CHOICE_INDEX = 0;}
                if(onFirstQuestion == true) {MULTIPLE_CHOICE_INDEX = 0;}
                if(MULTIPLE_CHOICE_INDEX == 0){

                    if(onFirstQuestion == false){
                        outOfMultipleChoiceQuestions = true;
                        onTrueFalseQuestion = true;
                        updateQuestion();
                    } else {
                        onFirstQuestion = false;
                        displayNextMultipleChoiceQuestion();}
                } else {

                    displayNextMultipleChoiceQuestion(); }

            }


        }



    }

    private void displayNextTrueFalseQuestion() {
        mAButton.setVisibility(View.GONE);
        mBButton.setVisibility(View.GONE);
        mCButton.setVisibility(View.GONE);
        mTrueButton.setVisibility(View.VISIBLE);
        mFalseButton.setVisibility(View.VISIBLE);
        mQuestion = mTrueFalseQuestionBank.get(TRUE_FALSE_INDEX).getQuestion();
        mQuestionTextView.setText(mQuestion);

    }

    private void displayNextMultipleChoiceQuestion() {
        mTrueButton.setVisibility(View.GONE);
        mFalseButton.setVisibility(View.GONE);
        mAButton.setVisibility(View.VISIBLE);
        mBButton.setVisibility(View.VISIBLE);
        mCButton.setVisibility(View.VISIBLE);
        mQuestion = mMultipleChoiceQuestionBank.get(MULTIPLE_CHOICE_INDEX).getQuestion();
        mQuestionTextView.setText(mQuestion);
        mAButton.setText("A: " +mMultipleChoiceQuestionBank.get(MULTIPLE_CHOICE_INDEX).getOptionADescription());
        mBButton.setText("B: " +mMultipleChoiceQuestionBank.get(MULTIPLE_CHOICE_INDEX).getOptionBDescription());
        mCButton.setText("C: " +mMultipleChoiceQuestionBank.get(MULTIPLE_CHOICE_INDEX).getOptionCDescription());

    }

    private void checkTrueFalseAnswer(boolean userSelection) {
        PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / TOTAL_QUESTIONS);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        boolean correctAnswer = mTrueFalseQuestionBank.get(TRUE_FALSE_INDEX).ismAnswer();


        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
             mScore = mScore +1;
        }else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
        TOTAL_QUESTIONS = mTrueFalseQuestionBank.size() + mMultipleChoiceQuestionBank.size();
        mScoreTextView.setText("Score " + mScore +"/"+ TOTAL_QUESTIONS);
    }

    private void checkMultipleChoiceAnswer(String userSelection) {
        String correctAnswer = mMultipleChoiceQuestionBank.get(MULTIPLE_CHOICE_INDEX).isAnswer();
        PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / TOTAL_QUESTIONS);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);

        if(userSelection.equals(correctAnswer)) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore = mScore +1;
        }else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
        TOTAL_QUESTIONS = mTrueFalseQuestionBank.size() + mMultipleChoiceQuestionBank.size();
        mScoreTextView.setText("Score " + mScore +"/"+ TOTAL_QUESTIONS);

    }




}




