package com.example.melbournequizzapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends Activity {



    // TODO: Declare member variables here:

     Button mTrueButton;
     Button mFalseButton;
     TextView mQuestionTextView;
     int mIndex;
     int mQuestion;
     int mScore;
     TextView mScoreTextView;
     ProgressBar mProgressbar;
     ArrayList<DataSnapshot> mSapshotList;
     //DatabaseReference mDatabaseReference;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");
      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
      mScoreTextView = (TextView) findViewById(R.id.score);
      mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);


       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Post post = snapshot.getValue(Post.class);
               System.out.println(post);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               System.out.println("The read failed: " + error.getCode());
           }
       });





      mQuestion = mQuestionBank[mIndex].getmQuestionID();

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
        mIndex = (mIndex +1 % mQuestionBank.length);

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

        mQuestion = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].ismAnswer();

        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
             mScore = mScore +1;
        }else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}
 class Post {

    public String author;
    public String title;

    public Post(String author, String title) {
        // ...
    }

}