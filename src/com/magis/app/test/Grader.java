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
            String[] correctAnswerComponents = correctAnswer.split("[.]| ");
            String studentAnswer = question.getStudentAnswers().get(answerIndex);
            String[] studentAnswerComponents = studentAnswer.split("[.]| ");
            /*
            divide the points per answer by the number of words or periods the answer has.
            example: int[] myArray = new int[5]
            this is 5 words.
            Now let's say the question has 3 fill-in-the-blanks for a total of 3 points.
            (3 / 3) / 5 = 1/5. Each correct word for this PARTICULAR text field is worth 1/5 points
             */
            //split on spaces and periods
            double pointsPerFillInTheBlank = pointsPerAnswer / correctAnswerComponents.length;
            double pointsLost = 0;
            /*
            for every part the student gets wrong, increase the weight of points deducted
            start at 0.9 so that the first mistake is only scaled as 1.0
             */
            double weight = 0.9;
            int diffIndex = 0;
            LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(correctAnswer, studentAnswer);
            dmp.diff_cleanupSemantic(diff);
            String[] studentWords = studentAnswer.split(" ");
            ArrayList<String> incorrectParts = new ArrayList<>();
            int goodPart = 0;
            int badPart = 0;
            for (int i = 0; i < diff.size(); i++) {
                System.out.println(diff.get(i));
                String type = diff.get(i).operation.toString();
                switch (type) {
                    case "EQUAL":
                        goodPart += diff.get(i).text.length();
                        break;
                    case "INSERT":
//                        /*
//                        If this is true, then we are in a DELETE/INSERT pair, meaning the student
//                        "deleted" a correct part of the answer and "inserted" their own
//                         */
//                       if (i > 0 && diff.get(i - 1).operation.toString().equals("DELETE")) {
//                           incorrectParts.add(diff.get(i).text);
//                           continue;
//                       } else {
//                           /*
//                           Example: If they add an additional number of characters equal to the correct answer's length, their score will be exactly zero.
//                           Another example: If they got everything right, but added a bunch of z's at the end, and the number of z's is equal to half the length of
//                           the correct answer, they will get 1/2 credit
//                            */
//                       }
//                        if (diff.get(i).text.length() == 1) {
//                            pointsLost += (double) diff.get(i).text.length() / (double) correctAnswer.length();
//                        } else {
//                            pointsLost = 1.0 / correctAnswer.length();
//                        }

//                        break;
                    case "DELETE":
                        badPart += diff.get(i).text.length();
//                        /*
//                        If this is true, then we are in a DELETE/INSERT pair, meaning the student
//                        "deleted" a correct part of the answer and "inserted" their own
//                        WE WILL DEAL WITH THE POINTS DEDUCTION IN THE "INSERT" OPERATION
//                         */
//                        if (i < diff.size() && diff.get(i + 1).operation.toString().equals("INSERT")) {
////                            continue;
//                        }
//                        /*
//                       Example: If they subtract a certain number of characters equal to the correct answer's length, their score will be exactly zero.
//                       Another example: If they remove half, they will get 1/2 credit
//                        */
//                        if (diff.get(i).text.length() == 1) {
//                            pointsLost += (diff.get(i).text.length() / (double) correctAnswer.length());
//                        } else {
//                            pointsLost += 1.0 / correctAnswerComponents.length;
//                        }
////                        incorrectParts.add(diff.get(i).text);
//                        /*
//                        If this is true, then we are in a DELETE/INSERT pair, meaning the student
//                        "deleted" a correct part of the answer and "inserted" their own.
//                        WE WILL TAKE CARE OF THE POINTS IN THE "INSERT" SECTION
//                         */
////                        if (i < diff.size() - 1 && diff.get(i + 1).operation.toString().equals("INSERT")) {
////                            incorrectParts.add(diff.get(i).text);
////                        } else {
////
////                        }
                        break;
                }
            }
//            for (String incorrectPart : incorrectParts) {
//                for (String correctAnswerComponent : correctAnswerComponents) {
//                /*
//                if the incprrect part contains a large component of the correct answer
//                ("System" in System.out.println(), "int[]" in int[] myArray ..., etc)
//                then the student performed a larger mistake and must deduct accordingly
//                 */
//                    System.out.println("incorrect part: " + incorrectPart);
//                    System.out.println("correctAnswerComponent: " + correctAnswerComponent);
//                    if (incorrectPart.contains(correctAnswerComponent)) {
//                        //grade less harshly if it's just 1 letter off
//                        if (incorrectPart.length() == 1) {
//                            pointsLost += 1.0 / (correctAnswerComponents.length * 3);
//                        } else {
//                            pointsLost += 1.0 / correctAnswerComponents.length;
//                        }
//                        break;
//                    }
//                }
//            }

//            total += pointsPerAnswer * (1 - Math.min(1, pointsLost));
            total += pointsPerAnswer * Math.max((double) goodPart / (double) correctAnswer.length() - (double) badPart / (double) correctAnswer.length(), 0);

//            for (String studentWord : studentWords) {
//                boolean correctWord = true;
//                StringBuilder diffWord = new StringBuilder();
//                while (!diffWord.toString().equals(studentWord) && diffIndex < diff.size()) {
//                    diffWord.append(diff.get(diffIndex).text);
//                    if (diffWord.toString().endsWith(" ") && diffWord.length() > 1) diffWord = new StringBuilder(diffWord.substring(0, diffWord.length() - 1));
//                    if (diffWord.toString().startsWith(" ") && diffWord.length() > 1) diffWord = new StringBuilder(diffWord.substring(1));
//
//                    diffIndex++;
//                    String type = diff.get(diffIndex).operation.toString();
//                    switch (type) {
////                        case "EQUAL":
////                            //TODO: Finish this
////                            break;
//                        case "INSERT":
//                        case "DELETE":
//                            correctWord = false;
//                            break;
//                    }
//                }
//                if (correctWord) {
//                    total += pointsPerAnswer;
//                } else {
//                    total -= pointsPerAnswer;
//                }
//            }
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
