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
        methodList = new ArrayList<>();
        Collections.addAll(methodList, methods);
    }

    @Override
    public void initialize() {
        int selection = rand.nextInt(2);
        if(selection == 0) {
            getInterfaceMethodQuestion();
        }
        else{
            interfaceVariableQuestion();
        }
    }

    @Override
    public int getNumUnique() {
        int answer = 40 + 7;
        return answer;
    }

    public void getInterfaceMethodQuestion(){
        answers.clear();
        answers.add("Yes");
        answers.add("No");
        answers.add("None of the Above");

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

    public void interfaceVariableQuestion(){
        answers.clear();

        int a = rand.nextInt(10)+1;
        int b = rand.nextInt(10)+1;
        b += a;
        int c = rand.nextInt(10)+1;
        c += b;
        int d = rand.nextInt(10)+1;
        d += c;

        int[] numbers = {a, b, c, d};

        int rightNum = numbers[rand.nextInt(numbers.length)];

        question = "public interface in1 {\n\tpublic static final int a = "+a;
        question += ";\n\tpublic static final int b = "+b+";\n\n\tpublic void display();}";
        question += "\n\n public class questionClass(){\n\tpublic void display(){\n\t\tint c = "+c+";\n\t\tint d = "+d+";\n\t\t"+
                "System.out.println("+rightNum+");\n\t}";
        question += "\npublic static void main(String[] args){\n\t\tquestionClass q = new questionClass();\n\t\tq.display();\n\t}\n}";
        question += "\n\nWhat is the result of this code?";

        answers.add(""+a);
        answers.add(""+b);
        answers.add(""+c);
        answers.add(""+d);
        answers.add("None of the Above");
        correctAnswer = ""+rightNum;
    }
}
