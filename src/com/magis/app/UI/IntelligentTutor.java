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

public class IntelligentTutor {
    public static VBox generateRecentActivity() {
        StudentModel.Student student = Main.studentModel.getStudent();
        VBox box = new VBox();

        //Title
        Label lastPlaceText = new Label();
        lastPlaceText.getStyleClass().add("box-title");
        lastPlaceText.setTextAlignment(TextAlignment.LEFT);
        lastPlaceText.setWrapText(true);
        lastPlaceText.setText("Pick up where you left off?");

        //Text description
        TextFlow lastPlaceSubText = new TextFlow();
        lastPlaceSubText.setPadding(new Insets(25,0,0,0));
        lastPlaceSubText.setTextAlignment(TextAlignment.LEFT);

        Text text1 = new Text();
        text1.getStyleClass().add("box-description");
        Text text2 = new Text();
        text2.getStyleClass().add("box-description");
        Text text3 = new Text();
        text3.getStyleClass().add("box-description");
        Text text4 = new Text();
        text4.getStyleClass().add("box-description");
        Text text5 = new Text();
        text5.getStyleClass().add("box-description");

        LessonModel.ChapterModel chapter = Main.lessonModel.getChapter(student.getRecentChapter());


        if (student.getRecentPage() < chapter.getNumPages()) {
            text1.setText("Click here to return to your last activity with ");
            if (chapter.getPage(student.getRecentPage()).getTitle() != null) {
                text2.setText(chapter.getTitle());
                text2.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text3.setText(" on the page ");
                text4.setText(chapter.getPage(student.getRecentPage()).getTitle());
                text4.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text5.setText(".");
            } else {
                text2.setText(chapter.getTitle());
                text2.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text3.setText(" on ");
                text4.setText("page " + (student.getRecentPage() + 1));
                text4.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text5.setText(".");
            }
            lastPlaceSubText.getChildren().addAll(text1, text2, text3, text4, text5);
        } else {
            text1.setText("Looks like my dumb AI thinks you're ready for a quiz/test. How 'bout it?");
            lastPlaceSubText.getChildren().add(text1);
        }


        box.getChildren().addAll(lastPlaceText, lastPlaceSubText);
        return box;
    }

    public static VBox generateInsights() {
        VBox insights = new VBox();

        return insights;
    }
}
