package com.magis.app.UI;

import com.magis.app.Main;
import com.magis.app.models.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.MethodAccessor_Integer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class IntelligentTutor {
    public static VBox generateRecentActivity() {
        StudentModel.Student student = Main.studentModel.getStudent();
        LessonModel.ChapterModel chapter = Main.lessonModel.getChapter(student.getRecentChapter());
        QuizzesModel quizzesModel = Main.quizzesModel;
        TestsModel testsModel = Main.testsModel;

        //determine if the recent page was a lesson page (not a "begin quiz/test" page)
        boolean onLessonPage = student.getRecentPage() < chapter.getNumPages();
        /*
        Determine if we are on a "begin quiz" page
        First check if the chapter has a quiz
        Then check of the page index matches the index of the quiz page for that lesson
         */
        boolean onQuizPage = quizzesModel.hasQuiz(chapter.getTitle()) && student.getRecentPage() == chapter.getNumPages();
        /*
        Determine if we are on a "begin test" page
        First check if the chapter has a test
        Then check of the page index matches the index of the test page for that lesson
        Note: We add 1 if we have a quiz page, because that pushes the index of the test page up one more
         */
        int testPageIndex = ((quizzesModel.hasQuiz(chapter.getTitle()) ? 1 : 0) + chapter.getNumPages());
        boolean onTestPage = testsModel.hasTest(chapter.getTitle()) && student.getRecentPage() == testPageIndex;

        ArrayList<Integer> visitedPages = student.getChapter(student.getRecentChapter()).getPageVisited();

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

        ArrayList<Text> texts = new ArrayList<>();

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

        if (onLessonPage) {

        }


        //if the last page was a lesson page
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
        } else { //
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
