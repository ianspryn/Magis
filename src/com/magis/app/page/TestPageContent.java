package com.magis.app.page;

import com.magis.app.Main;

import static com.magis.app.Configure.DEFAULT_NUM_TEST_QUESTIONS;

public class TestPageContent extends ExamPageContent {
    public TestPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerTest.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_TEST_QUESTIONS), Main.testsModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
        examSaver.setType("test");
    }

    @Override
    protected boolean buildQuestions() {
        return true;
    }
}
