package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Random;

public abstract class QuestionGenerator {
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";
    Random rand;

    public QuestionGenerator(){
        answers = new ArrayList<>();
        rand = new Random();
    }

    public abstract void initialize();

    public ArrayList<String> getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
