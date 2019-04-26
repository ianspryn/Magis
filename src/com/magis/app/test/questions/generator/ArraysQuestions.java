package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArraysQuestions extends QuestionGenerator{

    public ArraysQuestions(){
        super();
    }

    @Override
    public void initialize() {
        if (rand.nextInt(2) == 0) getArrayIndexQuestion();
        else getIndexQuestion();
    }

    public void getArrayIndexQuestion(){
        question = "";
        answers.clear();

        int num = rand.nextInt(5)+3;
        int numRows = rand.nextInt(5)+3;
        int numColumns = rand.nextInt(5)+3;

        int[] singleArray = new int[num];
        int[][] doubleArray = new int[numRows][numColumns];

        int doubleOrSingle = rand.nextInt(2);

        if(doubleOrSingle == 0){
            int index = rand.nextInt(singleArray.length);
            question = "Suppose we have a 1D array \"numList\" with the following elements inside it: \n";
            singleArray[0] = rand.nextInt(10);
            question+= ""+singleArray[0];
            for(int i=1; i<singleArray.length; i++){
                boolean uniqueIndex = false;
                while(uniqueIndex == false){
                    singleArray[i] = rand.nextInt(10);
                    uniqueIndex = true;
                    for(int j = 0; j<singleArray.length; j++){
                        if(singleArray[i] == singleArray[j] && j!=i){
                            uniqueIndex = false;
                        }
                    }
                }

                question += ", "+singleArray[i];
            }
            question += "\n\nWhat is the value at \"numList["+index+"]\"?";
            correctAnswer = ""+singleArray[index];
            answers.add(correctAnswer);
            answers.add("Null");

            boolean unique = false;
            String uniquePosition = "";
            while(unique == false){
                unique = true;
                uniquePosition = ""+singleArray[rand.nextInt(singleArray.length)];
                if(uniquePosition.equals(correctAnswer)){
                    unique = false;
                }
            }
            answers.add(uniquePosition);

            unique = false;
            while(unique == false){
                unique = true;
                uniquePosition = ""+singleArray[rand.nextInt(singleArray.length)];
                if(uniquePosition.equals(correctAnswer) || uniquePosition.equals(answers.get(2))){
                    unique = false;
                }
            }
            answers.add(uniquePosition);
        }
        else{
            int indexRow = rand.nextInt(doubleArray.length);
            int indexColumn = rand.nextInt(doubleArray[0].length);
            question = "Suppose we have a 2D array \"numList\" with the following elements inside it: \n";

            for(int i=0; i<doubleArray.length; i++){
                question+=""+doubleArray[i][0];
                for(int j=1; j<doubleArray[i].length; j++){
                    boolean uniqueIndex = false;
                    while(uniqueIndex == false){
                        doubleArray[i][j] = rand.nextInt(10);
                        uniqueIndex = true;
                        for(int k = 0; k<doubleArray[0].length; k++){
                            if(doubleArray[i][k] == doubleArray[i][j] && k!=j){
                                uniqueIndex = false;
                            }
                        }
                    }

                    question += ", "+doubleArray[i][j];
                }
                question+="\n";
            }
            question += "\n\nWhat is the value at \"numList["+indexRow+"]["+indexColumn+"]\"?";
            correctAnswer = ""+doubleArray[indexRow][indexColumn];
            answers.add(correctAnswer);
            answers.add("Null");

            boolean unique = false;
            String uniquePosition = "";
            while(unique == false){
                unique = true;
                uniquePosition = ""+doubleArray[rand.nextInt(doubleArray.length)][rand.nextInt(doubleArray[0].length)];
                if(uniquePosition.equals(correctAnswer)){
                    unique = false;
                }
            }
            answers.add(uniquePosition);

            unique = false;
            while(unique == false){
                unique = true;
                uniquePosition = ""+doubleArray[rand.nextInt(doubleArray.length)][rand.nextInt(doubleArray[0].length)];
                if(uniquePosition.equals(correctAnswer) || uniquePosition.equals(answers.get(2))){
                    unique = false;
                }
            }
            answers.add(uniquePosition);
        }

        Collections.shuffle(answers);
        answers.add("None of the Above");

    }

    public void getIndexQuestion(){
        question = "";
        answers.clear();

        int num = rand.nextInt(5)+1;
        int numRows = rand.nextInt(3)+1;
        int numColumns = rand.nextInt(3)+1;

        int[] singleArray = new int[num];
        int[][] doubleArray = new int[numRows][numColumns];

        int doubleOrSingle = rand.nextInt(2);
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

            if(singleArray.length == 1){
                answers.add("The for loops don't execute");
                answers.add("1");
                answers.add("2");
            }
            else{
                answers.add(""+(sum+sum+1));
                answers.add("0");
                answers.add(""+(sum*2));
            }
            Collections.shuffle(answers);
            answers.add("None of the Above");
        }
        else{
            question = "int[][] numList = new numList["+numRows+"]["+numColumns+"];\nint sum = 0;\n\n";
            question += "for(int i = 0; i < numList.length; i++){\n\tfor(int j = 0; j < numList[i].length; j++){\n\t\tnumList[i][j] = (i+j)+1;\n\t}\n}\n";
            question += "for(int i = 0; i < numList.length; i++){\n\tfor(int j = 0; j < numList[i].length; j++){\n\t\tsum += numList[i][j];\n\t}\n}\n\n";
            question += "What is the value of \"sum\"?";

            for(int i = 0; i<doubleArray.length; i++){
                for(int j = 0; j<doubleArray[i].length; j++) {
                    doubleArray[i][j] = (i+j)+1;
                }
            }
            for(int i = 0; i<doubleArray.length; i++){
                for(int j = 0; j<doubleArray[i].length; j++) {
                    sum += doubleArray[i][j];
                }
            }
            correctAnswer = ""+sum;
            answers.add(correctAnswer);
            answers.add(""+(sum-1));

            if(doubleArray.length == 1 && doubleArray[0].length == 1){
                answers.add("The for loops don't execute");
                answers.add("1");
            }
            else{
                answers.add(""+(sum * 2));
                answers.add("0");
            }
            Collections.shuffle(answers);
            answers.add("None of the Above");
        }
    }
}
