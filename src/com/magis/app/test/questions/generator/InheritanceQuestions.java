/*package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class InheritanceQuestions extends QuestionGenerator {

    private String[] secondAnswers = {"Example1 is a super class and Example2 is a sub class",
            "Example1 is a sub class and Example2 is a super class",
            "Example1 is a super class and Example2 is a super class",
            "Example1 is a sub class and Example2 is a sub class"};
    private String[] secondStrings = {"extended by", "extends"};
    private ArrayList<String> cAnswers;
    private ArrayList<String> eAnswers;
    private ArrayList<String> sAnswers;

    public InheritanceQuestions() {
        super();
        sAnswers = new ArrayList<>(Arrays.asList(secondAnswers));
    }

    @Override
    public void initialize() {
        generateSecondContructerQuestions();
    }


    public void generateSecondContructerQuestions() {
        question = "";
        answers = sAnswers;
        int random = rand.nextInt(2);
        String firstPart = secondStrings[random];


        question = "If class Example1 " + firstPart + " class Example2, what type of classes are Example1 and Example2?\n";
        correctAnswer = secondAnswers[random];


    }
}
*/