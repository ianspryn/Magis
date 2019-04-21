package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArraysQuestions extends QuestionGenerator{
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";
    Random rand;

    public ArraysQuestions(){
        super();
    }

    public void getArrayIndexQuestion(){
        question = "";
        answers.clear();

        int num = rand.nextInt(5)+1;
        int numRows = rand.nextInt(5)+1;
        int numColumns = rand.nextInt(5)+1;

        int[] singleArray = new int[num];
        int[][] doubleArray = new int[numRows][numColumns];

        int doubleOrSingle = rand.nextInt();

        if(doubleOrSingle == 0){
            int index = rand.nextInt(singleArray.length);
            question = "Suppose we have a 1D array \"numList\" with the following elements inside it: \n";
            for(int i=0; i<singleArray.length; i++){
                singleArray[i] = rand.nextInt(10);
                question += ", "+singleArray[i];
            }
            question += "\n\nWhat is the value at \"numList["+index+"]\"?";
            correctAnswer = ""+singleArray[index];
            answers.add(correctAnswer);
            answers.add("Null");
            answers.add(""+singleArray.length);
            answers.add(""+index);
        }
        else{
            int indexRow = rand.nextInt(doubleArray.length);
            int indexColumn = rand.nextInt(doubleArray[0].length);
            question = "Suppose we have a 2D array \"numList\" with the following elements inside it: \n";

            for(int i=0; i<doubleArray.length; i++){
                for(int j=0; j<doubleArray[i].length; j++){
                    doubleArray[i][j] = rand.nextInt(10);
                    question += ", "+doubleArray[i][j];
                }
            }
            question += "\n\nWhat is the value at \"numList["+indexRow+"]["+indexColumn+"]\"?";
            correctAnswer = ""+doubleArray[indexRow][indexColumn];
            answers.add(correctAnswer);
            answers.add("Null");
            answers.add(""+doubleArray.length);
            answers.add(""+(indexRow+indexColumn));
        }

        Collections.shuffle(answers);
        answers.add("Unknown");

    }

    public void getIndexQuestion(){
        question = "";
        answers.clear();

        int num = rand.nextInt(5)+1;
        int numRows = rand.nextInt(3)+1;
        int numColumns = rand.nextInt(3)+1;

        int[] singleArray = new int[num];
        int[][] doubleArray = new int[numRows][numColumns];

        int doubleOrSingle = rand.nextInt();
        int sum = 0;

        if(doubleOrSingle == 0){
            question = "int[] numList = new numList["+num+"];\nint sum = 0;\n\n";
            question += "for(int i = 0; i < numList.length; i++){\n\tnumList[i] = i;\n}\n";
            question += "for(int i = 0; i < numList.length; i++){\n\tsum += numList[i];\n}\n\n";
            question += "What is the value of \"sum\"?";

            for(int i = 0; i<singleArray.length; i++){
                singleArray[i] = i;
            }
            for(int i = 0; i<singleArray.length; i++){
                sum += singleArray[i];
            }
            correctAnswer = ""+sum;
            answers.add(correctAnswer);
            answers.add(""+(sum-singleArray[0]));

            if(singleArray.length == 1){
                answers.add("The for loops don't execute");
                answers.add("1");
            }
            else{
                answers.add(""+(sum-singleArray[singleArray.length-1]));
                answers.add("0");
            }
            Collections.shuffle(answers);
            answers.add("Unknown");
        }
        else{
            question = "int[][] numList = new numList["+numRows+"]["+numColumns+"];\nint sum = 0;\n\n";
            question += "for(int i = 0; i < numList.length; i++){\n\tfor(int j = 0; j < numList[i].length; j++){\n\t\tnumList[i][j] = i;\n\t}\n}\n";
            question += "for(int i = 0; i < numList.length; i++){\n\tfor(int j = 0; j < numList[i].length; j++){\n\t\tsum += numList[i][j];\n\t}\n}\n\n";
            question += "What is the value of \"sum\"?";

            for(int i = 0; i<doubleArray.length; i++){
                for(int j = 0; j<doubleArray[i].length; j++) {
                    doubleArray[i][j] = (i+j);
                }
            }
            for(int i = 0; i<doubleArray.length; i++){
                for(int j = 0; j<doubleArray[i].length; j++) {
                    sum += doubleArray[i][j];
                }
            }
            correctAnswer = ""+sum;
            answers.add(correctAnswer);
            answers.add(""+(sum-doubleArray[0][0]));

            if(doubleArray.length == 1 && doubleArray[0].length == 1){
                answers.add("The for loops don't execute");
                answers.add("1");
            }
            else{
                answers.add(""+(sum + 10));
                answers.add("0");
            }
            Collections.shuffle(answers);
            answers.add("Unknown");
        }
    }
}
