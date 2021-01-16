package com.example.Model;

import java.util.HashMap;
import java.util.List;

public class POJOForMultipleChoiceQuestions {


    public List<HashMap<String, String>> questions;


    public POJOForMultipleChoiceQuestions() {

    }

    public POJOForMultipleChoiceQuestions(List<HashMap<String, String>> questions) {

        this.questions = questions;
    }

}
