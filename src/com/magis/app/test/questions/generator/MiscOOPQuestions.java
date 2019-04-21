package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Random;

public class MiscOOPQuestions extends QuestionGenerator{
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";

    private String mainClass = "Vehicle";
    private String[] classes = {"Car", "Bike", "Boat", "Plane"};
    private String carClass = "BMW";
    private String bikeClass = "Motorcycle";
    private String boatClass = "Wave_Rider";
    private String planeClass = "Jet_Fighter";
    private String[] subClasses = {carClass, bikeClass, boatClass, planeClass};

    public MiscOOPQuestions(){
        super();
    }

    @Override
    public void initialize() {
        if (rand.nextInt(2) == 0) isaHasAQuestion();
        else getSuperMethodQuestion();
    }

    public void isaHasAQuestion(){
        answers.clear();
        answers.add("True");
        answers.add("False");
        answers.add("Unknown");

        question = "public class "+mainClass+" {...}\n";

        for(int i=0; i<classes.length; i++) {
            question += "public class "+classes[i]+ " extends "+mainClass+" {...}\n";
        }

        question += "public class "+carClass+ " extends "+classes[0]+" {...}\n";
        question += "public class "+bikeClass+ " extends "+classes[1]+" {...}\n";
        question += "public class "+boatClass+ " extends "+classes[2]+" {...}\n";
        question += "public class "+planeClass+ " extends "+classes[3]+" {...}\n\n";

        int isAHasA = rand.nextInt();

        if(isAHasA == 0){
            int trueOrFalse = rand.nextInt();
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
            int trueOrFalse = rand.nextInt();
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
        question = "";
        answers.clear();

        int maxSpeed1 = rand.nextInt(200);
        int maxSpeed2 = rand.nextInt(200);

        question = "public class Vehicle {\n\tint maxSpeed = "+maxSpeed1+";\n}\n\n";

        answers.add(""+maxSpeed1);
        answers.add(""+maxSpeed2);

        int thisOrSuper = rand.nextInt();

        if(thisOrSuper == 0) {
            question += "public class Car extends Vehicle {\n\tint maxSpeed = " +maxSpeed2+ ";" +
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+this.maxSpeed);\n\t}\n}";

            correctAnswer = answers.get(1);
        }
        else {
            question += "public class Car extends Vehicle {\n\tint maxSpeed = " +maxSpeed2+ ";" +
                    "\n\n\tpublic void display(){\n\t\tSystem.out.println(\"Max Speed: \"+super.maxSpeed);\n\t}\n}";

            correctAnswer = answers.get(0);
        }

        answers.add("Null");
        answers.add("IllegalArgumentException");
        answers.add("Unknown");

        question += "\n\nWhat is the result of calling \"display\" in the \"Car\" class?";
    }
}
