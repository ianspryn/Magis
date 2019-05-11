package com.magis.app.UI;

import com.jfoenix.controls.JFXButton;
import com.magis.app.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class PageContentContainer {
    private VBox masterContent;
    private VBox titleContent;
    private VBox pageContent;

    private int chapterIndex;

    public PageContentContainer(int chapterIndex) {
        masterContent = new VBox();
        masterContent.getStyleClass().add("lesson-page-content");
        masterContent.setAlignment(Pos.TOP_CENTER);

        titleContent = new VBox();
        titleContent.setAlignment(Pos.CENTER);
        titleContent.setPadding(new Insets(30,0,10,0));

        pageContent = new VBox();
        pageContent.setAlignment(Pos.TOP_LEFT);
        pageContent.setPadding(new Insets(0,0,0,20));

        this.chapterIndex = chapterIndex;
    }

    public  VBox getMasterContent() {
        return masterContent;
    }

    public void add (Node node) {
        pageContent.getChildren().add(node);
    }

    public void buildAsLessonPage(int pageIndex) {
        Text pageTitle = new Text(Main.lessonModel.getChapter(chapterIndex).getPage(pageIndex).getTitle());
        pageTitle.getStyleClass().add("page-box-title");
        titleContent.getChildren().add(pageTitle);

        masterContent.getChildren().addAll(titleContent, pageContent);
    }

    public void buildAsQuizIntroPage() {
        JFXButton button = new JFXButton("Click to begin quiz");
        button.setDisableVisualFocus(true); //fix the button on page appear to be highlighted (not selected, just highlighted)
        button.getStyleClass().addAll("start-test-button", "jfx-button-raised", "jfx-button-raised-color");
        button.setOnAction(e -> {
            Main.takingExam = true;
            new com.magis.app.page.QuizPage(chapterIndex);
        });
        masterContent.setAlignment(Pos.CENTER);
        masterContent.getChildren().add(button);
        //tell the student if they've already taken ths test
        if (Main.studentModel.getStudent().hasTakenQuiz(chapterIndex)) {
            TextFlow textFlow = new TextFlow();
            textFlow.setPadding(new Insets(15,0,0,0));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            Text text = new Text("Just a heads up, you've already taken this quiz! Your best score was ");
            text.getStyleClass().addAll("insight-text", "text-no-color");
            Text score = new Text(Main.studentModel.getStudent().getQuiz(chapterIndex).getBestScore() + "%");
            score.getStyleClass().addAll("insight-text", "text-color");
            textFlow.getChildren().addAll(text, score);
            masterContent.getChildren().add(textFlow);
        }
    }

    public void buildAsTestIntroPage() {
        masterContent.setAlignment(Pos.CENTER);
        JFXButton button = new JFXButton("Click to begin test");
        button.setDisableVisualFocus(true); //fix the button on page appear to be highlighted (not selected, just highlighted)
        button.getStyleClass().addAll("start-test-button", "jfx-button-raised", "jfx-button-raised-color");
        button.setOnAction(e -> {
            Main.takingExam = true;
            new com.magis.app.page.TestPage(chapterIndex);
        });
        masterContent.getChildren().add(button);
        //tell the student if they've already taken ths quiz
        if (Main.studentModel.getStudent().hasTakenTest(chapterIndex)) {
            TextFlow textFlow = new TextFlow();
            textFlow.setPadding(new Insets(15,0,0,0));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            Text text = new Text("Just a heads up, you've already taken this test! Your best score was ");
            text.getStyleClass().addAll("insight-text", "text-no-color");
            Text score = new Text(Main.studentModel.getStudent().getTest(chapterIndex).getBestScore() + "%");
            score.getStyleClass().addAll("insight-text", "text-color");
            textFlow.getChildren().addAll(text, score);
            masterContent.getChildren().add(textFlow);
        }
    }
}


