package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.test.Grader;

import java.util.ArrayList;

public class QuizPage extends Page {

    private PageSidePanel pageSidePanel;
    private QuizPageContent pageContent;
    private int numQuestions;
    private Grader grader;
    private ArrayList<Integer> usedBankQuestions;
    private ArrayList<String> usedGeneratorQuestions;

    public QuizPage(int chapterIndex) {
        super(new QuizSidePanel(chapterIndex), new QuizPageContent(chapterIndex), Main.lessonModel.getChapter(chapterIndex).getTitle());
        getPageSidePanel().setPageContainer(this);
        pageSidePanel = super.getPageSidePanel();
        pageContent = (QuizPageContent) super.getPageContent();

        numQuestions = 7;
        grader = new Grader(numQuestions);
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();

        pageContent.setNumQuestions(numQuestions);
        pageContent.setGrader(grader);
        pageContent.setUsedBankQuestions(usedBankQuestions);
        pageContent.setUsedGeneratorQuestions(usedGeneratorQuestions);

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
