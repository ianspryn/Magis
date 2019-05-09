package com.magis.app.test;

import com.magis.app.login.Password;
import com.magis.app.models.StudentModel;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Grader {

    private ArrayList<ExamQuestion> questions;
    private ArrayList<Label> pointLabels;
    private double studentPoints; //double because you might only get 0.5 points on a question with multiple correct answers
    private double totalPoints;
    private double grade;
    private static double pointsPerAnswer;
    private static double total;

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
            pointLabels.get(pointsLabelIndex).setText(temp);
            question.setPointsAndQuestionIndex(temp); //update examQuestion so the grading change is reflected when viewing history

            studentPoints += total;
            totalPoints += question.getLevel();
            pointsLabelIndex++;
        }
        grade = 100.0 * studentPoints / totalPoints;
        grade = Double.parseDouble(new DecimalFormat("#.##").format(grade));
    }

    private void gradeAsWritten(ExamQuestion question) {
        ArrayList<String> studentAnswers = question.getStudentAnswers();
        diff_match_patch dmp = new diff_match_patch();
        for (int stringIndex = 0; stringIndex < studentAnswers.size(); stringIndex++) {
            String studentAnswer = studentAnswers.get(stringIndex);
            /*
            divide the points per answer by the number of words the answer has.
            example: int[] myArray = new int[5]
            this is 5 words.
            Now let's say the question has 3 fill-in-the-blanks for a total of 3 points.
            (3 / 3) / 5 = 1/5. Each correct word for this PARTICULAR text field is worth 1/5 points
             */
            pointsPerAnswer /= studentAnswer.split(" ").length;
            String word = studentAnswer.split(" ")[stringIndex];
            String correctAnswer = question.getCorrectAnswers().get(stringIndex);
            LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(studentAnswer, correctAnswer);
            dmp.diff_cleanupSemantic(diff);

            String diffWord = "";
            boolean correctWord = true;
            for (int i = 0; i < diff.size(); i++) {
                String type = diff.get(i).operation.toString();
                switch (type) {
                    case "EQUAL":
                        //TODO: Finish this
                        break;
                    case "INSERT":
                    case "DELETE":
                        correctWord = false;
                        break;
                }
                if (diffWord.equals(word)) {
                    if (!correctWord) total -= pointsPerAnswer;
                }
            }
//            for (String part : parts) {
//                String type = part.split(",")[0];
//                switch (type) {
//                    case "EQUAL":
//                        break;
//                    case "INSERT":
//                        break;
//                    case "DELETE":
//                        break;
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
