package com.magis.app.test;

import com.magis.app.Main;

import java.util.ArrayList;

public class ExamSaver {

    private int chapterIndex;
    private String type;
    private ArrayList<ExamQuestion> examQuestions;

    public ExamSaver(int chapterIndex) {
        this.chapterIndex = chapterIndex;
        examQuestions = new ArrayList<>();
    }

    public void add(ExamQuestion examQuestion) {
        examQuestions.add(examQuestion);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void save() {
        switch (type) {
            case "quiz":
                Main.studentModel.getStudent().saveQuiz(this);
                break;
            case "test":
                Main.studentModel.getStudent().saveTest(this);
                break;
        }
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public ArrayList<ExamQuestion> getExamQuestions() {
        return examQuestions;
    }
}
