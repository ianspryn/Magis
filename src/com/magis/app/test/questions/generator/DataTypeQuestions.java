package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class DataTypeQuestions extends QuestionGenerator{

    DecimalFormat df;

    private String[] dataTypeString = {"Cat","Dog","Mouse","Bug","Tree","Liquid","Programming","Sum",
            "Four","One","Two","Ten","Five","Zero Point Four", "Four and 4", "3 and 2 and 1", "c and c", "10.23 plus 10.30 equals 20.53"};
    private Character[] dataTypeChar = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
            's','t','u','v','w','x','y','z'};
    private ArrayList<String> dataTypeMatchingString;
    private ArrayList<Character> dataTypeMatchingChar;

    private String[] dataTypeMatchingAnswers = {"int", "double", "String", "char", "None"};

    private char[] characters = {'+','-','*','/','%','<','=','>'};

    public DataTypeQuestions(){
        df = new DecimalFormat("###.##");
        rand = new Random();
        dataTypeMatchingString = new ArrayList<>(Arrays.asList(dataTypeString));
        dataTypeMatchingChar = new ArrayList<>(Arrays.asList(dataTypeChar));
        answers = new ArrayList<>(Arrays.asList(dataTypeMatchingAnswers));
    }

    @Override
    public void initialize() {
        datatypeMatchingQuestion();
    }

    @Override
    public int getNumUnique() {
        return Integer.MAX_VALUE;
    }

    public void datatypeMatchingQuestion(){
        question = "<x> = ";

        int dataTypeSelector = rand.nextInt(4);

        if(dataTypeSelector == 0){
            question = question + rand.nextInt(100);
            correctAnswer = dataTypeMatchingAnswers[0];
        }
        else if(dataTypeSelector == 1){
            int zeroPoint = rand.nextInt(2);
            if(zeroPoint == 0)
                question = question + (df.format(rand.nextDouble()*100));
            else{
                question = question + (double)rand.nextInt(100);
            }
            correctAnswer = dataTypeMatchingAnswers[1];
        }
        else if(dataTypeSelector == 2){
            int stringSelector = rand.nextInt(dataTypeMatchingString.size());
            question = question + "\"" + dataTypeMatchingString.get(stringSelector) + "\"";
            correctAnswer = dataTypeMatchingAnswers[2];
        }
        else{
            int charSelector = rand.nextInt(dataTypeMatchingChar.size());
            question = question + "\'" + dataTypeMatchingChar.get(charSelector)+"\'";
            correctAnswer = dataTypeMatchingAnswers[3];
        }

        question = question + "\n\nWhat data type would best be used to initialize this value?";
    }
}