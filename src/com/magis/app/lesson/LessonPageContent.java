package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.models.LessonModel;
import com.magis.app.test.quiz.QuizPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class LessonPageContent {

    private VBox pageContent;
    private int chapterIndex;

    public LessonPageContent(int chapterIndex) {
        pageContent = new VBox();
        this.chapterIndex = chapterIndex;
    }

    public VBox getPageContent() {
        return pageContent;
    }

    public void initialize() {
        update(0);
    }

    /**
     * Update the page content of the lesson page
     * @param pageIndex the page to load. If it's -1, then it is the optional test page
     */
    public void update(int pageIndex) {
        if (pageIndex == -1) {
            showQuizPage();
            return;
        }

        pageContent.getChildren().clear();
        pageContent.setAlignment(Pos.TOP_LEFT);
        
        ArrayList<LessonModel.ChapterModel.PageModel.LessonContent> lessonContents = Main.lessonModel.getChapter(chapterIndex).getPages(pageIndex).getLessonContent();
        for (int i = 0; i < lessonContents.size(); i++) {
            LessonModel.ChapterModel.PageModel.LessonContent currentLesson = lessonContents.get(i);
            String type = currentLesson.getType();
            switch (type) {
                case "text":
                    Label text = new Label();
                    text.setText(currentLesson.getContent());
                    text.setWrapText(true);
                    text.setPrefWidth(700);
                    text.getStyleClass().add("lesson-text");
                    text.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT);
                    text.setPadding(new Insets(20,20,20,20));
                    pageContent.getChildren().add(text);
                    break;
                case "image":
                    ImageView image = new ImageView(currentLesson.getContent());
                    image.setPreserveRatio(true);
                    pageContent.getChildren().add(image);
                    break;
                default:
                    System.err.println("Unrecognized XML tag <" + type + ">. Defaulting to text field.");
                    Label textDefault = new Label();
                    textDefault.setText(currentLesson.getContent());
                    textDefault.setWrapText(true);
                    textDefault.setPrefWidth(700);
                    textDefault.getStyleClass().add("lesson-text");
                    textDefault.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT);
                    textDefault.setPadding(new Insets(20,20,20,20));
                    pageContent.getChildren().add(textDefault);
                    break;
            }
        }
    }

    private void showQuizPage() {
        pageContent.getChildren().clear();
        pageContent.setAlignment(Pos.CENTER);
        Button button = new Button("Click to begin test");
        button.getStyleClass().addAll("start-test-button","drop-shadow");
        button.setOnAction(e -> QuizPage.Page(chapterIndex));
        pageContent.getChildren().add(button);
    }
}
