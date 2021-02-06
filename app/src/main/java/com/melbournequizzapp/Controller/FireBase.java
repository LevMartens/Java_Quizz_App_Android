package com.melbournequizzapp.Controller;

import android.util.Log;

import androidx.annotation.NonNull;


import com.melbournequizzapp.Model.MultipleChoice;
import com.melbournequizzapp.Model.POJOForMultipleChoiceQuestions;
import com.melbournequizzapp.Model.POJOForTrueFalseQuestions;
import com.melbournequizzapp.Model.ResultHandler;
import com.melbournequizzapp.Model.TrueFalse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FireBase {

    POJOForMultipleChoiceQuestions fetchedMultipleChoice;
    POJOForTrueFalseQuestions fetchedTrueFalse;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String questionTrueFalse;
    boolean answerTrueFalse;
    String questionMC;
    String answerMC;
    String optionADescription;
    String optionBDescription;
    String optionCDescription;




    public List<TrueFalse> mTrueFalseQuestionBank = new ArrayList<TrueFalse>();
    public List<MultipleChoice> mMultipleChoiceQuestionBank = new ArrayList<MultipleChoice>();



    public List<TrueFalse> getTrueFalseQuestions(ResultHandler handler) {
        DocumentReference docRef = db.collection("questionBankTrueFalse").document("questions");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        fetchedTrueFalse = document.toObject(POJOForTrueFalseQuestions.class);


                        for (int i = 0; i < fetchedTrueFalse.questions.size(); i++) {
                            for (Map.Entry<String, Boolean> pair : fetchedTrueFalse.questions.get(i).entrySet()) {
                                questionTrueFalse = pair.getKey();
                                answerTrueFalse = pair.getValue();

                                mTrueFalseQuestionBank.add(0, new TrueFalse(questionTrueFalse, answerTrueFalse));

                            }

                        }
                        handler.onSuccess();



                    } else {
                        Log.d("d", "No such document");
                    }
                } else {
                    Log.d("r", "get failed with ", task.getException());
                }
            }
        });

   return  mTrueFalseQuestionBank;
    }
    public List<MultipleChoice> getMultipleChoiceQuestions(ResultHandler handler) {
        DocumentReference docRef = db.collection("questionBankMultipleChoice").document("questions");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        fetchedMultipleChoice = document.toObject(POJOForMultipleChoiceQuestions.class);


                        for (int i = 0; i < fetchedMultipleChoice.questions.size(); i++) {
                            questionMC = fetchedMultipleChoice.questions.get(i).get("question");
                            answerMC = fetchedMultipleChoice.questions.get(i).get("answer");
                            optionADescription = fetchedMultipleChoice.questions.get(i).get("optionADescription");
                            optionBDescription = fetchedMultipleChoice.questions.get(i).get("optionBDescription");
                            optionCDescription = fetchedMultipleChoice.questions.get(i).get("optionCDescription");

                            mMultipleChoiceQuestionBank.add(0, new MultipleChoice(questionMC,answerMC,optionADescription,optionBDescription,optionCDescription));



                        }
                       handler.onSuccess();
                    } else {
                        Log.d("d", "No such document");
                    }
                } else {
                    Log.d("r", "get failed with ", task.getException());
                }
            }
        });

        return  mMultipleChoiceQuestionBank;

    }


}

