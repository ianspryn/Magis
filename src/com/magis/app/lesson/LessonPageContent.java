package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.models.LessonContent;
import javafx.geometry.Insets;
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

    public void update(int pageIndex) {
        pageContent.getChildren().clear();
        
        ArrayList<LessonContent> lessonContents = Main.lessonModel.getChapters(chapterIndex).getPages(pageIndex).getLessonContent();
        for (int i = 0; i < lessonContents.size(); i++) {
            LessonContent currentLesson = lessonContents.get(i);
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
}