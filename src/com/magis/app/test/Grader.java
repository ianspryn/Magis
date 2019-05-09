package com.magis.app.test;

import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Grader {

    private ArrayList<ExamQuestion> questions;
    private ArrayList<Label> pointLabels;
    private double totalStudentOverall; //double because you might only get 0.5 points on a question with multiple correct answers
    private double totalPoints;
    private double grade;
    private static double pointsPerAnswer;
    private static double total;

    public Grader() {
        questions = new ArrayList<>();
        pointLabels = new ArrayList<>();
        totalStudentOverall = 0;
        totalPoints = 0;
    }

    public int getNumCorrectAnswers(int key) {
        return questions.get(key).getCorrectAnswers().size();
    }

    public void grade() {
        int pointsLabelIndex = 0;
        for (ExamQuestion question : questions) {
            pointsPerAnswer = (double) question.getLevel() / question.getNumCorrectAnswers();
            total = 0;
            if (question.isWritten()) {
                gradeAsWritten(question);
            } else {
                for (String studentAnswer : question.getStudentAnswers()) {
                    if (question.getCorrectAnswers().contains(studentAnswer)) {
                        total += pointsPerAnswer;
                    }
                    if (question.getIncorrectAnswers().contains(studentAnswer)) {
                        total -= pointsPerAnswer;
                    }
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
            if (pointLabels.size() > pointsLabelIndex) pointLabels.get(pointsLabelIndex).setText(temp);
            else System.err.println("Error. Missing pointsAndQuestionIndex label for question \"" + question.getQuestion() + "\" to update the score");
            question.setPointsAndQuestionIndex(temp); //update examQuestion so the grading change is reflected when viewing history

            totalStudentOverall += total;
            totalPoints += question.getLevel();
            pointsLabelIndex++;
        }
        grade = 100.0 * totalStudentOverall / totalPoints;
        grade = Double.parseDouble(new DecimalFormat("#.##").format(grade));
    }

    private void gradeAsWritten(ExamQuestion question) {
        diff_match_patch dmp = new diff_match_patch();
        for (int answerIndex = 0; answerIndex < question.getNumCorrectAnswers(); answerIndex++) {
            String correctAnswer = question.getCorrectAnswers().get(answerIndex);
            String studentAnswer = question.getStudentAnswers().get(answerIndex);

            LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(correctAnswer, studentAnswer);
            dmp.diff_cleanupSemantic(diff);

            int goodPart = 0;
            int badPart = 0;
            for (diff_match_patch.Diff diff1 : diff) {
                System.out.println(diff1);
                String type = diff1.operation.toString();
                switch (type) {
                    case "EQUAL":
                        goodPart += diff1.text.length();
                        break;
                    case "INSERT":
                    case "DELETE":
                        badPart += diff1.text.length();
                        break;
                }
            }
            total += pointsPerAnswer * Math.max((double) goodPart / (double) correctAnswer.length() - (double) badPart / (double) correctAnswer.length(), 0);
        }
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
