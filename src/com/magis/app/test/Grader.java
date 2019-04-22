package com.magis.app.test;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grader {

    private HashMap<Integer, ArrayList<String>> studentAnswers;
    private HashMap<Integer, ArrayList<String>> correctAnswers;
    private double numCorrectAnswers; //double because you might only get 0.5 points on a question with multiple correct answers
    private int numQuestions;
    private double grade;

    public Grader(int numQuestions) {
        studentAnswers = new HashMap<>();
        correctAnswers = new HashMap<>();
        numCorrectAnswers = 0;
        this.numQuestions = numQuestions;
    }

    public Integer getNumCorrectAnswer(int key) {
        return correctAnswers.get(key).size();
    }

    public void addStudentAnswer(int key, String answer) {
        if (!studentAnswers.containsKey(key)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(answer);
            studentAnswers.put(key, temp);
        } else {
            studentAnswers.get(key).add(answer);
//            studentAnswers.put(key, temp);
        }
    }

    public void removeStudentAnswer(int key, String answer) {
        studentAnswers.get(key).remove(answer);
    }

    public void addCorrectAnswer(int key, ArrayList<String> answer) {
        correctAnswers.put(key, answer);
    }

    public ArrayList<String> getCorrectAnswer(int key) {
        return correctAnswers.get(key);
    }
    
    public void grade() {
        for (Map.Entry<Integer, ArrayList<String>> student : studentAnswers.entrySet()) {

            ArrayList<String> correctAnswer = correctAnswers.get(student.getKey());
            ArrayList<String> studentAnswer = student.getValue();
            int counter = 0;
            for (String string : correctAnswer) {
                if (studentAnswer.contains(string)) {
                    counter++;
                }
            }
            numCorrectAnswers += (double) counter / (double) correctAnswer.size();
            //if the student's answer matches the correct answer
//            if (student.getValue().equals(correctAnswers.get(student.getKey()))) {
//                numCorrectAnswers++;
//            }
        }
        grade = 100.0 * numCorrectAnswers / (double) numQuestions;
        grade = Double.parseDouble(new DecimalFormat("#.##").format(grade));
    }
    
    public Double getGrade() {
        return grade;
    }

    public boolean contains(int index, String text) {
        return correctAnswers.get(index).contains(text);
    }
}
