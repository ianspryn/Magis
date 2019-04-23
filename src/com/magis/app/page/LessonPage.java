package com.magis.app.page;

import com.magis.app.Main;

public class LessonPage extends Page {


    public LessonPage(int chapterIndex) {
        super(new LessonSidePanel(chapterIndex), new LessonPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        pageSidePanel.setPageClass(this);
        setNumPages(chapterIndex);
        updatePage(0);
    }

    public LessonPage(int chapterIndex, int page) {
        super(new LessonSidePanel(chapterIndex), new LessonPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        pageSidePanel.setPageClass(this);
        setNumPages(chapterIndex);
        updatePage(page);
    }

    private void setNumPages(int chapterIndex) {
        String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
        int hasQuiz = Main.quizzesModel.hasQuiz(chapterTitle) ? 1 : 0;
        int hasTest = Main.testsModel.hasTest(chapterTitle) ? 1 : 0;
        int numLessonPages = Main.lessonModel.getChapter(chapterIndex).getNumPages();
        numPages = numLessonPages + hasQuiz + hasTest;
    }

    @Override
    void updatePage(int pageIndex) {
        currentPage = pageIndex;
        pageSidePanel.update(pageIndex);
        pageContent.update(pageIndex);
        updateNavigation(pageIndex);
        pageContent.getScrollPane().setVvalue(0);
        pageContent.getScrollPane().setHvalue(0);
    }

}
