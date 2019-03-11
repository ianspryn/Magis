package com.magis.app.quizzes.questions.generator;

import java.util.Random;

public class CommentQuestions {
    Random rand;

    private String[] commentQuestions = {
            "This is a comment",
            "This is\nA comment",
            "Author: Student\nClass: COMP 101\nDescription: ---",
            "This code will do [x]",
            "This code will do [x]\nIt will also do [y]"
    };
    private String[] generalCommentAnswers = {"Single-Line Comment", "Multi-Line Comment", "Java-Doc Comment", "Unknown"};

    private String correctAnswer;
    private String question = "";

    public CommentQuestions(){
        rand = new Random();
    }

    public void generateGeneralCommentQuestion(){
        question = "";
        int random;

        do {
            random = rand.nextInt(5);
            question = commentQuestions[random];
            commentQuestions[random] = "";
        }while(question.equals(""));

        question = question+"\n\nWhat would you use to comment the sentence above?";

        switch(random){
            case 0: correctAnswer = generalCommentAnswers[0];
                break;
            case 1: correctAnswer = generalCommentAnswers[1];
                break;
            case 2: correctAnswer = generalCommentAnswers[2];
                break;
            case 3: correctAnswer = generalCommentAnswers[0];
                break;
            case 4: correctAnswer = generalCommentAnswers[1];
                break;
        }
    }

    public String[] getCommentAnswers(){ return generalCommentAnswers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
