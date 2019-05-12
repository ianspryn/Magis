package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class ObjectComparisonQuestions extends QuestionGenerator {

    private static String chapterTitle = "Object and Object Comparison";

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};
    private String[] comparableStringAnswers = {"Positive", "Negative", "Zero", "None of the Above"};

    private String[] equalsStrings = {"Programming is fun!", "programming is Fun!",
            "programming is fun!", "PROGRAMMING IS FUN!"};
    private String[] equalsAnswers = {"True", "False", "None of the Above"};
    private ArrayList<String> cAnswers;
    private ArrayList<String> eAnswers;

    private String[] dataTypeString = {"Cat","DOG","Mouse","Bug","Tree","LIQUID","Programming","SUM",
            "Four","ONE","Two","Ten","five","zero POINT four", "four and 4", "3 and 2 and 1", "c and c", "10.23 plus 10.30 equals 20.53"};


    private char[] characters = {'+','-','*','/','%','<','=','>'};

    public ObjectComparisonQuestions(){
        super();
        cAnswers = new ArrayList<>(Arrays.asList(comparableStringAnswers));
        eAnswers = new ArrayList<>(Arrays.asList(equalsAnswers));
    }

    @Override
    public void initialize() {
        if (rand.nextInt(3) == 0) generateEqualsQuestion();
        else if (rand.nextInt(3)==1) generateComparableQuestion();
        else getStringMethodQuestion();
    }

    @Override
    public int getNumUnique() {
        int answers = ((comparableStrings.length*comparableStrings.length)*comparableStringAnswers.length);
        answers += ((Math.pow(equalsStrings.length, 2))*equalsAnswers.length);
        return answers;
    }

    public void generateComparableQuestion(){
        answers = cAnswers;

        int random = rand.nextInt(comparableStrings.length);
        String firstPart = comparableStrings[random];
        random = rand.nextInt(comparableStrings.length);
        String secondPart = comparableStrings[random];

        question = "```String sent1 = \""+firstPart+"\";\nString sent2 = \""+secondPart+"\";\n";
        question += "sent1.compareTo(sent2);```\nWhat would be the value of the number returned from this compareTo?";

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
        answers = eAnswers;

        int random = rand.nextInt(equalsStrings.length);
        String firstPart = equalsStrings[random];
        random = rand.nextInt(equalsStrings.length);
        String secondPart = equalsStrings[random];

        question = "```String sent1 = \""+firstPart+"\";\nString sent2 = \""+secondPart+"\";\n";

        random = rand.nextInt(3);

        if(random == 0) {
            question += "sent1.equals(sent2);```\nWill this statement return true or false?";
            if(firstPart.equals(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
        else if(random == 1){
            question += "sent1 == sent2;```\nWill this statement return true or false?";
            if(firstPart.equals(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
        else{
            question += "sent1.equalsIgnoreCase(sent2);```\nWill this statement return true or false?";
            if(firstPart.equalsIgnoreCase(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
    }

    public void getStringMethodQuestion(){
        int methodSelector = rand.nextInt(4);
        int stringSelector = rand.nextInt(dataTypeString.length);
        String word = dataTypeString[stringSelector];
        ArrayList<String> shuffler = new ArrayList<>();

        switch(methodSelector){
            case 0:
                question = "```\""+word+"\".length();```\nWhat is the value returned by this method?";
                correctAnswer = ""+word.length();
                shuffler.add(correctAnswer);
                shuffler.add(""+(word.length()-1));
                shuffler.add(""+(word.length()+1));
                shuffler.add(""+(word.length()+2));
                break;
            case 1:
                question = "```\""+word+"\".toUpperCase();```\nWhat is the value returned by this method?";
                correctAnswer = word.toUpperCase();
                shuffler.add(correctAnswer);
                shuffler.add(word+"!!!!!");
                shuffler.add(word.toLowerCase());
                shuffler.add("");
                break;
            case 2:
                question = "```\""+word+"\".toLowerCase();```\nWhat is the value returned by this method?";
                correctAnswer = word.toLowerCase();
                shuffler.add(correctAnswer);
                shuffler.add(word+"...");
                shuffler.add(word.toUpperCase());
                shuffler.add("");
                break;
            case 3:
                stringSelector = rand.nextInt(dataTypeString.length);
                String word2 = dataTypeString[stringSelector];
                int endPoint = rand.nextInt(word.length());
                int startPoint = 0;
                if(endPoint != 0) startPoint = rand.nextInt(endPoint);
                question = "```\""+word+"\".replace(\'"+word.substring(startPoint, endPoint)+"\', \""+word2+"\");```\nWhat is the value returned by this method?";
                correctAnswer = word.replace(word.substring(startPoint,endPoint),word2);
                shuffler.add(correctAnswer);
                shuffler.add(word2);
                shuffler.add(word+=word2);
                shuffler.add("");
                break;
        }

        Collections.shuffle(shuffler);
        shuffler.add("None of the Above");
        answers = shuffler;
    }
}