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
import javafx.scene.text.Text;

import java.util.ArrayList;


public class LessonPageContent {

    private VBox masterContent;
    private VBox titleContent;
    private VBox pageContent;
    private int chapterIndex;

    public LessonPageContent(int chapterIndex) {
        masterContent = new VBox();
        masterContent.getStyleClass().add("lesson-page-content");
        masterContent.setAlignment(Pos.TOP_CENTER);
        titleContent = new VBox();
        titleContent.setAlignment(Pos.CENTER);
        titleContent.setPadding(new Insets(30,0,10,20));
        pageContent = new VBox();
        pageContent.setAlignment(Pos.TOP_LEFT);
        pageContent.setPadding(new Insets(0,0,0,20));
        this.chapterIndex = chapterIndex;
    }

    public VBox getPageContent() {
        return masterContent;
    }

    public void initialize() {

        update(0);
    }

    /**
     * Update the page content of the lesson page
     *
     * @param pageIndex the page to load. If it's -1, then it is the optional test page
     */
    public void update(int pageIndex) {
        if (pageIndex == -1) {
            showQuizPage();
            return;
        }

        //Mark the page as visited
        Main.studentModel.getStudent(Main.username).getChapter(chapterIndex).visitPage(pageIndex);

        //Last page visited
        Main.studentModel.getStudent(Main.username).setRecentPlace(chapterIndex, pageIndex);

        masterContent.getChildren().clear();
        titleContent.getChildren().clear();
        pageContent.getChildren().clear();
        Text pageTitle = new Text(Main.lessonModel.getChapter(chapterIndex).getPage(pageIndex).getTitle());
        pageTitle.getStyleClass().add("page-title-text");
        titleContent.getChildren().add(pageTitle);

        ArrayList<LessonModel.ChapterModel.PageModel.LessonContent> lessonContents = Main.lessonModel.getChapter(chapterIndex).getPage(pageIndex).getLessonContent();
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
                    text.setPadding(new Insets(20, 0, 20, 0));
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
                    textDefault.setPadding(new Insets(20, 20, 20, 20));
                    pageContent.getChildren().add(textDefault);
                    break;
            }
        }
        masterContent.getChildren().addAll(titleContent, pageContent);
    }

    private void showQuizPage() {
        masterContent.getChildren().clear();
        masterContent.setAlignment(Pos.CENTER);
        Button button = new Button("Click to begin quiz");
        button.getStyleClass().addAll("start-test-button", "drop-shadow");
        button.setOnAction(e -> {
            Main.takingTest = true;
            QuizPage.Page(chapterIndex);
        });
        masterContent.getChildren().add(button);
    }
}
