package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class ClassesQuestions extends QuestionGenerator{

    private String[] comparableStrings = {"Private class Example", "Public class Example", "is it visble?\n","is it not visble?\n"};

    private String[] comparableStringAnswers = {"True", "False"};

    private String[] equalsStrings = {"int", "String", "Double","char"};
    private String[] equalsAnswers = {"public example(int x){}", "public example(String word){}",
            "public example(Double y){}","public example(char c){}"};
    private String[] secondAnswers = {"int x = 3, int y = 5", "int x = 3",
            "int y = 3","nothing at all"};
    private String[] secondStrings = {"pubic class child extends parent{\n     public child{\n           super();\n        int y = 5;\n      }\n}",
            "pubic class child extends parent{\n     public child{\n           super();\n}\n}",
            "pubic class child extends parent{\n     public child{\n        int y = 5;\n      }\n}",
            "pubic class child extends parent{\n     public child{\n      }\n}"};
    private ArrayList<String> cAnswers;
    private ArrayList<String> eAnswers;
    private ArrayList<String> sAnswers;

    public ConstructerQuestions(){
        super();
        cAnswers = new ArrayList<>(Arrays.asList(comparableStringAnswers));
        eAnswers = new ArrayList<>(Arrays.asList(equalsAnswers));
        sAnswers = new ArrayList<>(Arrays.asList(secondAnswers));
    }

    @Override
    public void initialize() {
        int selection = rand.nextInt(3);
        if(selection == 0){
            generateContentQuestion();
        }
        else if(selection == 1){
            generateConstructerQuestion();
        }
        else {
            generateSecondContructerQuestions();
        }
    }

    public void generateConstructerQuestion(){
        question = "";
        answers = cAnswers;
        int random = rand.nextInt(2);
        String firstPart =  secondStrings[random];
        int random2 = rand.nextInt(2);
        String secondPart =  secondStrings[random2+2];

        question = "Is "+firstPart+" "+secondpart+"\n";
        if(random == && random1 ==) {
            correctAnswer = comparableStringAnswers[];
        }
        else if(random == && random1 ==){
            correctAnswer = comparableStringAnswers[];
        }
        else if(random == && random1 ==){
            correctAnswer = comparableStringAnswers[];
        }
        else {
            correctAnswer = comparableStringAnswers[];
        }

    }

    public void generateContentQuestion(){
        question = "";
        answers = eAnswers;

        int random = rand.nextInt(4);
        String firstPart =  equalsStrings[random];


        question = "Which is the correct constructor for class example if we want the paramater to be a "+firstPart+"?\n";
        correctAnswer = equalsAnswers[random];

    }
    public void generateSecondContructerQuestions(){
        question = "";
        answers = sAnswers;
        int random = rand.nextInt(2);
        String firstPart =  secondStrings[random];


        question = "For constructor \n"+firstPart+"\n with parent class\n " +
                "pubic class parent{\n     public parent{\n           int x = 3;\n" +
                "   }\n}\nwhat variables will be initialized in the constructor?\n";
        correctAnswer = secondAnswers[random];


    }
    //2 more



}