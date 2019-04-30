package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Random;

public abstract class QuestionGenerator {
    protected ArrayList<String> answers;
    protected String correctAnswer;
    protected String question = "";
    protected int level = 2;
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

    public int getLevel() { return level; }
}
