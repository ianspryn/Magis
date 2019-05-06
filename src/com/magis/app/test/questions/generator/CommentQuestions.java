package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class CommentQuestions extends QuestionGenerator{

    private String[] commentQuestions = {
            "This is a comment",
            "This is\nA comment",
            "Author: Student\nClass: COMP 101\nDescription: ---",
            "Param: Number, number to be summed",
            "Return: Sum, sum of all the numbers added together",
            "This code will do [x]",
            "This code will do [x]\nIt will also do [y]"
    };
    private String[] generalCommentAnswers = {"Single-Line Comment", "Multi-Line Comment", "Java-Doc Comment", "None of the Above"};

    public CommentQuestions(){
        answers = new ArrayList<>(Arrays.asList(generalCommentAnswers));
    }

    @Override
    public void initialize() {
        generateGeneralCommentQuestion();
    }

    public void generateGeneralCommentQuestion(){
        int random = rand.nextInt(commentQuestions.length);
        question = commentQuestions[random];

        question += "\n\nWhat would you use to comment the sentence above?";

        switch(random){
            case 0: correctAnswer = generalCommentAnswers[0];
                break;
            case 1: correctAnswer = generalCommentAnswers[1];
                break;
            case 2: correctAnswer = generalCommentAnswers[2];
                break;
            case 3: correctAnswer = generalCommentAnswers[2];
                break;
            case 4: correctAnswer = generalCommentAnswers[2];
                break;
            case 5: correctAnswer = generalCommentAnswers[0];
                break;
            case 6: correctAnswer = generalCommentAnswers[1];
                break;
        }
    }

    public int getNumUnique(){
        return commentQuestions.length;
    }
}
