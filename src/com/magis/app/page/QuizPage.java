package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.test.Grader;

import java.util.ArrayList;

public class QuizPage extends Page {

    private PageSidePanel pageSidePanel;
    private QuizPageContent pageContent;
    private int numQuestions;
    private int numPages;
    private Grader grader;
    private ArrayList<Integer> usedBankQuestions;
    private ArrayList<String> usedGeneratorQuestions;

    public QuizPage(int chapterIndex) {
        super(new QuizSidePanel(chapterIndex, (int) Math.ceil((double) 7 / 2)), new QuizPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        getPageSidePanel().setPageContainer(this);
        pageSidePanel = super.getPageSidePanel();
        pageContent = (QuizPageContent) super.getPageContent();

        numQuestions = 7;
        numPages = (int) Math.ceil((double) numQuestions / 2);

        grader = new Grader(numQuestions);
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();

        pageContent.setNumQuestions(numQuestions);
        pageContent.setGrader(grader);
        pageContent.setUsedBankQuestions(usedBankQuestions);
        pageContent.setUsedGeneratorQuestions(usedGeneratorQuestions);

        for (int i = 0; i < pageSidePanel.getNumPages(); i++) {
            pageContent.buildPage(i);
        }

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
