package com.magis.app.UI;

import com.magis.app.Main;
import com.magis.app.models.LessonModel;
import com.magis.app.models.StudentModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class SmartContinue {
    public static VBox generate() {
        StudentModel.Student student = Main.studentModel.getStudent(Main.username);
        VBox box = new VBox();

        //Title
        Label lastPlaceText = new Label();
        lastPlaceText.getStyleClass().add("chapter-title-text");
        lastPlaceText.setTextAlignment(TextAlignment.LEFT);
        lastPlaceText.setWrapText(true);
        lastPlaceText.setText("Pick up where you left off?");

        //Text description
        TextFlow lastPlaceSubText = new TextFlow();
        lastPlaceSubText.setPadding(new Insets(25,0,0,0));
        lastPlaceSubText.getStyleClass().add("chapter-description-text");
        lastPlaceSubText.setTextAlignment(TextAlignment.LEFT);

        Text text1, text2, text3, text4, text5;
        LessonModel.ChapterModel chapter = Main.lessonModel.getChapter(student.getRecentChapter());


        if (student.getRecentPage() < chapter.getNumPages()) {
            text1 = new Text("Click here to return to your last activity with ");
            if (chapter.getPage(student.getRecentPage()).getTitle() != null) {
                text2 = new Text(chapter.getTitle());
                text2.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text3 = new Text(" on the page ");
                text4 = new Text(chapter.getPage(student.getRecentPage()).getTitle());
                text4.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text5 = new Text(".");
            } else {
                text2 = new Text(chapter.getTitle());
                text2.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text3 = new Text(" on ");
                text4 = new Text("page " + (student.getRecentPage() + 1));
                text4.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text5 = new Text(".");
            }
            lastPlaceSubText.getChildren().addAll(text1, text2, text3, text4, text5);
        } else {
            text1 = new Text("Looks like my dumb AI thinks you're ready for a quiz/test. How 'bout it?");
            lastPlaceSubText.getChildren().add(text1);
        }
        text1.getStyleClass().add("chapter-description-text");


        box.getChildren().addAll(lastPlaceText, lastPlaceSubText);
        return box;
    }
}
