package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.UI.TestPageContent;

public class TestPage extends Page {

    private PageSidePanel pageSidePanel;
    private PageContent pageContent;

    public TestPage(int chapterIndex) {
        super(new TestSidePanel(chapterIndex), new com.magis.app.page.TestPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        getPageSidePanel().setPageContainer(this);
        pageSidePanel = super.getPageSidePanel();
        pageContent = super.getPageContent();
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
