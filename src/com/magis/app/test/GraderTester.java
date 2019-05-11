package com.magis.app.test;
import org.junit.Assert;
import org.junit.Test;

public class GraderTester {

    @Test
    public void testGradingFillInTheBlank() {
        ExamQuestion examQuestion = new ExamQuestion();
        examQuestion.setWritten(true);
        examQuestion.setLevel(5);
        examQuestion.setPointsAndQuestionIndex("2 points\nQuestion 1");
        examQuestion.setQuestion("Why am I doing this?");
        examQuestion.addCorrectAnswer("I'm really tired right now");
        examQuestion.addStudentAnswer("I'm really tired right now");

        Grader grader = new Grader();
        grader.addQuestion(examQuestion);
        grader.grade();

        Assert.assertEquals(100.0, grader.getGrade(), 0.01);
    }

    @Test
    public void testGradingFillInTheBlankIncorrectAnswer() {
        ExamQuestion examQuestion = new ExamQuestion();
        examQuestion.setWritten(true);
        examQuestion.setLevel(5);
        examQuestion.setPointsAndQuestionIndex("2 points\nQuestion 1");
        examQuestion.setQuestion("Why am I doing this?");
        examQuestion.addCorrectAnswer("int[] myArray = new int[5];");
        examQuestion.addStudentAnswer("int[] myArray = new int[5];");

        Grader grader = new Grader();
        grader.addQuestion(examQuestion);
        grader.grade();

        Assert.assertEquals(80.0, grader.getGrade(), 0.01);
    }
}
