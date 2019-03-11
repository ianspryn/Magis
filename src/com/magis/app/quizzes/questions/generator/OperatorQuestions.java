package com.magis.app.quizzes.questions.generator;

import java.util.Random;

public class OperatorQuestions {
    Random rand;

    private String[] incrementalEquations = {"++", "--", "+=", "-=", "*=", "/="};

    private String[] manipulativeStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};

    private String[] answers;
    private char[] characters = {'+','-','*','/','%'};
    private String correctAnswer;
    private String question = "";

    public OperatorQuestions(){
        rand = new Random();
    }

    public void getIntegerDivisionQuestion(){
        question = "";
        int firstInt = 0, secondInt = 100;
        double firstDouble = 0;

        question+="int value1 = "+secondInt+"\n";

        int firstData = rand.nextInt(2);
        if(firstData>0){
            firstInt = rand.nextInt(100)+1;
            question += "int value2 = " + firstInt;
        }
        else{
            firstDouble = rand.nextDouble()*100;
            question += "double value2 = " + firstDouble;
        }

    }

    public String[] getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}