package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class ObjectComparisonQuestions extends QuestionGenerator{

    Random rand;

    private static String chapterTitle = "Object and Object Comparison";

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};
    private String[] comparableStringAnswers = {"Positive", "Negative", "Zero", "Unknown"};

    private String[] equalsStrings = {"Programming is fun!", "programming is Fun!",
            "programming is fun!", "PROGRAMMING IS FUN!"};
    private String[] equalsAnswers = {"True", "False", "Unknown"};

    private ArrayList<String> answers;
    private ArrayList<String> cAnswers;
    private ArrayList<String> eAnswers;

    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public ObjectComparisonQuestions(){
        super();
        cAnswers = new ArrayList<>(Arrays.asList(comparableStringAnswers));
        eAnswers = new ArrayList<>(Arrays.asList(equalsAnswers));
    }

    public void generateComparableQuestion(){
        question = "";
        answers = cAnswers;

        int random = rand.nextInt(comparableStrings.length);
        String firstPart = comparableStrings[random];
        random = rand.nextInt(comparableStrings.length);
        String secondPart = comparableStrings[random];

        question = "String sent1 = \""+firstPart+"\";\nString sent2 = \""+secondPart+"\";\n";
        question += "sent1.compareTo(sent2);\n\nWhat would be the value of the number returned from this compareTo?";

        if(firstPart.compareTo(secondPart)>0){
            correctAnswer = comparableStringAnswers[0];
        }
        else if(firstPart.compareTo(secondPart)<0){
            correctAnswer = comparableStringAnswers[1];
        }
        else{
            correctAnswer = comparableStringAnswers[2];
        }
    }

    public void generateEqualsQuestion(){
        question = "";
        answers = eAnswers;

        int random = rand.nextInt(equalsStrings.length);
        String firstPart = equalsStrings[random];
        random = rand.nextInt(equalsStrings.length);
        String secondPart = equalsStrings[random];

        question = "String sent1 = \""+firstPart+"\";\nString sent2 = \""+secondPart+"\";\n";

        random = rand.nextInt(3);

        if(random == 0) {
            question += "sent1.equals(sent2);\n\nWill this statement return true or false?";
            if(firstPart.equals(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
        else if(random == 1){
            question += "sent1 == sent2\n\nWill this statement return true or false?";
            if(firstPart.equals(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
        else{
            question += "sent1.equalsIgnoreCase(sent2);\n\nWill this statement return true or false?";
            if(firstPart.equalsIgnoreCase(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
    }
}