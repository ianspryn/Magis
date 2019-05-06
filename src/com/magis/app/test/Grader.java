package com.magis.app.test;

import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Grader {

    private ArrayList<ExamQuestion> questions;
    private ArrayList<Label> pointLabels;
    private double studentPoints; //double because you might only get 0.5 points on a question with multiple correct answers
    private double totalPoints;
    private double grade;

    public Grader() {
        questions = new ArrayList<>();
        pointLabels = new ArrayList<>();
        studentPoints = 0;
        totalPoints = 0;
    }

    public int getNumCorrectAnswers(int key) {
        return questions.get(key).getCorrectAnswers().size();
    }

    public void grade() {
        int pointsLabelIndex = 0;
        for (ExamQuestion question : questions) {
            double pointsPerAnswer = (double) question.getLevel() / question.getNumCorrectAnswers();
            double total = 0;
            for (String studentAnswer : question.getStudentAnswers()) {
                if (question.getCorrectAnswers().contains(studentAnswer)) {
                    total += pointsPerAnswer;
                }
                if (question.getIncorrectAnswers().contains(studentAnswer)) {
                    total -= pointsPerAnswer;
                }
            }

            total = Double.parseDouble(new DecimalFormat("#.##").format(total)); //don't let 0.3333333333333333 be a thing. Make it 0.33.
            total = Math.max(0, total); //don't let the score be negative

            //update the points label to say " x/y points"
            String temp;
            if (total % 1 == 0) { //avoid showing 2.0/2 (make it 2/2 instead)
                temp = (int) total + "/" + question.getPointsAndQuestionIndex();
            } else { //but do show things like: 1.5/2
                temp = total + "/" + question.getPointsAndQuestionIndex();
            }
            pointLabels.get(pointsLabelIndex).setText(temp);
            question.setPointsAndQuestionIndex(temp); //update examQuestion so the grading change is reflected when viewing history

            studentPoints += total;
            totalPoints += question.getLevel();
            pointsLabelIndex++;
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

    public ExamQuestion getExamQuestion(int index) {
        return  questions.get(index);
    }

    public void addPointLabel(Label label) {
        pointLabels.add(label);
    }
}
