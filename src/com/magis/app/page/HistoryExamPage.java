package com.magis.app.page;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.models.StudentModel;
import com.magis.app.test.ExamQuestion;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import static com.magis.app.home.StatsPage.goToChapterInsights;

public class HistoryExamPage {

    private VBox masterVBox;
    private StudentModel.Student.Attempt attempt;

    public HistoryExamPage(int chapterIndex, int index, String type) {

        UIComponents.GenericPage page = new UIComponents.GenericPage();
        page.getBackButton().setOnMouseClicked(e -> goToChapterInsights(page.getMastervBox(), chapterIndex));
        page.getPageTitle().setText(Main.lessonModel.getChapter(chapterIndex).getTitle());
        masterVBox = page.getMastervBox();

        switch (type) {
            case "quiz":
                attempt = Main.studentModel.getStudent().getQuiz(chapterIndex).getAttempt(index);
                break;
            case "test":
                attempt = Main.studentModel.getStudent().getTest(chapterIndex).getAttempt(index);
                break;
        }
        buildPage();

        Main.setScene(page.getMaster());
    }

    private void buildPage() {
        for (ExamQuestion examQuestion : attempt.getExamQuestions()) {
            VBox questionBox = rebuildQuestion(examQuestion);
            masterVBox.getChildren().add(questionBox);
        }
    }

    private VBox rebuildQuestion(ExamQuestion examQuestion) {
        VBox questionBox = new VBox();
        questionBox.setSpacing(15);
        questionBox.setPadding(new Insets(20,0,20,20));

        Label pointsAndQuestionIndex = new Label(examQuestion.getPointsAndQuestionIndex());
        pointsAndQuestionIndex.setPadding(new Insets(0,0,-10,0));
        pointsAndQuestionIndex.getStyleClass().addAll("lesson-text-small", "text-color");
        questionBox.getChildren().add(pointsAndQuestionIndex);

        Label statement = new Label(examQuestion.getQuestion());
        statement.setWrapText(true);
        statement.setPrefWidth(700);
        questionBox.getChildren().add(statement);

        if (examQuestion.getCorrectAnswers().size() == 1) {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (String answer : examQuestion.getAnswers()) {
                JFXRadioButton radioButton = new JFXRadioButton();
                radioButton.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                radioButton.getStyleClass().addAll("exam-radio-button", "jfx-radio-button");
                radioButton.setText(answer);
                radioButton.setToggleGroup(toggleGroup);
                radioButton.setDisable(true);

                //if this is a correct answer, mark it as green
                if (examQuestion.getCorrectAnswers().contains(answer)) {
                    radioButton.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a;");
                    radioButton.setUnderline(true);
                }

                //if this was a selected answer, select the button
                if (examQuestion.getStudentAnswers().contains(answer)) {
                    radioButton.setSelected(true);
                }

                //if this is an incorrect answer that the student selected, mark it as red
                if (examQuestion.getStudentAnswers().contains(answer) && !examQuestion.getCorrectAnswers().contains(answer)) {
                    radioButton.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336;");
                }

                questionBox.getChildren().addAll(radioButton);
            }
        } else {
            for (String answer : examQuestion.getAnswers()) {
                JFXCheckBox checkBoxButton = new JFXCheckBox();
                checkBoxButton.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                checkBoxButton.getStyleClass().add("test-checkbox-button");
                checkBoxButton.setText(answer);
                checkBoxButton.setDisable(true);

                //if this is a correct answer, mark it as green
                if (examQuestion.getCorrectAnswers().contains(answer)) {
                    checkBoxButton.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a;");
                    checkBoxButton.setUnderline(true);
                }

                //if this was a selected answer, select the button
                if (examQuestion.getStudentAnswers().contains(answer)) {
                    checkBoxButton.setSelected(true);
                }

                //if this is an incorrect answer that the student selected, mark it as red
                if (examQuestion.getStudentAnswers().contains(answer) && !examQuestion.getCorrectAnswers().contains(answer)) {
                    checkBoxButton.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336;");
                }
                questionBox.getChildren().add(checkBoxButton);
            }
        }
        return questionBox;
    }


}
