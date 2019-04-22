package com.magis.app.page;

import com.magis.app.Main;

import static com.magis.app.Configure.*;

public class TestSidePanel extends ExamSidePanel {
    public TestSidePanel(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerTest.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_TEST_QUESTIONS));
    }
}
