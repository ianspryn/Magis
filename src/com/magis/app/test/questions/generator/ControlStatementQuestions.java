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

    @Override
    public int getNumUnique() {
        return 60 + 20 + 6 + 5;
    }

    public void getIfElseQuestion(){
        answers.clear();

        int finalNum = 0;

        int num = rand.nextInt(10);
        int num2 = rand.nextInt(10);
        int num3 = rand.nextInt(10);
        int adder = rand.nextInt(10)+1;
        int subtractor = rand.nextInt(10)+1;
        int product = rand.nextInt(10);

        question = "```int num = "+num+";\n\n";
        question += "if (num == "+num2+") {\n\tnum = num + "+adder+";\n}";
        question += "else if (num == "+num3+") {\n\tnum = num - "+subtractor+";\n}";
        question += "else {\n\tnum = num * "+product+";\n}```";

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

        question+="\nWhat is the final result of \"num\"?";
    }

    public void whileLoopQuestions(){
        answers.clear();

        int startingNum = rand.nextInt(10)+1;
        int num = startingNum;
        int controller = rand.nextInt(startingNum);

        question = "```int num = "+num+";\n\nwhile (num > "+controller+") {\n\tnum--;\n}```";
        question += "\nHow many times will this while loop iterate?";

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
    }

    public void forLoopQuestion(){
        answers.clear();

        int num = rand.nextInt(3)+3;

        question = "```int num = "+num+";\nint product = 1;\n\n";
        question += "for (int i = num; i > 0; i--) {\n\tproduct *= i\n}```\n";
        question += "What is the final value of \"product\"?";

        int falseProduct1 = 1;
        int falseProduct2 = 1;
        int falseProduct3 = 1;

        int product = 1;
        for(int i = num; i>0; i--){
            product *= i;
        }

        for(int i = (num-1); i>0; i--){
            falseProduct1 *= i;
        }

        for(int i = (num-2); i>0; i--){
            falseProduct2 *= i;
        }

        if((num-3) == 0){
            falseProduct3 = 0;
        }
        else {
            for (int i = (num - 3); i > 0; i--) {
                falseProduct3 *= i;
            }
        }

        int noneOfTheAbove = rand.nextInt(10);
        if(noneOfTheAbove == 0){
            correctAnswer = "None of the Above";
            product++;
            answers.add(""+product);
        }
        else {
            correctAnswer = "" + product;
            answers.add(correctAnswer);
        }
        answers.add(""+falseProduct1);
        answers.add(""+falseProduct2);
        answers.add(""+falseProduct3);

        Collections.shuffle(answers);
        answers.add("None of the Above");
    }

    public void forEachLoopQuestion(){
        answers.clear();

        int length = rand.nextInt(5)+1;
        int[] numbers = new int[length];
        int sum = 0;

        question = "```int[] numbers = {";
        numbers[0] = rand.nextInt(10)+1;
        question += ""+numbers[0];

        for(int i = 1; i<numbers.length; i++){
            numbers[i] = rand.nextInt(10)+1;
            question += ", "+numbers[i];
        }

        question +="};\nint sum = 0;\n";

        question += "for(int num : numbers){\n\tsum += num\n}```\n";
        question += "What is the final value of \"sum\"?";

        for(int num: numbers){
            sum += num;
        }

        int noneOfTheAbove = rand.nextInt(2);
        if(noneOfTheAbove == 0){
            correctAnswer = "None of the Above";
            sum+=numbers.length;
            answers.add(""+sum);
            answers.add(""+(sum+1));
        }
        else {
            correctAnswer = ""+sum;
            answers.add(correctAnswer);
            answers.add(""+(sum+1));
        }
        answers.add(""+(sum-numbers[length-1]));
        answers.add("0");

        Collections.shuffle(answers);
        answers.add("None of the Above");
    }
}
