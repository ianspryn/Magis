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
    private String[] secondAnswers = {"int x = 3, int y = 5", "int x = 3",
            "int y = 3","nothing at all"};
    private String[] secondStrings = {"```pubic class child extends parent{\n\tpublic child{\n\t\tsuper();\n\t\tint y = 5;\n\t}\n}```",
            "```pubic class child extends parent{\n\tpublic child{\n\t\tsuper();\n\t}\n}```",
            "```pubic class child extends parent{\n\tpublic child{\n\t\tint y = 5;\n\t}\n}```",
            "```pubic class child extends parent{\n\tpublic child{\n\t}\n}```"};
    private ArrayList<String> cAnswers;
    private ArrayList<String> eAnswers;
    private ArrayList<String> sAnswers;

    private String mainClass = "Vehicle";
    private String[] classes = {"Car", "Bike", "Boat", "Plane"};
    private String carClass = "BMW";
    private String bikeClass = "Motorcycle";
    private String boatClass = "Wave_Rider";
    private String planeClass = "Jet_Fighter";
    private String[] subClasses = {carClass, bikeClass, boatClass, planeClass};

    public ConstructerQuestions(){
        super();
        cAnswers = new ArrayList<>(Arrays.asList(comparableStringAnswers));
        eAnswers = new ArrayList<>(Arrays.asList(equalsAnswers));
        sAnswers = new ArrayList<>(Arrays.asList(secondAnswers));
    }

    @Override
    public void initialize() {
        int selection = rand.nextInt(5);
        if(selection == 0){
            generateContentQuestion();
        }
        else if(selection == 1){
            generateConstructerQuestion();
        }
        else if(selection == 2){
            generateSecondContructerQuestions();
        }
        else if(selection == 3){
            getSuperMethodQuestion();
        }
        else{
            isaHasAQuestion();
        }
    }

    @Override
    public int getNumUnique() {
        return equalsAnswers.length + secondAnswers.length + comparableStringAnswers.length + 4 + 4 + 4 + 4 + (4*4);
    }

    public void generateConstructerQuestion(){
        answers.clear();
        for(int i = 0; i<4; i++){
            answers.add(cAnswers.get(i));
        }

        ArrayList<String> four = new ArrayList();
        for (int i = 0; i < 4; i++) {
            String answer = "";
            do { answer = comparableStrings[rand.nextInt(comparableStrings.length)]; }
            while (four.contains(answer));
            four.add(answer);
        }
        /*
        int random;
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        random = rand.nextInt(comparableStrings.length);
        four.add(comparableStrings[random]);
        */

        int fourAnswer = rand.nextInt(comparableStringAnswers.length);



        question = "Which is the correct constructor for: ```public class "+four.get(fourAnswer)+"(){. . .}```\n";
        question += "A. ```public "+four.get(0)+"(){. . .}```\n"+"B. ```public "+four.get(1)+"(){. . . }```\n";
        question +="C. ```public "+four.get(2)+"(){. . .}```\n"+"D. ```public "+four.get(3)+"(){. . .}```\n";

        correctAnswer = comparableStringAnswers[fourAnswer];

    }

    public void generateContentQuestion(){
        answers.clear();
        for(int i = 0; i<4; i++){
            answers.add(eAnswers.get(i));
        }

        int random = rand.nextInt(4);
        String firstPart =  equalsStrings[random];


        question = "Which is the correct constructor for class example if we want the parameter to be a "+firstPart+"?\n";
        correctAnswer = equalsAnswers[random];

    }
    public void generateSecondContructerQuestions(){
        answers.clear();
        for(int i = 0; i<4; i++){
            answers.add(sAnswers.get(i));
        }

        int random = rand.nextInt(4);
        String firstPart =  secondStrings[random];


        question = "For constructor \n"+firstPart+"\nWith parent class:\n " +
                "```pubic class parent{\n     public parent{\n           int x = 3;\n" +
                "   }\n}```\nWhat variables will be initialized in the constructor?\n";
        correctAnswer = secondAnswers[random];


    }
    public void isaHasAQuestion(){
        answers.clear();
        answers.add("True");
        answers.add("False");

        question = "```public class "+mainClass+" {...}\n";

        for(int i=0; i<classes.length; i++) {
            question += "public class "+classes[i]+ " extends "+mainClass+" {...}\n";
        }

        question += "public class "+carClass+ " extends "+classes[0]+" {...}\n";
        question += "public class "+bikeClass+ " extends "+classes[1]+" {...}\n";
        question += "public class "+boatClass+ " extends "+classes[2]+" {...}\n";
        question += "public class "+planeClass+ " extends "+classes[3]+" {...}```\n";

        int isAHasA = rand.nextInt(2);

        if(isAHasA == 0){
            int trueOrFalse = rand.nextInt(2);
            if(trueOrFalse == 0){
                correctAnswer = answers.get(0);
                int mainOrSub = rand.nextInt();
                if(mainOrSub == 0){
                    question += "True or False: \"Vehicle\" has a \""+classes[rand.nextInt(classes.length)]+"\"";
                }
                else{
                    int selectClass = rand.nextInt(classes.length);
                    switch(selectClass){
                        case 0:
                            question += "True or False: \"Car\" has a \"BMW\"";
                            break;
                        case 1:
                            question += "True or False: \"Bike\" has a \"Motorcycle\"";
                            break;
                        case 2:
                            question += "True or False: \"Boat\" has a \"Wave_Rider\"";
                            break;
                        case 3:
                            question += "True or False: \"Plane\" has a \"Jet_Fighter\"";
                            break;
                    }
                }
            }
            else{
                correctAnswer = answers.get(1);
                int selectClass = rand.nextInt(classes.length);
                String wrongClass;
                switch(selectClass){
                    case 0:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("BMW")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \"Car\" has a \""+wrongClass+"\"";
                        break;
                    case 1:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("Motorcycle")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \"Bike\" has a \""+wrongClass+"\"";
                        break;
                    case 2:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("Wave_Rider")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \"Boat\" has a \""+wrongClass+"\"";
                        break;
                    case 3:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("Jet_Fighter")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \"Plane\" has a \""+wrongClass+"\"";
                        break;
                }
            }
        }
        else{
            int trueOrFalse = rand.nextInt(2);
            if(trueOrFalse == 0){
                correctAnswer = answers.get(0);
                int mainOrSub = rand.nextInt();
                if(mainOrSub == 0){
                    question += "True or False: \""+classes[rand.nextInt(classes.length)]+"\" is a \"Vehicle\"";
                }
                else{
                    int selectClass = rand.nextInt(classes.length);
                    switch(selectClass){
                        case 0:
                            question += "True or False: \"BMW\" is a \"Car\"";
                            break;
                        case 1:
                            question += "True or False: \"Motorcycle\" is a \"Bike\"";
                            break;
                        case 2:
                            question += "True or False: \"Wave_Rider\" is a \"Boat\"";
                            break;
                        case 3:
                            question += "True or False: \"Jet_Fighter\" is a \"Plane\"";
                            break;
                    }
                }
            }
            else{
                correctAnswer = answers.get(1);
                int selectClass = rand.nextInt(classes.length);
                String wrongClass;
                switch(selectClass){
                    case 0:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("BMW")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \""+wrongClass+"\" is a \"Car\"";
                        break;
                    case 1:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("Motorcycle")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \""+wrongClass+"\" is a \"Bike\"";
                        break;
                    case 2:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("Wave_Rider")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \""+wrongClass+"\" is a \"Boat\"";
                        break;
                    case 3:
                        wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        while(wrongClass.equals("Jet_Fighter")){
                            wrongClass = subClasses[rand.nextInt(subClasses.length)];
                        }
                        question += "True or False: \""+wrongClass+"\" is a \"Plane\"";
                        break;
                }
            }

        }
    }

    public void getSuperMethodQuestion(){
        answers.clear();

        int maxSpeed1 = rand.nextInt(200)+1;
        int maxSpeed2 = rand.nextInt(200)+1;
        while(maxSpeed2==maxSpeed1){
            maxSpeed2 = rand.nextInt(200)+1;
        }

        question = "```public class Vehicle {\n\tint maxSpeed = "+maxSpeed1+";\n}\n\n";

        answers.add(""+maxSpeed1);
        answers.add(""+maxSpeed2);
        answers.add("0");
        answers.add("Error");
        answers.add("None of the Above");


        int thisOrSuper = rand.nextInt(5);

        if(thisOrSuper == 0) {
            question += "public class Car extends Vehicle {\n\tint maxSpeed = " +maxSpeed2+ ";" +
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+this.maxSpeed);\n\t}\n}```";

            correctAnswer = answers.get(1);
        }
        else if(thisOrSuper == 1){
            question += "public class Car extends Vehicle {\n\tint maxSpeed = "+maxSpeed1+";"+
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+super.maxSpeed);\n\t}\n}```";

            correctAnswer = answers.get(0);
        }
        else if(thisOrSuper == 2){
            question += "public class Car extends Vehicle {\n\tint maxSpeed;" +
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+this.maxSpeed);\n\t}\n}```";

            correctAnswer = answers.get(2);
        }
        else if(thisOrSuper == 3){
            question += "public class Car extends Vehicle {\n\tint maxSpeed = " +maxSpeed2+ ";" +
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+super.minimumSpeed);\n\t}\n}```";

            correctAnswer = answers.get(3);
        }
        else{
            int maxSpeed3 = rand.nextInt(10)+maxSpeed1+maxSpeed2;
            question += "public class Car extends Vehicle {\n\tint maxSpeed = " +maxSpeed3+ ";" +
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+this.maxSpeed);\n\t}\n}```";

            correctAnswer = answers.get(4);
        }

        question += "\n\nWhat is the result of calling \"display\" in the \"Car\" class?";
    }
    //2 more


    
}