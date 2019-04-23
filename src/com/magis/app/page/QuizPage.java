package com.magis.app.page;

import com.magis.app.Main;

public class QuizPage extends ExamPage {

    public QuizPage(int chapterIndex) {
        super(chapterIndex, new QuizSidePanel(chapterIndex), new QuizPageContent(chapterIndex), Main.numQuestionsPerQuiz, Main.lessonModel.getChapter(chapterIndex).getTitle());
        pageSidePanel.setPageClass(this);
    }
}
