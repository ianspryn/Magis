package com.magis.app.test;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grader {

    private ArrayList<ExamQuestion> questions;
    private double studentScore; //double because you might only get 0.5 points on a question with multiple correct answers
    private int numQuestions;
    private double grade;

    public Grader(int numQuestions) {
        questions = new ArrayList<>();
        studentScore = 0;
        this.numQuestions = numQuestions;
    }

    public int getNumCorrectAnswers(int key) {
        return questions.get(key).getCorrectAnswers().size();
    }

    public void grade() {
        for (ExamQuestion question : questions) {
            int counter = 0;
            for (String studentAnswer : question.getStudentAnswers()) {
                if (question.getCorrectAnswers().contains(studentAnswer)) {
                    counter++;
                }
            }
            studentScore += ((double) counter / (double) question.getNumCorrectAnswers()) * (double) question.getLevel();
            System.out.println("LEVEL: " + question.getLevel());
        }
        grade = 100.0 * studentScore / (double) numQuestions;
        grade = Double.parseDouble(new DecimalFormat("#.##").format(grade));
    }
    
    public Double getGrade() {
        return grade;
    }

    public boolean isCorrect(int key, String text) {
        return questions.get(key).getCorrectAnswers().contains(text);
    }

    public void addQuestion(ExamQuestion examQuestion) {
        questions.add(examQuestion);
    }
}
