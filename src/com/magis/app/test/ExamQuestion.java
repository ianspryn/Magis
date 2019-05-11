package com.magis.app.test;

import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamQuestion {

    private int level;
    private String pointsAndQuestionIndex;
    private boolean isWritten;
    private String question;
    private ArrayList<String> answers;
    private ArrayList<String> correctAnswers;
    private ArrayList<String> incorrectAnswers;
    private ArrayList<String> studentAnswers;
    private HashMap<Integer, JFXTextField> textFields;
    public ExamQuestion() {
        level = 1;
        isWritten = false; //default to false
        answers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        incorrectAnswers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
        textFields = new HashMap<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPointsAndQuestionIndex() {
        return pointsAndQuestionIndex;
    }

    public void setPointsAndQuestionIndex(String pointsAndQuestionIndex) {
        this.pointsAndQuestionIndex = pointsAndQuestionIndex;
    }

    public boolean isWritten() {
        return isWritten;
    }

    public void setWritten(boolean written) {
        isWritten = written;
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

    public void addStudentAnswer(String studentAnswer) { studentAnswers.add(studentAnswer); }

    public void removeStudentAnswer(String oldAnswer) {
        studentAnswers.remove(oldAnswer);
    }

    public void addTextField(int key, JFXTextField textField) { textFields.put(key, textField); }

    public HashMap<Integer, JFXTextField> getTextFields() { return textFields; }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
            return answers;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public ArrayList<String> getIncorrectAnswers() { return incorrectAnswers; }

    public ArrayList<String> getStudentAnswers() {
        return studentAnswers;
    }

    public Integer getNumCorrectAnswers() { return correctAnswers.size(); }

    public Integer getNumStudentAnswers() { return studentAnswers.size(); }
}
