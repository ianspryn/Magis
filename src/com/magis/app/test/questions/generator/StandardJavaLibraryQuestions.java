package com.magis.app.test.questions.generator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class StandardJavaLibraryQuestions extends QuestionGenerator{
    DecimalFormat df = new DecimalFormat("####.##");

    public StandardJavaLibraryQuestions(){
        super();
    }

    private String[] manipulativeStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};

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

    @Override
    public void initialize() {
        int selector = rand.nextInt(2);
        if(selector == 1){
            getMathMethodQuestion();
        }
        else{
            getSubstringQuestion();
        }
    }

    @Override
    public int getNumUnique() {
        return 0;
    }
}
