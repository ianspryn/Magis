package com.magis.app.page;

import com.magis.app.Configure;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.test.TestResult;

import static com.magis.app.Configure.*;

public class QuizPage extends ExamPage {

    public QuizPage(int chapterIndex) {
        super(chapterIndex, new QuizSidePanel(chapterIndex), new QuizPageContent(chapterIndex), Main.numQuestionsPerQuiz.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_QUIZ_QUESTIONS), Main.lessonModel.getChapter(chapterIndex).getTitle());
        pageSidePanel.setPageClass(this);

        submitButton.setOnMouseClicked(e -> {
            if (UIComponents.confirmMessage("Submit quiz", "Do you wish to submit your quiz?")) {
                examPageContent.grader.grade();
                Main.studentModel.getStudent().getQuiz(chapterIndex).addScore(examPageContent.grader.getGrade());
                TestResult testResultPage = new TestResult(examPageContent.grader.getGrade());
                testResultPage.getButton().setOnAction(e2 -> updatePage(1));
                addPage(0, "Results", testResultPage.getvBox());
                examPageContent.disableInput();
                examPageContent.colorize();
                updatePage(0);
                Main.takingTest = false;
                submitButton.setVisible(false);
            }
        });
    }
}
