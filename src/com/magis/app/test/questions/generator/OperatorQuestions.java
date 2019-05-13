package com.magis.app.test.questions.generator;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class OperatorQuestions extends QuestionGenerator {
    DecimalFormat df = new DecimalFormat("####.##");

    private String chapterTitle = "Operators";

    private String[] incrementalEquations = {"++", "--", "+=", "-=", "*=", "/="};

    private String[] manipulativeStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};

    private char[] characters = {'+','-','*','/','%'};

    public OperatorQuestions(){
        super();
    }

    @Override
    public void initialize() {
        int selection = rand.nextInt(4);
        switch (selection) {
            case 0:
                getSubstringQuestion();
                break;
            case 1:
                getModularQuestion();
                break;
            case 2:
                getIntegerDivisionQuestion();
                break;
            case 3:getIncrementalQuestion();
                break;
            case 4:getMathMethodQuestion();
                break;
        }
    }

    @Override
    public int getNumUnique() {
        return Integer.MAX_VALUE;
    }

    public void getIntegerDivisionQuestion(){
        int firstInt, secondInt = 100;
        double firstDouble;

        ArrayList<String> shuffler = new ArrayList<>();

        question = "```int value1 = "+secondInt+";\n";

        int firstData = rand.nextInt(2);
        if(firstData>0){
            firstInt = rand.nextInt(100)+1;
            question += "int value2 = " + firstInt+";\n";
            correctAnswer = ""+(secondInt/firstInt);
            shuffler.add(correctAnswer);
            shuffler.add(""+(df.format(secondInt/(double)firstInt)));
        }
        else{
            firstDouble = (rand.nextDouble()+1)*100;
            question += "double value2 = " + df.format(firstDouble)+";\n";
            correctAnswer = ""+(df.format(secondInt/firstDouble));
            shuffler.add(correctAnswer);
            shuffler.add(""+(secondInt/(int)firstDouble));
        }

        for(int i=2; i<4; i++){
            shuffler.add(""+(rand.nextInt(50)));
        }
        shuffler.add(""+(df.format(rand.nextDouble()*10)));
        Collections.shuffle(shuffler);

        answers = shuffler;

        question+="quotient = value1 / value2;```\nWhat is the value of \"quotient\"?";
    }

    public void getIncrementalQuestion(){
        int number;
        int extraNumber;
        number = rand.nextInt(11);
        ArrayList<String> shuffler = new ArrayList<>();

        question = "```int value = " + number;

        int incrementSelector = rand.nextInt(incrementalEquations.length+1);

        switch(incrementSelector){
            case 0:
                question += "\nvalue++;";
                correctAnswer = ""+(number+1);
                shuffler.add(correctAnswer);
                break;
            case 1:
                question += "\nvalue--;";
                correctAnswer = ""+(number-1);
                shuffler.add(correctAnswer);
                break;
            case 2:
                extraNumber = rand.nextInt(11);
                question += "\nvalue += " + extraNumber+";";
                number += extraNumber;
                correctAnswer = ""+(number);
                shuffler.add(correctAnswer);
                break;
            case 3:
                extraNumber = rand.nextInt(11);
                question += "\nvalue -= " + extraNumber+";";
                number -= extraNumber;
                correctAnswer = ""+(number);
                shuffler.add(correctAnswer);
                break;
            case 4:
                extraNumber = rand.nextInt(11);
                question += "\nvalue *= " + extraNumber+";";
                number *= extraNumber;
                correctAnswer = ""+(number);
                shuffler.add(correctAnswer);
                break;
            case 5:
                extraNumber = rand.nextInt(11)+1;
                question += "\nvalue /= " + extraNumber+";";
                number /= extraNumber;
                correctAnswer = ""+(number);
                shuffler.add(correctAnswer);
                break;
            case 6:
                extraNumber = rand.nextInt(11)+1;
                question += "\nvalue ** "+extraNumber+";";
                correctAnswer = "None of the Above";
                shuffler.add(""+extraNumber);
        }

        shuffler.add(""+(number+25));
        if(number == 0){
            shuffler.add(""+(number + 1));
        }
        else {
            shuffler.add("" + (number * 11));
        }
        shuffler.add(""+(number-14));
        Collections.shuffle(shuffler);
        shuffler.add("None of the Above");

        answers = shuffler;

        question+="```\nWhat is the value of \"value\" after this incremental equation?";
    }

    public void getModularQuestion(){
        int divisor = rand.nextInt(26)+1;
        int divider = rand.nextInt(divisor)+1;
        ArrayList<String> shuffler = new ArrayList<>();

        question = "```"+divisor+" % "+divider+"```\nWhat is the result of this operation?";
        correctAnswer = ""+(divisor%divider);
        shuffler.add(correctAnswer);
        shuffler.add((""+(divisor+divider)));
        shuffler.add((""+(divisor*divider)));
        shuffler.add((""+(divisor+1)));
        Collections.shuffle(shuffler);
        shuffler.add("None of the Above");
        answers = shuffler;
    }

    public void getSubstringQuestion(){
        int addString = rand.nextInt(manipulativeStrings.length);
        String message = manipulativeStrings[addString];
        ArrayList<String> shuffler = new ArrayList();

        int endPoint = rand.nextInt(message.length()-1)+1;
        int startPoint = rand.nextInt(endPoint);

        question = "```String message = \""+message+"\".substring("+startPoint+", "+endPoint+");```" +
                "\nWhat does message equal after this method?";

        correctAnswer = "\""+message.substring(startPoint, endPoint)+"\"";
        shuffler.add(correctAnswer);
        if(startPoint<endPoint)
            shuffler.add("\""+message.substring(startPoint+1, endPoint)+"\"");
        else{
            if(endPoint == message.length()-1)
                shuffler.add("\""+message.substring(startPoint-1, endPoint)+"\"");
            else if(endPoint == 0)
                shuffler.add("\""+message.substring(startPoint, endPoint+1)+"\"");
            else
                shuffler.add("\""+message.substring(startPoint-1, endPoint)+"\"");
        }

        String reverseMessage = "\"";
        for(int i=message.length()-2; i>=0; i--){
            reverseMessage+=message.substring(i,i+1);
        }
        reverseMessage+="\"";
        shuffler.add(reverseMessage);
        shuffler.add("A random String of size "+(endPoint-startPoint));
        Collections.shuffle(shuffler);
        shuffler.add("None of the Above");

        answers = shuffler;
    }

    public void getMathMethodQuestion(){
        int operationSelector = rand.nextInt(2);
        ArrayList<String> shuffler = new ArrayList<>();

        switch(operationSelector){
            case 0:
                double num1 = rand.nextInt(101);
                question = "```double root = Math.sqrt("+num1+");```\nWhat is the value of \"root\"?";
                correctAnswer = ""+df.format(Math.sqrt(num1));
                shuffler.add(correctAnswer);
                shuffler.add(""+(num1+1));
                shuffler.add(""+df.format(Math.pow(num1,2)));
                shuffler.add(""+df.format(Math.sin(num1)));
                break;
            case 1:
                double num2 = rand.nextInt(11);
                double power = rand.nextInt(3)+1;
                question = "```double power = Math.sqrt("+num2+", "+power+");```\nWhat is the value of \"power\"?";
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