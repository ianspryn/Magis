package com.magis.app.page;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.models.StudentModel;
import com.magis.app.test.ExamQuestion;
import com.magis.app.test.diff_match_patch;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;

import static com.magis.app.Configure.NUM_QUESTIONS_PER_PAGE;
import static com.magis.app.home.StatsPage.goToChapterInsights;

public class HistoryExamPage {

    private VBox masterVBox;
    private StudentModel.Student.Attempt attempt;
    private int chapterIndex;
    public HistoryExamPage(int chapterIndex, int index, String type) {
        this.chapterIndex = chapterIndex;
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
        pointsAndQuestionIndex.setPadding(new Insets(0,0,-10,-10));
        pointsAndQuestionIndex.getStyleClass().addAll("lesson-header-three-text", "text-color");
        pointsAndQuestionIndex.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it
        questionBox.getChildren().add(pointsAndQuestionIndex);

        ExamPageContent.buildStatement(questionBox, examQuestion, chapterIndex);

        //replace the content by marking the student's answer as correct/incorrect
        if (examQuestion.isWritten()) {
            for (int answerIndex = 0; answerIndex < examQuestion.getNumCorrectAnswers(); answerIndex++) {
                TextFlow answerContainer = new TextFlow();
                ExamPageContent.replaceTextFieldWithNode(questionBox, answerContainer);
                ExamPageContent.applyDiffing(examQuestion, answerContainer, answerIndex);
            }

            /*
            We will now build the second box that contains the actual correct answer
             */
            VBox answerBox = new VBox();
//            ExamPageContent.buildWrittenAnswerStatement(answerBox, examQuestion, chapterIndex); //create it again

            //fill the textFlow with text of the correct answer (it will all be green as a result)
            if (Arrays.asList(ExamPageContent.splitQuestion(examQuestion.getQuestion())).contains("###")) {
                ExamPageContent.buildWithTextFlow(answerBox, examQuestion, chapterIndex, true);
            }
            //else it's a fill-in-the-blank at the end and we are guaranteed it's only one fill-in-the-blank
            else {
                HBox container = new HBox();
                container.getStyleClass().add("code-text");
                container.setMaxWidth(HBox.USE_PREF_SIZE);

                TextFlow textFlow = new TextFlow();
                textFlow.setStyle("-fx-background-color: #00C853"); //Green A700
                Text text = new Text(examQuestion.getCorrectAnswers().get(0));
                text.setStyle("-fx-fill: #004c05"); //Dark Green
                textFlow.getChildren().add(text);
                //Constrain it to the size of the text inside of it
                textFlow.setMaxHeight(TextFlow.USE_PREF_SIZE);
                textFlow.setMaxWidth(TextFlow.USE_PREF_SIZE);
                container.getChildren().add(textFlow);
                answerBox.getChildren().add(container);
            }



            Label correctAnswerText = new Label("Correct Answer");
            correctAnswerText.setPadding(new Insets(40,0,0,20));
            correctAnswerText.getStyleClass().add("lesson-header-three-text");
            correctAnswerText.setStyle("-fx-text-fill: #00C853;");

            questionBox.getChildren().addAll(correctAnswerText, answerBox);
        }
        else if (examQuestion.getCorrectAnswers().size() == 1) {
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
                    radioButton.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a; -jfx-unselected-color: #00cd0a;");
                    radioButton.setUnderline(true);
                }

                //if this was a selected answer, select the button
                if (examQuestion.getStudentAnswers().contains(answer)) {
                    radioButton.setSelected(true);
                }

                //if this is an incorrect answer that the student selected, mark it as red
                if (examQuestion.getStudentAnswers().contains(answer) && !examQuestion.getCorrectAnswers().contains(answer)) {
                    radioButton.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336; -jfx-unselected-color: #f44336;");
                }

                questionBox.getChildren().addAll(radioButton);
            }
        } else {
            for (String answer : examQuestion.getAnswers()) {
                JFXCheckBox checkBox = new JFXCheckBox();
                checkBox.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                checkBox.getStyleClass().add("exam-checkbox-button");
                checkBox.setText(answer);
                checkBox.setDisable(true);

                //if this is a correct answer, mark it as green
                if (examQuestion.getCorrectAnswers().contains(answer)) {
                    checkBox.setStyle("-fx-text-fill: #00cd0a;");
                    checkBox.setCheckedColor(Color.valueOf("#00cd0a"));
                    checkBox.setUnCheckedColor(Color.valueOf("#00cd0a"));
                    checkBox.setSelected(!checkBox.isSelected());
                    checkBox.setSelected(!checkBox.isSelected());
                    checkBox.setUnderline(true);
                }

                //if this was a selected answer, select the button
                if (examQuestion.getStudentAnswers().contains(answer)) {
                    checkBox.setSelected(true);
                }

                //if this is an incorrect answer that the student selected, mark it as red
                if (!examQuestion.getCorrectAnswers().contains(answer)) {
                    checkBox.setStyle("-fx-text-fill: #f44336;");
                    checkBox.setCheckedColor(Color.valueOf("#f44336"));
                    checkBox.setSelected(!checkBox.isSelected());
                    checkBox.setSelected(!checkBox.isSelected());
                }
                questionBox.getChildren().add(checkBox);
            }
        }
        return questionBox;
    }


}
