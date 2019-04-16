package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArraysQuestions {
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";
    Random rand;

    public ArraysQuestions(){
        answers = new ArrayList<>();
        rand = new Random();
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

    public ArrayList<String> getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
