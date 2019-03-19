package com.magis.app.quizzes.questions.generator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

class QuestionGeneratorTest {

    @Test
    void testCommentQuestion(){
        CommentQuestions cq = new CommentQuestions();
        cq.generateGeneralCommentQuestion();
        String[] answers = cq.getCommentAnswers();
        String question = cq.getQuestion();
        String correctAnswer = cq.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testDataTypeQuestion(){
        DataTypeQuestions dtq = new DataTypeQuestions();
        dtq.datatypeMatchingQuestion();
        String[] answers = dtq.getAnswers();
        String question = dtq.getQuestion();
        String correctAnswer = dtq.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testObjectComparisonQuestion(){
        ObjectComparisonQuestions objc  = new ObjectComparisonQuestions();
        objc.generateEqualsQuestion();
        String[] answers = objc.getAnswers();
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testSubstringQuestion(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getSubstringQuestion();
        String[] answers = objc.getAnswers();
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }
}