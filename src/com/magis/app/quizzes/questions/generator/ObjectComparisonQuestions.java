package com.magis.app.quizzes.questions.generator;

import java.util.Random;

public class ObjectComparisonQuestions {

    Random rand;

    private String chapterTitle = "Object and Object Comparison";

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};
    private String[] comparableStringAnswers = {"Positive", "Negative", "Zero", "Unknown"};

    private String[] equalsStrings = {"Programming is fun!", "programming is Fun!",
            "programming is fun!", "PROGRAMMING IS FUN!"};
    private String[] equalsAnswers = {"True", "False", "Unknown"};

    private String[] answers;

    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public ObjectComparisonQuestions(){
        rand = new Random();
    }

    public void generateComparableQuestion(){
        question = "";
        answers = comparableStringAnswers;

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
        answers = equalsAnswers;

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
            if(firstPart == secondPart){
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

    public String[] getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public String getChapter(){
        return chapterTitle;
    }
}