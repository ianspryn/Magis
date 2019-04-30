package com.magis.app.page;

import com.magis.app.Configure;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.test.TestResult;

import static com.magis.app.Configure.*;

public class TestPage extends ExamPage {
    public TestPage(int chapterIndex) {
        super(chapterIndex, new TestSidePanel(chapterIndex), new com.magis.app.page.TestPageContent(chapterIndex), Main.numQuestionsPerTest.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_TEST_QUESTIONS), Main.lessonModel.getChapter(chapterIndex).getTitle());
        pageSidePanel.setPageClass(this);

        submitButton.setOnMouseClicked(e -> {
            if (UIComponents.confirmMessage("Submit test", "Do you wish to submit your test?")) {
                examPageContent.grader.grade();
                Main.studentModel.getStudent().getTest(chapterIndex).addScore(examPageContent.grader.getGrade());
                TestResult testResultPage = new TestResult(examPageContent.grader.getGrade());
                testResultPage.getButton().setOnAction(e2 -> updatePage(1));
                addPage(0, "Results", testResultPage.getvBox());
                examPageContent.disableInput();
                examPageContent.colorize();
                getExamPageContent().getExamSaver().save();
                Main.takingTest = false;
                submitButton.setVisible(false);
                updatePage(0);
            }
        });
    }

}
