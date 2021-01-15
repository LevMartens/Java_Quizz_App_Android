package com.example.melbournequizzapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {



    // TODO: Declare member variables here:

     Button mTrueButton;
     Button mFalseButton;
     TextView mQuestionTextView;
     int mIndex;
     String mQuestion;
     int mScore;
     TextView mScoreTextView;
     ProgressBar mProgressbar;
     EmployeePojo fetched;
     String test1;
     boolean test2;
    String test3;
    boolean test4;
    String test5;
    boolean test6;
    private  List<TrueFalse> mQuestionBank = new ArrayList<TrueFalse>();



    // TODO: Uncomment to create question bank


    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.size());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
      mScoreTextView = (TextView) findViewById(R.id.score);
      mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);


        DocumentReference docRef = db.collection("questionBank").document("questions");









        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("o", "DocumentSnapshot data: " + document.getData());
                        fetched = document.toObject(EmployeePojo.class);
                        Log.d("Fetched", "fetched!"  +fetched.questions);




                        for (int i = 0; i <fetched.questions.size(); i++) {
                            for (Map.Entry<String, Boolean> pair : fetched.questions.get(i).entrySet()) {
                            test1 = pair.getKey();
                              test2 = pair.getValue();

                              mQuestionBank.add(1, new TrueFalse(test1,test2));


                             }
                            Log.d("test", "foooooooo   " +test1+ "  "+test2);


                        }
                        Log.d("test", "PPPPP" +mQuestionBank.get(0).getmQuestionID() +mQuestionBank.get(1).getmQuestionID() +mQuestionBank.get(2).getmQuestionID());


//




                    } else {
                        Log.d("d", "No such document");
                    }
                } else {
                    Log.d("r", "get failed with ", task.getException());
                }
            }
        });







      mQuestionBank.add(new TrueFalse("firstQuestion",true));
      mQuestion = mQuestionBank.get(mIndex).getmQuestionID();

        mQuestionTextView.setText(mQuestion);



      mTrueButton.setOnClickListener(new View.OnClickListener() {


          @Override
          public void onClick(View view) {
              checkAnswer(true);
             updateQuestion();
          }
      });

      mFalseButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateQuestion();
            }
        });




    }
    private void updateQuestion(){
        mIndex = (mIndex +1 % mQuestionBank.size());

        if(mIndex == 0){
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

        mQuestion = mQuestionBank.get(mIndex).getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + mQuestionBank.size());
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank.get(mIndex).ismAnswer();

        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
             mScore = mScore +1;
        }else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}

class EmployeePojo {


    public List<HashMap<String, Boolean>> questions;


    public EmployeePojo() {

    }

    public EmployeePojo( List<HashMap<String, Boolean>> questions) {

        this.questions = questions;
    }


}


