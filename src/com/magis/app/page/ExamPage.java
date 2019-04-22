package com.magis.app.page;

import com.jfoenix.controls.JFXButton;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.test.TestResult;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamPage extends Page {

    protected ArrayList<Integer> usedBankQuestions;
    protected ArrayList<String> usedGeneratorQuestions;
    protected JFXButton submitButton;
    protected ExamPageContent examPageContent;

    public ExamPage(int chapterIndex, PageSidePanel sidePanel, PageContent pageContent, HashMap<String, Integer> numQuestionsPerExam, String title) {
        super(sidePanel, pageContent, title);
        examPageContent = (ExamPageContent) super.pageContent;
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();

        //Exam submit button
        submitButton = new JFXButton("Submit");
        submitButton.getStyleClass().addAll("submit-test-button", "jfx-button-raised", "jfx-button-raised-color");
        bottomNavigation.setCenter(submitButton);
        submitButton.setOnMouseClicked(e -> {
            if (UIComponents.confirmMessage("Submit Quiz", "Do you wish to submit your exam?")) {
                examPageContent.grader.grade();
                Main.studentModel.getStudent().getQuiz(chapterIndex).addScore(examPageContent.grader.getGrade());
                TestResult testResultPage = new TestResult(Main.lessonModel.getChapter(chapterIndex).getTitle(), "exam", examPageContent.grader.getGrade());
                testResultPage.getButton().setOnAction(e2 -> updatePage(1));
                addPage(0, "Results", testResultPage.getvBox());
                examPageContent.disableInput();
                examPageContent.colorize();
                updatePage(0);
                Main.takingTest = false;
            }
        });

        int numQuestions = numQuestionsPerExam.get(title);
        numPages = (int) Math.ceil((double) numQuestions / 2);
        for (int i = 0; i < numPages; i++) {
            examPageContent.buildPage(i);
        }
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
            submitButton.setVisible(true);
        } else {
            rightButton.setVisible(true);
            submitButton.setVisible(false);
        }
    }

}
