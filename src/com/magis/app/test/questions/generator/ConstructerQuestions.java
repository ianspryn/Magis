package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class ConstructerQuestions extends QuestionGenerator{

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};

    private String[] comparableStringAnswers = {"A", "B", "C", "D"};

    private String[] equalsStrings = {"int", "String", "Double","char"};
    private String[] equalsAnswers = {"public example(int x){}", "public example(String word){}",
            "public example(Double y){}","public example(char c){}"};
    private String[] secondAnswers = {"public example(int x){}", "public example(String word){}",
            "public example(Double y){}","public example(char c){}"};
    private String[] secondStrings = {"int", "String", "Double","char"};
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
        if else(selection == 1){
            generateConstructerQuestion();
        }
        else(){
            generateSecondContructerQuestions();
        }
    }

    public void generateConstructerQuestion(){
        question = "";
        answers = cAnswers;
        ArrayList<String> four = new ArrayList();
        int random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);

        int fourAnswer = rand.nextInt(comparableStringAnswers.length);



        question = "Which is the correct constructor for: public class "+four.get(fourAnswer)+"(){}\n";
        question += "A. public "+four.get(0)+"(){}\n"+"B. public "+four.get(1)+"(){}\n";
        question +="C. public "+four.get(2)+"(){}\n"+"D. public "+four.get(3)+"(){}\n";

        correctAnswer = comparableStringAnswers[fourAnswer];

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
        int random = rand.nextInt(4);
        String firstPart =  secondStrings[random];


        question = "Which is the correct constructor for class example if we want the paramater to be a "+firstPart+"?\n";
        correctAnswer = secondAnswers[random];


    }
    //2 more


    
}