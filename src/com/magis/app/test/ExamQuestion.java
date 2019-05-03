package com.magis.app.test;

import java.util.AbstractList;
import java.util.ArrayList;

public class ExamQuestion {

    private int level;
    private String pointsStatement;
    private String question;
    private ArrayList<String> answers;
    private ArrayList<String> correctAnswers;
    private ArrayList<String> incorrectAnswers;
    private ArrayList<String> studentAnswers;

    public ExamQuestion() {
        level = 1;
        answers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        incorrectAnswers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPointsStatement() {
        return pointsStatement;
    }

    public void setPointsStatement(String pointsStatement) {
        this.pointsStatement = pointsStatement;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void addAnswers(ArrayList<String> answers) {
        this.answers.addAll(answers);
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }

    public void addCorrectAnswer(String correctAnswer) {
        correctAnswers.add(correctAnswer);
    }

    public void addCorrectAnswers(ArrayList<String> correctAnswers) {
        this.correctAnswers.addAll(correctAnswers);
    }

    public void addIncorrectAnswers(ArrayList<String> incorrectAnswers) { this.incorrectAnswers.addAll(incorrectAnswers); }

    public ArrayList<String> getIncorrectAnswers() { return incorrectAnswers; }

    public void addStudentAnswer(String studentAnswer) { studentAnswers.add(studentAnswer); }

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

    public Integer getNumCorrectAnswers() { return correctAnswers.size(); }

    public Integer getNumStudentAnswers() { return studentAnswers.size(); }
}
