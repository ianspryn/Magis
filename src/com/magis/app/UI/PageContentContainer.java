package com.magis.app.UI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.magis.app.Main;
import com.magis.app.models.LessonModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        pageTitle.getStyleClass().add("page-title-text");
        titleContent.getChildren().add(pageTitle);

        masterContent.getChildren().addAll(titleContent, pageContent);
    }

    public void buildAsQuizIntroPage() {
        masterContent.setAlignment(Pos.CENTER);
        JFXButton button = new JFXButton("Click to begin quiz");
        button.setDisableVisualFocus(true); //fix the button on page appear to be highlighted (not selected, just highlighted)
        button.getStyleClass().addAll("start-test-button", "jfx-button-raised", "jfx-button-raised-color");
        button.setOnAction(e -> {
            Main.takingTest = true;
            new com.magis.app.page.QuizPage(chapterIndex);
        });
        masterContent.getChildren().add(button);
    }

    public void buildAsTestIntroPage() {
        masterContent.setAlignment(Pos.CENTER);
        JFXButton button = new JFXButton("Click to begin test");
        button.setDisableVisualFocus(true); //fix the button on page appear to be highlighted (not selected, just highlighted)
        button.getStyleClass().addAll("start-test-button", "jfx-button-raised", "jfx-button-raised-color");
        button.setOnAction(e -> {
            Main.takingTest = true;
            new com.magis.app.page.TestPage(chapterIndex);
        });
        masterContent.getChildren().add(button);
    }
}


