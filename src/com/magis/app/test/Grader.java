package com.magis.app.test;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Grader {

    private ArrayList<ExamQuestion> questions;
    private double studentPoints; //double because you might only get 0.5 points on a question with multiple correct answers
    private double totalPoints;
    private double grade;

    public Grader() {
        questions = new ArrayList<>();
        studentPoints = 0;
        totalPoints = 0;
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
            studentPoints += ((double) counter / (double) question.getNumCorrectAnswers()) * (double) question.getLevel();
            totalPoints += question.getLevel();
        }
        grade = 100.0 * studentPoints / totalPoints;
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
