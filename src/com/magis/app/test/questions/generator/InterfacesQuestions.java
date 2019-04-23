package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InterfacesQuestions extends QuestionGenerator {

    String[] methods = {"public void display();", "public int getSum();", "public double getAverage;"
    ,"public String toString();", "public void setNum(int num);", "public boolean isEqual(int num);"
    ,"public int getProduct(int num1, int num2);"};

    ArrayList<String> methodList;

    public InterfacesQuestions(){
        super();
        methodList = (ArrayList<String>) Arrays.asList(methods);
    }

    @Override
    public void initialize() {
        getInterfaceMethodQuestion();
    }

    public void getInterfaceMethodQuestion(){
        question = "";
        answers.clear();
        answers.add("Yes");
        answers.add("No");
        answers.add("Unknown");

        correctAnswer = answers.get(1);

        Collections.shuffle(methodList);

        int num = rand.nextInt(methods.length);
        String methodUsed = methodList.get(num);

        question = "public interface in1 {\n\t";

        int limit = rand.nextInt(methods.length);

        for(int i = 0; i<limit; i++){
            question+="\t"+methodList.get(i)+"\n";

            if(methodUsed.equals(methodList.get(i))){
                correctAnswer = answers.get(0);
            }
        }
        question += "\n}";
        question += "\n\nIs \""+methodUsed+"\" a method required in any class that implements interface \"in1\"?";
    }
}
