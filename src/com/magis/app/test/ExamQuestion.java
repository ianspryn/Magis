package com.magis.app.test;

import java.util.ArrayList;

public class ExamQuestion {

    private String question;
    private ArrayList<String> answers;
    private ArrayList<String> correctAnswers;
    private ArrayList<String> studentAnswers;

    public ExamQuestion() {
        answers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers.addAll(answers);
    }

    public void setCorrectAnswer(String correctAnswer) {
        correctAnswers.add(correctAnswer);
    }

    public void setCorrectAnswers(ArrayList<String> correctAnswers) {
        this.correctAnswers.addAll(correctAnswers);
    }

    public void addStudentAnswer(String studentAnswer) {
        studentAnswers.add(studentAnswer);
    }

    public void removeStudentAnswer(String oldAnswer) {
        studentAnswers.remove(oldAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public ArrayList<String> getStudentAnswers() {
        return studentAnswers;
    }
}
