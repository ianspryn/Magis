package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

public class InputOutputQuestions extends QuestionGenerator{

    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";

    private String[] scannerAnswers = {"sc.next();", "sc.nextLine();", "sc.nextInt();", "sc.nextDouble();"};
    private String[] words = {"Dog", "Cat", "Rat", "Apple", "The Boy Jumped", "The Dog Played", "Java is Fun"
    , "Programming is a useful tool", "Information is the key to everything"};
    private char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l'};

    public InputOutputQuestions(){
        super();
    }

    @Override
    public void initialize() {
        getScannerQuestions();
    }

    public void getScannerQuestions(){
        answers.clear();
        Collections.addAll(answers, scannerAnswers);
        question = "Suppose we have a scanner sc, and we have to input the following: \n\n";

        int num = rand.nextInt(4);

        String input = "";
        char input2;

        switch(num){
            case 0:
                num = rand.nextInt(10);
                input = ""+num;
                question += input+"\n\n";
                correctAnswer = answers.get(2);
                break;
            case 1:
                double num2 = rand.nextDouble();
                input = ""+num2;
                question += input+"\n\n";
                correctAnswer = answers.get(3);
                break;
            case 2:
                num = rand.nextInt(words.length);
                input = words[num];
                question += input+"\n\n";
                correctAnswer = answers.get(0);
                break;
            case 3:
                num = rand.nextInt(letters.length);
                input2 = letters[num];
                question += input2+"\n\n";
                correctAnswer = answers.get(1);
                break;
        }
        question += "What is the proper way to scan in this input with a scanner?";
    }
}
