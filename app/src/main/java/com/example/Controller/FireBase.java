package com.example.Controller;

import android.util.Log;

import androidx.annotation.NonNull;


import com.example.Model.POJOForQuestions;
import com.example.melbournequizzapp.TrueFalse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FireBase {


    POJOForQuestions fetched;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String question;
    boolean answer;


    public List<TrueFalse> mQuestionBank = new ArrayList<TrueFalse>();

    public List<TrueFalse> getQuestions() {
        DocumentReference docRef = db.collection("questionBank").document("questions");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        fetched = document.toObject(POJOForQuestions.class);


                        for (int i = 0; i <fetched.questions.size(); i++) {
                            for (Map.Entry<String, Boolean> pair : fetched.questions.get(i).entrySet()) {
                                question = pair.getKey();
                                answer = pair.getValue();

                                mQuestionBank.add(1, new TrueFalse(question, answer));

                            }

                        }

                    } else {
                        Log.d("d", "No such document");
                    }
                } else {
                    Log.d("r", "get failed with ", task.getException());
                }
            }
        });
   return  mQuestionBank;
    }
}

