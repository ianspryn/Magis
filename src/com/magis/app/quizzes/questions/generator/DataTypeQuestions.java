package com.magis.app.quizzes.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class DataTypeQuestions {

    Random rand;
    DecimalFormat df;

    private String[] dataTypeString = {"Cat","Dog","Mouse","Bug","Tree","Liquid","Programming","Sum",
            "Four","One","Two","Ten","Five","Zero Point Four", "Four and 4", "3 and 2 and 1", "c and c", "10.23 plus 10.30 equals 20.53"};
    private Character[] dataTypeChar = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
            's','t','u','v','w','x','y','z'};
    private ArrayList<String> dataTypeMatchingString = new ArrayList<String>(Arrays.asList(dataTypeString));
    private ArrayList<Character> dataTypeMatchingChar = new ArrayList<Character>(Arrays.asList(dataTypeChar));

    private String[] dataTypeMatchingAnswers = {"int", "double", "String", "char", "None"};

    private String[] answers;

    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public DataTypeQuestions(){
        df = new DecimalFormat("###.##");
        rand = new Random();
    }

    public void datatypeMatchingQuestion(){
        question = "";
        answers = dataTypeMatchingAnswers;
        question = "<x> = ";

        int dataTypeSelector = rand.nextInt(4);

        if(dataTypeSelector == 0){
            question = question + rand.nextInt(100);
            correctAnswer = dataTypeMatchingAnswers[0];
        }
        else if(dataTypeSelector == 1){
            question = question + (df.format(rand.nextDouble()*100));
            correctAnswer = dataTypeMatchingAnswers[1];
        }
        else if(dataTypeSelector == 2){
            int stringSelector = rand.nextInt(dataTypeMatchingString.size());
            question = question + "\"" + dataTypeMatchingString.get(stringSelector) + "\"";
            dataTypeMatchingString.remove(stringSelector);
            correctAnswer = dataTypeMatchingAnswers[2];
        }
        else{
            int charSelector = rand.nextInt(dataTypeMatchingChar.size());
            question = question + "\'" + dataTypeMatchingChar.get(charSelector)+"\'";
            dataTypeMatchingChar.remove(charSelector);
            correctAnswer = dataTypeMatchingAnswers[3];
        }

        question = question + "\n\nWhat data type would best be used to initialize this value?";
    }

    public String[] getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}