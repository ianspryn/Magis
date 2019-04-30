package com.magis.app.test;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grader {

//    private HashMap<Integer, ExamQuestion> questions;
    private ArrayList<ExamQuestion> questions;
//    private HashMap<Integer, ArrayList<String>> studentAnswers;
//    private HashMap<Integer, ArrayList<String>> correctAnswers;
    private double studentScore; //double because you might only get 0.5 points on a question with multiple correct answers
    private int numQuestions;
    private double grade;

    public Grader(int numQuestions) {
        questions = new ArrayList<>();
//        questions = new HashMap<>();
//        studentAnswers = new HashMap<>();
//        correctAnswers = new HashMap<>();
        studentScore = 0;
        this.numQuestions = numQuestions;
    }

    public int getNumCorrectAnswers(int key) {
        return questions.get(key).getCorrectAnswers().size();
    }

//    public Integer getNumCorrectAnswers(int key) {
//        return correctAnswers.get(key).size();
//    }

//    public void addStudentAnswer(int key, String answer) {
//        if (!studentAnswers.containsKey(key)) {
//            ArrayList<String> temp = new ArrayList<>();
//            temp.add(answer);
//            studentAnswers.put(key, temp);
//        } else {
//            studentAnswers.get(key).add(answer);
////            studentAnswers.put(key, temp);
//        }
//    }

//    public void removeStudentAnswer(int key, String answer) {
//        studentAnswers.get(key).remove(answer);
//    }

//    public void addCorrectAnswer(int key, ArrayList<String> answer) {
//        correctAnswers.put(key, answer);
//    }

    public void grade() {
        for (Map.Entry<Integer, ExamQuestion> questions : questions.entrySet()) {
            ExamQuestion question = questions.getValue();
            System.out.println(question.getStudentAnswers());
            int counter = 0;
            for (String studentAnswer : question.getStudentAnswers()) {
                if (question.getCorrectAnswers().contains(studentAnswer)) {
                    counter++;
                }
            }
            studentScore += ((double) counter / (double) question.getCorrectAnswers().size()) * (double) question.getLevel();
//            System.out.println(studentScore);
        }
        grade = 100.0 * studentScore / (double) numQuestions;
//        System.out.println(grade);
        grade = Double.parseDouble(new DecimalFormat("#.##").format(grade));
//        System.out.println(grade);
    }
    
//    public void grade() {
//        for (Map.Entry<Integer, ArrayList<String>> student : studentAnswers.entrySet()) {
//
//            ArrayList<String> correctAnswer = correctAnswers.get(student.getKey());
//            ArrayList<String> studentAnswer = student.getValue();
//            int counter = 0;
//            for (String string : correctAnswer) {
//                if (studentAnswer.contains(string)) {
//                    counter++;
//                }
//            }
//            studentScore += (double) counter / (double) correctAnswer.size();
//        }
//        grade = 100.0 * studentScore / (double) numQuestions;
//        grade = Double.parseDouble(new DecimalFormat("#.##").format(grade));
//    }
    
    public Double getGrade() {
        return grade;
    }

//    public boolean isCorrect(int index, String text) {
//        return correctAnswers.get(index).isCorrect(text);
//    }

    public boolean isCorrect(int key, String text) {
        return questions.get(key).getCorrectAnswers().contains(text);
    }

    public void addQuestion(int questionIndex, ExamQuestion examQuestion) {
        questions.put(questionIndex, examQuestion);
    }
}
