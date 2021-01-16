package com.example.melbournequizzapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controller.FireBase;
import com.example.Controller.ResultHandler;
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
     int INDEX;
     int mScore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getQuestions();
        setupView();




    }

    private void getQuestions() {
        FireBase firebase = new FireBase();
        mTrueFalseQuestionBank = firebase.getTrueFalseQuestions(new ResultHandler() {
            @Override
            public void onSuccess() {
                mTrueFalseQuestionBank = firebase.mTrueFalseQuestionBank;
                  mQuestion = mTrueFalseQuestionBank.get(INDEX).getQuestion();
                  mQuestionTextView.setText(mQuestion);
            }
        });  //The result handler will switch the "loading..." text for the first question.
        mMultipleChoiceQuestionBank = firebase.getMultipleChoiceQuestions();


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
        PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mTrueFalseQuestionBank.size());
        mTrueFalseQuestionBank = new ArrayList<TrueFalse>();
        mMultipleChoiceQuestionBank = new ArrayList<MultipleChoice>();
        //mTrueFalseQuestionBank.add(new TrueFalse("firstQuestion",true));
        //Add loading text when questionbank have data update textview

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
        //mQuestion = mTrueFalseQuestionBank.get(INDEX).getQuestion();
        mQuestionTextView.setText("loading...");

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

    private  void answerAButtonTapped() {}

    private  void answerBButtonTapped() {}

    private  void answerCButtonTapped() {}

    private void updateQuestion(){
        INDEX = ( INDEX +1 % mTrueFalseQuestionBank.size());

        if(INDEX == 0){
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

        mQuestion = mTrueFalseQuestionBank.get(INDEX).getQuestion();
        mQuestionTextView.setText(mQuestion);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + mTrueFalseQuestionBank.size());
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mTrueFalseQuestionBank.get(INDEX).ismAnswer();

        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
             mScore = mScore +1;
        }else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

//    public void notifyMeAbout(Observer firestore){
//        observer = firestore;
//    }
//
//    public void updateUIOnFireStoreFetchCompletion() {
//
//
//        Log.d("PPPPPPAAAAA", "Firebase done!");
//    }


}




