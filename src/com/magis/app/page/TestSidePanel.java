package com.magis.app.page;

import com.magis.app.Main;

public class TestSidePanel extends ExamSidePanel {
    public TestSidePanel(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerTest.get(Main.lessonModel.getChapter(chapterIndex).getTitle()));
    }
}
