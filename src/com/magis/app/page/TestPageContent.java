package com.magis.app.page;

import com.magis.app.Main;

public class TestPageContent extends ExamPageContent {
    public TestPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerTest.get(Main.lessonModel.getChapter(chapterIndex).getTitle()), Main.testsModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()), "TEST");
        examSaver.setType("test");
    }
}
