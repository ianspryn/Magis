package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class ClassesQuestions extends QuestionGenerator{

    private String[] comparableStrings = {"Private class Example;", "Public class Example;", "it is visble.\n","it is not visble.\n"};

    private String[] comparableStringAnswers = {"True", "False"};

    private String[] equalsStrings = {"Getter", "Setter","public String getName() {\n" +
            "    return name;\n" +
            "  }","public void setName(String newName) {\n" +
            "    this.name = newName;\n" +
            "  }","public String getName(String newName) {\n" +
            "    this.name = newName;\n" +
            "  }","public void setName() {\n" +
            "    return name;\n" +
            "  }"};
    private String[] equalsAnswers = {"It provides a level of protection to a variabel by returning the contents of"+
            " the variable through a function, rather then pulling the contents of the variable from the variable directly.",
            "It provides a level of protection to a variabel by changing the contents of the variable"+
            "through a function, rather then changing the contents of the variable directly.",
            "Yes", "No"};
    private String[] secondAnswers = {"Example1 is a super class and Example2 is a sub class",
            "Example1 is a sub class and Example2 is a super class",
            "Example1 is a super class and Example2 is a super class",
            "Example1 is a sub class and Example2 is a sub class"};
    private String[] secondStrings = {"extended by", "extends"};
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
        question = "If we have a class: "+firstPart+" "+secondpart;
        if(random == 0 && random1 == 0) {
            correctAnswer = comparableStringAnswers[1];
        }
        else if(random == 1 && random1 == 0){
            correctAnswer = comparableStringAnswers[0];
        }
        else if(random ==0 && random1 ==1){
            correctAnswer = comparableStringAnswers[0];
        }
        else {
            correctAnswer = comparableStringAnswers[1];
        }

    }

    public void generateContentQuestion(){

        if(rand.nextInt(2) == 0) {
            question = "";
            answers.add(eAnswers.get(0));
            answers.add(cAnswers.get(1));
            int random = rand.nextInt(2);
            String firstPart = equalsStrings[random];
            question = "What does a " + firstPart + " do?\n";
            correctAnswer = equalsAnswers[random];
        }
        else{
            question = "";
            answers.add(eAnswers.get(2));
            answers.add(cAnswers.get(3));
            int random = rand.nextInt(4);
            random +=2;
            String firstPart = equalsStrings[random];
            question = "Is this correct? " + firstPart + "\n";
            if(random == 2){
                correctAnswer = equalsAnswers[2];
            }
            else if(random == 3){
                correctAnswer = equalsAnswers[2];
            }
            else if(random == 4){
                correctAnswer = equalsAnswers[3];
            }
            else{
                correctAnswer = equalsAnswers[3];
            }
        }

    }
    public void generateSecondContructerQuestions(){
        question = "";
        answers = sAnswers;
        int random = rand.nextInt(2);
        String firstPart =  secondStrings[random];
        

        question = "If class Example1 " + firstPart + " class Example2, what type of classes are Example1 and Example2?\n";
        correctAnswer = secondAnswers[random];


    }
    //2 more



}