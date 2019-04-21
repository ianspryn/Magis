package com.magis.app.page;

import com.magis.app.Main;

public class QuizPage extends Page {

    private PageSidePanel pageSidePanel;
    private PageContent pageContent;

    public QuizPage(int chapterIndex) {
        super(new QuizSidePanel(chapterIndex), new QuizPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        getPageSidePanel().setPageContainer(this);
        this.pageSidePanel = super.getPageSidePanel();
        this.pageContent = super.getPageContent();
        updatePage(0);
    }

    @Override
    void updatePage(int pageIndex) {
        setCurrentPage(pageIndex);
        pageSidePanel.update(pageIndex);
        pageContent.update(pageIndex);
        updateNavigation(pageIndex);
    }
}
