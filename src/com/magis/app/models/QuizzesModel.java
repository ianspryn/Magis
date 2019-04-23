package com.magis.app.models;

public class QuizzesModel extends ExamsModel {

    public QuizzesModel() {
        super("quizzes");
    }

    public boolean hasQuiz(String chapterName) {
        return chapters.containsKey(chapterName);
    }
}
