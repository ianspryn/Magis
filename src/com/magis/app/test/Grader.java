package com.magis.app.test;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Grader {

    private HashMap<Integer, String> studentAnswers;
    private HashMap<Integer, String> correctAnswers;
    private int numCorrectAnswers;
    private int numQuestions;

    public Grader(int numQuestions) {
        this.studentAnswers = new HashMap<>();
        this.correctAnswers = new HashMap<>();
        this.numQuestions = 0;
        this.numQuestions = numQuestions;
    }

    public void addStudentAnswer(int key, String answer) {
        studentAnswers.put(key, answer);
    }

    public void addCorrectAnswer(int key, String answer) {
        correctAnswers.put(key, answer);
    }

    public String getCorrectAnswer(int key) {
        return correctAnswers.get(key);
    }
    
    public void grade() {
        for (Map.Entry<Integer, String> student : studentAnswers.entrySet()) {
            //if the student's answer matches the correct answer
            if (student.getValue().equals(correctAnswers.get(student.getKey()))) {
                numCorrectAnswers++;
            }
        }
    }
    
    public Double getGrade() {
        Double grade = 100.0 * (double) numCorrectAnswers / (double) numQuestions;
        return Double.parseDouble(new DecimalFormat("#.##").format(grade));
    }

}
