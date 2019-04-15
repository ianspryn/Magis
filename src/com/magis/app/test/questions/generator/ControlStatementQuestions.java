package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ControlStatementQuestions {
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";
    Random rand;

    public ControlStatementQuestions(){
        answers = new ArrayList<>();
        rand = new Random();
    }

    public void getIfElseQuestion(){
        question = "";
        answers.clear();

        int finalNum = 0;

        int num = rand.nextInt(10);
        int num2 = rand.nextInt(10);
        int num3 = rand.nextInt(10);
        int adder = rand.nextInt(10)+1;
        int subtractor = rand.nextInt(10)+1;
        int product = rand.nextInt(10);

        question += "num = "+num+";\n\n";
        question += "if (num == "+num2+") {\n\tnum = num + "+adder+";\n}";
        question += "else if (num == "+num3+") {\n\tnum = num - "+subtractor+";\n}";
        question += "else {\n\tnum = num * "+product+";\n}";

        if(num == num2){
            finalNum = num + adder;
            correctAnswer = ""+finalNum;
            answers.add(correctAnswer);
            answers.add(""+(num-subtractor));
            answers.add(""+(num*product));
        }
        else if(num == num3){
            finalNum = num - subtractor;
            answers.add(""+(num+adder));
            correctAnswer = ""+finalNum;
            answers.add(correctAnswer);
            answers.add(""+(num*product));
        }
        else{
            finalNum = num * product;
            answers.add(""+(num+adder));
            answers.add(""+(num-subtractor));
            correctAnswer = ""+finalNum;
            answers.add(correctAnswer);
        }

        answers.add("Unknown");

        question+="\n\nWhat is the final result of \"num\"?";
    }

    public void whileLoopQuestions(){
        question = "";
        answers.clear();

        int startingNum = rand.nextInt(10);
        int num = startingNum;
        int controller = rand.nextInt(10)+1;

        question += "num = "+num+";\n\nwhile ("+num+" > "+controller+") {\n\t"+num+"--;\n}";
        question += "\n\nHow many times will this while loop iterate?";

        int j = 0;
        while(num > controller){
            num--;
            j++;
        }

        if(j==0){
            correctAnswer = "The loop will not run at all";
            answers.add(correctAnswer);
            answers.add(""+(j+1)+" times");
            answers.add(""+(j+2)+" times");
            answers.add(""+(j+3)+" times");
        }
        else{
            correctAnswer = ""+j+" times";
            answers.add(correctAnswer);
            answers.add(""+(j+1)+" times");
            answers.add(""+(j+2)+" times");
            answers.add("The loop will not run at all");
        }

        Collections.shuffle(answers);
        answers.add("Unknown");
    }

    public ArrayList<String> getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
