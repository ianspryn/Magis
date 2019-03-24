package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class LessonPage {

    static int currentPage;
    static LessonSidePanel panel;
    public static LessonPageContent lessonPageContent;
    static boolean hasQuiz = false;
    static int numPages;

    public static void Page(int chapterIndex) {
        currentPage = 0;
        hasQuiz = Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle());

        if (hasQuiz) {
            numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages() + 1;
        } else {
            numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages();
        }

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-lesson");

        BorderPane sideBar = new BorderPane();
        sideBar.getStyleClass().add("sidebar");

        //Home icon
        HBox homeBox = UIComponents.createHomeBox(false);

         lessonPageContent = new LessonPageContent(chapterIndex);
         lessonPageContent.initialize();

        //Lesson Side Panel
        panel = new LessonSidePanel(chapterIndex, lessonPageContent, Main.lessonModel.getChapter(chapterIndex).getPages());
        panel.initialize();

        sideBar.setTop(homeBox);
        sideBar.setLeft(panel.getvBox());

//        sideBar.getChildren().addAll(home, panel.getvBox());
        borderPane.setCenter(lessonPageContent.getPageContent());
        borderPane.setLeft(sideBar);

        //Lesson area
        BorderPane lessonArea = new BorderPane();
        //remove the thin border around the nodes
        lessonArea.setStyle("-fx-box-border: transparent");

        //Lesson content
        ScrollPane lessonPageScrollPane = new ScrollPane();
        lessonPageScrollPane.setFitToWidth(true);
        lessonPageScrollPane.setFitToHeight(true);
        lessonPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        lessonPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        lessonPageScrollPane.setContent(lessonPageContent.getPageContent());

        //Bottom navigation
        BorderPane navigationContent = new BorderPane();
        navigationContent.setPadding(new Insets(10,10,10,10));

        //Left navigation
        StackPane leftButton = UIComponents.createNavigationButton("<");
        leftButton.setOnMouseClicked(e -> {
            if (currentPage > 0) {
                updatePage(-1);
            }
        });

        //Right navigation
        StackPane rightButton = UIComponents.createNavigationButton(">");
        rightButton.setOnMouseClicked(e -> {
            if (currentPage < numPages - 1) {
                updatePage(1);
            }
        });

        navigationContent.setLeft(leftButton);
        navigationContent.setRight(rightButton);

        lessonArea.setCenter(lessonPageScrollPane);
        lessonArea.setBottom(navigationContent);

        borderPane.setCenter(lessonArea);

        Scene scene = new Scene(borderPane, Main.window.getScene().getWidth(), Main.window.getScene().getHeight());
        scene.getStylesheets().add("com/magis/app/css/style.css");

        Main.setScene(scene, Main.lessonModel.getChapter(chapterIndex).getTitle());
    }

    private static void updatePage(int move) {
        currentPage += move;
        if (currentPage == numPages - 1 && hasQuiz) {

            lessonPageContent.update(-1);
        } else {
            lessonPageContent.update(currentPage);
        }
        panel.update(currentPage);
    }
}
