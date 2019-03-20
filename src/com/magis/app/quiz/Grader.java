package com.magis.app.quiz;

import java.util.HashMap;
import java.util.Map;

public class Grader {

    private HashMap<Integer, String> studentAnswers;
    private HashMap<Integer, String> correctAnswers;
    private Double grade;
    private int numCorrectAnswers;
    private int numTotalQuestions;

    public Grader() {
        this.studentAnswers = new HashMap<>();
        this.correctAnswers = new HashMap<>();
        this.numTotalQuestions = 0;
        this.numTotalQuestions = correctAnswers.size();
    }

    public void addStudentAnswer(int key, String answer) {
        studentAnswers.put(key, answer);
    }

    public void addCorrectAnswer(int key, String answer) {
        correctAnswers.put(key, answer);
    }
    
    private void grade() {
        for (Map.Entry<Integer, String> student : studentAnswers.entrySet()) {
            //if the student's answer matches the correct answer
            if (student.getValue().equals(correctAnswers.get(student.getKey()))) {
                numCorrectAnswers++;
            }
        }
    }
    
    public Double getGrade() {
        grade();
        return (double) numCorrectAnswers / (double) numTotalQuestions;
    }

}
