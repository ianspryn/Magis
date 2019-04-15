package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Random;

public class ExceptionsQuestions {
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";
    Random rand;

    public ExceptionsQuestions(){
        answers = new ArrayList<>();
        rand = new Random();
    }

    public ArrayList<String> getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
