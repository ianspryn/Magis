package com.magis.app.test;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.*;

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
        collectTextFields();
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

    private void collectTextFields() {
        for (ExamQuestion question : questions) {
            if (!question.isWritten()) continue;

            HashMap<Integer, JFXTextField> textFields = question.getTextFields();
            for (Map.Entry<Integer, JFXTextField> iter : textFields.entrySet()) {
                question.addStudentAnswer(iter.getValue().getText());
            }
        }
    }

    private void gradeAsWritten(ExamQuestion question) {
        diff_match_patch dmp = new diff_match_patch();
        for (int answerIndex = 0; answerIndex < question.getNumCorrectAnswers(); answerIndex++) {
            //https://regex101.com/r/RpbPGc/3
            String regex = " +|(?<=[.|=|*|+|-|/|\"|\\[|\\]])|(?=[.|=|*|+|-|/|\"|\\[|\\]|;])";
            //for the fairness of grading, remove spaces that are allowed to be removed
            String[] formattedCorrectAnswer = question.getCorrectAnswers().get(answerIndex).split(regex);
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : formattedCorrectAnswer) {
                if (str.length() == 0) continue;
                stringBuilder.append(str);
            }
            String correctAnswer = stringBuilder.toString();
            String[] formattedStudentAnswer = question.getStudentAnswers().get(answerIndex).split(regex);
            stringBuilder = new StringBuilder();
            for (String str : formattedStudentAnswer) {
                if (str.length() == 0) continue;
                stringBuilder.append(str);
            }
            String studentAnswer = stringBuilder.toString();
            //Now that we've cleaned it up, we can get the number of components to the question
            int numParts = correctAnswer.split(regex).length;


            LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(correctAnswer, studentAnswer);
            dmp.diff_cleanupSemantic(diff);
            int badPart = 0;
            for (int i = 0; i < diff.size(); i++) {
                diff_match_patch.Diff diff1 = diff.get(i);
                String type = diff1.operation.toString();
                switch (type) {
                    case "INSERT":
                        /*
                        If this if-statement is true, then this is a part of the exam's code where the student just "deleted"
                        the correct answer and "inserted" the wrong answer. Meaning, they are a pair.
                        We should focus on how much they removed. Consider the folllowing:
                        CORRECT: int[] myVar = new int[5];
                        STUDENT: int[] asdf asdf = new int[5];
                        The student is only deducted for missing "myVar"
                        However, if this were the case:
                        CORRECT: int[] myVar = new int[5];
                        STUDENT: int[] myVar asdf = new int[5];
                        The student would still be deducted for the extra "asdf"
                         */
//                        if (i > 0 && diff.get(i - 1).operation.toString().equals("DELETE")) continue;
                        //grade off-by-one-character less harshly
                        if (diff.get(i).text.length() == 1) {
                            badPart += 1.0 / (numParts * 2);
                        } else {
                            badPart += Math.max(1, diff1.text.split(regex).length);
                        }
                        break;
                    case "DELETE":
                        //grade off-by-one-character less harshly
                        if (diff.get(i).text.length() == 1) {
                            badPart += 1.0 / (numParts * 2);
                        } else {
                            badPart += Math.max(1, diff1.text.split(regex).length);
                        }
                        break;
                }
            }
            total += pointsPerAnswer * Math.max(0, 1.0 - ((double) badPart / (double) numParts));
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

    public ArrayList<ExamQuestion> getQuestions() {
        return questions;
    }

    public void addPointLabel(Label label) {
        pointLabels.add(label);
    }
}
