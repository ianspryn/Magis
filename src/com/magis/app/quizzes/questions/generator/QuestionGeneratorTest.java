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

    @Test
    void testIntegerDivision(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getIntegerDivisionQuestion();
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
    void testIncremental(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getIncrementalQuestion();
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
    void testModular(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getModularQuestion();
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
    void testMathMethod(){
        MethodQuestions objc  = new MethodQuestions();
        objc.getMathMethodQuestion();
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
    void testStringMethod(){
        MethodQuestions objc  = new MethodQuestions();
        objc.getStringMethodQuestion();
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
    void testVariableNames(){
        VariableQuestions objc  = new VariableQuestions();
        objc.getVariableNameQuestion();
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
    void testInstanceVariable(){
        VariableQuestions objc  = new VariableQuestions();
        objc.getInstanceVariableQuestion();
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