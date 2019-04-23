package com.magis.app.page;

import com.magis.app.Main;

public class TestPage extends ExamPage {
    public TestPage(int chapterIndex) {
        super(chapterIndex, new TestSidePanel(chapterIndex), new com.magis.app.page.TestPageContent(chapterIndex), Main.numQuestionsPerTest, Main.lessonModel.getChapter(chapterIndex).getTitle());
        pageSidePanel.setPageClass(this);
    }

}
