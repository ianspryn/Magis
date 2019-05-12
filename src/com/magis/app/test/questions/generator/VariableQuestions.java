package com.magis.app.test.questions.generator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class VariableQuestions extends QuestionGenerator{

    private String[] names1 = {"calculation", "value", "num1", "product", "quotient", "publicNumber", "interest_on_loan",
            "sum", "difference_of_nums", "totalSum", "num3", "square_root", "x", "y"};
    private String[] names2 = {"4sum", "public", "void", "return", "32unit", "difference of nums", "Public Number", "interest on loan",
            " ", "2power", "<greeting>"};

    private ArrayList<String> correctVariables;
    private ArrayList<String> wrongVariables;

    private String[] dataTypeMatchingAnswers = {"int", "double", "String", "char", "None"};
    private char[] characters = {'+','-','*','/','%','<','=','>'};

    public VariableQuestions(){
        super();
        correctVariables = new ArrayList<>(Arrays.asList(names1));
        wrongVariables = new ArrayList<>(Arrays.asList(names2));
    }

    @Override
    public void initialize() {
        if (rand.nextInt(2) == 0) getVariableNameQuestion();
        else getInstanceVariableQuestion();
    }

    @Override
    public int getNumUnique() {
        int answer = names1.length + names2.length;
        return answer;
    }

    public void getVariableNameQuestion(){
        answers.clear();
        answers.add("True");
        answers.add("False");

        int rightWrong = rand.nextInt(2);
        int selector;
        String word = "";

        switch(rightWrong){
            case 0:
                selector = rand.nextInt(correctVariables.size());
                word = correctVariables.get(selector);
                correctVariables.remove(selector);
                correctAnswer = answers.get(0);
                question = "```<datatype> "+word+" = ...;```\n";
                break;
            case 1:
                selector = rand.nextInt(wrongVariables.size());
                word = wrongVariables.get(selector);
                wrongVariables.remove(selector);
                correctAnswer = answers.get(1);
                question = "```<datatype> "+word+" = ...;```\n";
                break;
        }
        question+="True or False, is \""+word+"\" a proper name for a variable?";
    }

    public void getInstanceVariableQuestion(){
        ArrayList<String> shuffler = new ArrayList<>();

        int selector = rand.nextInt(names1.length);
        String word = names1[selector];

        question = "```private int "+word+";\n\n" +
                "public void setThing(int "+word+"){\n\n}```\n" +
                "How would you set the instance variable \""+word+"\" equal to the local variable \""+word+"\"?";

        shuffler.add("this."+word+" = " +word+";");
        shuffler.add(word+" = "+word+";");
        shuffler.add("int value = "+word+";\n"+word+" = value;");
        correctAnswer = shuffler.get(0);
        Collections.shuffle(shuffler);
        shuffler.add("None of the Above");

        answers = shuffler;
    }
}
