package com.magis.app.test.questions.generator;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class MethodQuestions {
    Random rand;
    DecimalFormat df = new DecimalFormat("####.##");

    private static String chapterTitle = "Methods";

    private String[] dataTypeString = {"Cat","DOG","Mouse","Bug","Tree","LIQUID","Programming","SUM",
            "Four","ONE","Two","Ten","five","zero POINT four", "four and 4", "3 and 2 and 1", "c and c", "10.23 plus 10.30 equals 20.53"};

    private String[] dataTypes = {"int", "double", "String", "char"};


    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";

    public MethodQuestions(){
        rand = new Random();
        answers = new ArrayList<>();
    }

    public void getStringMethodQuestion(){
        question="";
        int methodSelector = rand.nextInt(4);
        int stringSelector = rand.nextInt(dataTypeString.length);
        String word = dataTypeString[stringSelector];
        ArrayList<String> shuffler = new ArrayList<>();

        switch(methodSelector){
            case 0:
                question += "\""+word+"\".length();\n\nWhat is the value returned by this method?";
                correctAnswer = ""+word.length();
                shuffler.add(correctAnswer);
                shuffler.add(""+(word.length()-1));
                shuffler.add(""+(word.length()+1));
                shuffler.add(""+(word.length()+2));
                break;
            case 1:
                question += "\""+word+"\".toUpperCase();\n\nWhat is the value returned by this method?";
                correctAnswer = word.toUpperCase();
                shuffler.add(correctAnswer);
                shuffler.add(word+"!!!!!");
                shuffler.add(word.toLowerCase());
                shuffler.add("");
                break;
            case 2:
                question += "\""+word+"\".toLowerCase();\n\nWhat is the value returned by this method?";
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
                int startPoint = rand.nextInt(endPoint);
                question += "\""+word+"\".replace(\'"+word.substring(startPoint, endPoint)+"\', \""+word2+"\");\n\nWhat is the value returned by this method?";
                correctAnswer = word.replace(word.substring(startPoint,endPoint),word2);
                shuffler.add(correctAnswer);
                shuffler.add(word2);
                shuffler.add(word+=word2);
                shuffler.add("");
                break;
        }

        Collections.shuffle(shuffler);
        shuffler.add("Unknown");
        answers = shuffler;
    }

    public void getMathMethodQuestion(){
        question="";
        int operationSelector = rand.nextInt(2);
        ArrayList<String> shuffler = new ArrayList<>();

        switch(operationSelector){
            case 0:
                double num1 = rand.nextInt(101);
                question += "double root = Math.sqrt("+num1+");\n\nWhat is the value of \"root\"?";
                correctAnswer = ""+df.format(Math.sqrt(num1));
                shuffler.add(correctAnswer);
                shuffler.add(""+(num1+1));
                shuffler.add(""+df.format(Math.pow(num1,2)));
                shuffler.add(""+df.format(Math.sin(num1)));
                break;
            case 1:
                double num2 = rand.nextInt(11);
                double power = rand.nextInt(3)+1;
                question += "double power = Math.sqrt("+num2+", "+power+");\n\nWhat is the value of \"power\"?";
                correctAnswer = ""+df.format(Math.pow(num2, power));
                shuffler.add(correctAnswer);
                shuffler.add(""+(num2+1));
                shuffler.add(""+df.format(Math.sqrt(num2)));
                shuffler.add(""+df.format(Math.sin(num2)));
                break;
        }

        Collections.shuffle(shuffler);
        shuffler.add("Unknown");
        answers = shuffler;
    }

    public ArrayList<String> getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public static String getChapter(){
        return chapterTitle;
    }
}
