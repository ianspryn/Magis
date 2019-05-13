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

    private String[] dataTypes = {"int", "double", "String", "char", "void", "program", "Java", "Data", "System", "Integer", "Character"};

    public MethodQuestions(){
        super();
    }

    @Override
    public void initialize() {
        getReturnType();
    }

    @Override
    public int getNumUnique() {
        return dataTypes.length;
    }

    public void getReturnType(){
        answers.clear();
        int dataType = rand.nextInt(dataTypes.length);
        answers.add("int");
        answers.add("double");
        answers.add("String");
        answers.add("char");
        answers.add("Nothing");
        answers.add("None of the Above");
        question = "```public ";

        switch(dataType){
            case 0:
                question += "int ";
                correctAnswer = answers.get(0);
                break;
            case 1:
                question += "double ";
                correctAnswer = answers.get(1);
                break;
            case 2:
                question += "String ";
                correctAnswer = answers.get(2);
                break;
            case 3:
                question += "char ";
                correctAnswer = answers.get(3);
                break;
            case 4:
                question += "void ";
                correctAnswer = answers.get(4);
                break;
            default:
                question += dataTypes[dataType]+" ";
                correctAnswer = answers.get(5);
                break;
        }
        question+= " myMethod(){\n\t.\n\t.\n\t.\n}```\nWhat is the return of this method?";
    }
}
