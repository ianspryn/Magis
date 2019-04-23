package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class ControlStatementQuestions extends QuestionGenerator{

    public ControlStatementQuestions(){
        super();
    }

    @Override
    public void initialize() {
        int selection = rand.nextInt(4);
        switch (selection) {
            case 0:
                getIfElseQuestion();
                break;
            case 1:
                whileLoopQuestions();
                break;
            case 2:
                forLoopQuestion();
                break;
            case 3:
                forEachLoopQuestion();
                break;
        }
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

        question += "int num = "+num+";\n\n";
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

        question += "int num = "+num+";\n\nwhile ("+num+" > "+controller+") {\n\t"+num+"--;\n}";
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

    public void forLoopQuestion(){
        question = "";
        answers.clear();

        int num = rand.nextInt(6);

        question += "int num = "+num+";\nint product = 0;\n\n";
        question += "for (int i = num; i > 0; i--) {\n\tproduct *= i\n}\n\n";
        question += "What is the final value of \"product\"?";

        int falseProduct1 = 0;
        int falseProduct2 = 0;
        int falseProduct3 = 0;

        int product = 0;
        for(int i = num; i>0; i--){
            product *= i;
        }

        for(int i = num; i>1; i--){
            falseProduct2 *= i;
        }

        for(int i = num; i>2; i--){
            falseProduct3 *= i;
        }

        correctAnswer = ""+product;
        answers.add(correctAnswer);
        answers.add(""+falseProduct1);
        answers.add(""+falseProduct2);
        answers.add(""+falseProduct3);

        Collections.shuffle(answers);
        answers.add("Unknown");
    }

    public void forEachLoopQuestion(){
        question = "";
        answers.clear();

        int length = rand.nextInt(5)+1;
        int[] numbers = new int[length];
        int sum = 0;

        question = "int[] numbers = {";

        for(int i = 0; i<numbers.length; i++){
            numbers[i] = rand.nextInt();
            question += ""+numbers[i]+", ";
        }

        question +="};\nint sum = 0;\n";

        question += "for(int num : numbers){\n\tsum += num\n}\n\n";
        question += "What is the final value of \"sum\"?";

        for(int num: numbers){
            sum += num;
        }

        correctAnswer += ""+sum;
        answers.add(correctAnswer);
        answers.add(""+(sum-numbers[length-1]));
        answers.add(""+(sum-numbers[0]));
        answers.add(""+(sum-numbers[length/2]));
        Collections.shuffle(answers);
        answers.add("Unknown");
    }
}
