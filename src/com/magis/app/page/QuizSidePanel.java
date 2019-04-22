package com.magis.app.page;

import com.magis.app.Main;

public class QuizSidePanel extends ExamSidePanel {

    public QuizSidePanel(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerQuiz.get(Main.lessonModel.getChapter(chapterIndex).getTitle()));
    }
}
