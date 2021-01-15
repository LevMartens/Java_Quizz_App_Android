package com.example.melbournequizzapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controller.FireBase;

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
     List<TrueFalse> mQuestionBank;
     int PROGRESS_BAR_INCREMENT;
     int CURRENT_QUESTION;
     int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getQuestions();
        setupView();



    }

    private void getQuestions() {
        FireBase firebase = new FireBase();
        mQuestionBank = firebase.getQuestions();
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mAButton = (Button) findViewById(R.id.buttonA);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.size());
        mQuestionBank.add(new TrueFalse("firstQuestion",true));

        putWidgetsInStartingPosition();
        setupWidgets();
    }

    private void putWidgetsInStartingPosition() {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)mQuestionTextView.getLayoutParams();
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.topMargin = 40; // in PX
        mQuestionTextView.setLayoutParams(p);
        mAButton.setVisibility(View.GONE);
    }

    private void setupWidgets() {
        mQuestion = mQuestionBank.get(CURRENT_QUESTION).getmQuestionID();

        mQuestionTextView.setText(mQuestion);

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
    }

    private void trueButtonTapped() {
        mAButton.setVisibility(View.VISIBLE);
        mAButton.setText("A: Dinosaurs are dicks");
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)mQuestionTextView.getLayoutParams();
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.topMargin = 700; // in PX
        mQuestionTextView.setLayoutParams(p);
        checkAnswer(true);
        updateQuestion();
    }

    private void falseButtonTapped(){
        checkAnswer(false);
        updateQuestion();
    }

    private void updateQuestion(){
        CURRENT_QUESTION = ( CURRENT_QUESTION +1 % mQuestionBank.size());

        if(CURRENT_QUESTION == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored" + mScore + "points");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank.get(CURRENT_QUESTION).getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + mQuestionBank.size());
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank.get(CURRENT_QUESTION).ismAnswer();

        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
             mScore = mScore +1;
        }else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}




