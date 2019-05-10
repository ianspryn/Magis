package com.magis.app.models;

import com.magis.app.Main;

public class QuizzesModel extends ExamsModel {

    public QuizzesModel() {
        super("quizzes");
    }

    /**
     * Checks if there is a quiz associated with this chapter
     * @param chapterName the name of the chapter
     * @return true if there is a quiz, false otherwise
     */
    public boolean hasQuiz(String chapterName) {
        return chapters.containsKey(chapterName) || Main.questionGenerator.containsKey(Main.lessonModel.getChapterIndex(chapterName));
    }
}
