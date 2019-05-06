package com.magis.app.models;

import com.magis.app.Main;

public class QuizzesModel extends ExamsModel {

    public QuizzesModel() {
        super("quizzes");
    }

    public boolean hasQuiz(String chapterName) {
        return chapters.containsKey(chapterName) || Main.questionGenerator.containsKey(Main.lessonModel.getChapterIndex(chapterName));
    }
}
