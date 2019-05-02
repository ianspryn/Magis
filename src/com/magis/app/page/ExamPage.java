package com.magis.app.page;

import com.jfoenix.controls.JFXButton;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.test.TestResult;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

import static com.magis.app.Configure.NUM_QUESTIONS_PER_PAGE;

public class ExamPage extends Page {

    protected ArrayList<Integer> usedBankQuestions;
    protected ArrayList<String> usedGeneratorQuestions;
    protected JFXButton submitButton;
    protected ExamPageContent examPageContent;
    protected ExamSidePanel examSidePanel;

    public ExamPage(int chapterIndex, PageSidePanel sidePanel, PageContent pageContent, int numQuestions, String title) {
        super(sidePanel, pageContent, title);
        examSidePanel = (ExamSidePanel) super.pageSidePanel;
        examPageContent = (ExamPageContent) super.pageContent;
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();

        //Exam submit button
        submitButton = new JFXButton("Submit");
        submitButton.getStyleClass().addAll("submit-test-button", "jfx-button-raised", "jfx-button-raised-color");
        bottomNavigation.setCenter(submitButton);

        numPages = (int) Math.ceil((double) numQuestions / NUM_QUESTIONS_PER_PAGE);
        for (int i = 0; i < numPages; i++) {
            if (!examPageContent.buildPage(i)) {
                numPages = i + 1;
                break;
            }
        }
        examSidePanel.buildSidePanel(numPages);
        updatePage(0);
    }

    protected void addPage(int pageIndex, String pageTitle, VBox vBox) {
        examPageContent.pageContents.add(pageIndex, vBox);
        pageSidePanel.pageLabels.add(pageIndex, pageTitle);
        pageSidePanel.contentPagesVBox.getChildren().add(0, pageSidePanel.getPageLabel(pageIndex));
        if (pageSidePanel.currentPageIndex >= pageIndex) pageSidePanel.currentPageIndex++;
        numPages++;

        for (int i = 0; i < numPages; i++) {
            Label label = pageSidePanel.getPageLabel(i);
            int index = i;
            label.setOnMouseClicked(e -> updatePage(index));
        }
    }

    @Override
    void updatePage(int pageIndex) {
        currentPage = pageIndex;
        pageSidePanel.update(pageIndex);
        examPageContent.update(pageIndex);
        updateNavigation(pageIndex);
    }

    public void updateNavigation(int currentPage) {
        if (currentPage == 0) leftButton.setVisible(false);
        else leftButton.setVisible(true);
        if (currentPage == numPages - 1) {
            rightButton.setVisible(false);
            if (Main.takingTest) submitButton.setVisible(true);
        } else {
            rightButton.setVisible(true);
            if (Main.takingTest) submitButton.setVisible(false);
        }
    }

    public ExamPageContent getExamPageContent() {
        return examPageContent;
    }
}
