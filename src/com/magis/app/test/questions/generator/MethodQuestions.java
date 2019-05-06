package com.magis.app.test.questions.generator;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class MethodQuestions extends QuestionGenerator{
    DecimalFormat df = new DecimalFormat("####.##");

    private String[] dataTypeString = {"Cat","DOG","Mouse","Bug","Tree","LIQUID","Programming","SUM",
            "Four","ONE","Two","Ten","five","zero POINT four", "four and 4", "3 and 2 and 1", "c and c", "10.23 plus 10.30 equals 20.53"};

    private String[] dataTypes = {"int", "double", "String", "char"};

    public MethodQuestions(){
        super();
    }

    @Override
    public void initialize() {
        getMathMethodQuestion();
    }

    @Override
    public int getNumUnique() {
        return Integer.MAX_VALUE;
    }

    public void getMathMethodQuestion(){
        int operationSelector = rand.nextInt(2);
        ArrayList<String> shuffler = new ArrayList<>();

        switch(operationSelector){
            case 0:
                double num1 = rand.nextInt(101);
                question = "double root = Math.sqrt("+num1+");\n\nWhat is the value of \"root\"?";
                correctAnswer = ""+df.format(Math.sqrt(num1));
                shuffler.add(correctAnswer);
                shuffler.add(""+(num1+1));
                shuffler.add(""+df.format(Math.pow(num1,2)));
                shuffler.add(""+df.format(Math.sin(num1)));
                break;
            case 1:
                double num2 = rand.nextInt(11);
                double power = rand.nextInt(3)+1;
                question = "double power = Math.sqrt("+num2+", "+power+");\n\nWhat is the value of \"power\"?";
                correctAnswer = ""+df.format(Math.pow(num2, power));
                shuffler.add(correctAnswer);
                shuffler.add(""+(num2+1));
                shuffler.add(""+df.format(Math.sqrt(num2)));
                shuffler.add(""+df.format(Math.sin(num2)));
                break;
        }

        Collections.shuffle(shuffler);
        shuffler.add("None of the Above");
        answers = shuffler;
    }
}
