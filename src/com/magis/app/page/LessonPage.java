package com.magis.app.page;

import com.magis.app.Main;

public class LessonPage extends Page {

    private PageSidePanel pageSidePanel;
    private PageContent pageContent;

    public LessonPage(int chapterIndex) {
        super(new LessonSidePanel(chapterIndex), new LessonPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        getPageSidePanel().setPageContainer(this);
        pageSidePanel = super.getPageSidePanel();
        pageContent = super.getPageContent();
        updatePage(0);
    }

    public LessonPage(int chapterIndex, int page) {
        super(new LessonSidePanel(chapterIndex), new LessonPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        this.pageSidePanel = super.getPageSidePanel();
        this.pageContent = super.getPageContent();
        updatePage(page);
    }

    @Override
    void updatePage(int pageIndex) {
        setCurrentPage(pageIndex);
        pageSidePanel.update(pageIndex);
        pageContent.update(pageIndex);
        updateNavigation(pageIndex);
        pageContent.getScrollPane().setVvalue(0);
    }

}
