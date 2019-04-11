package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class LessonPage {

    static int currentPage;
    static LessonSidePanel lessonSidePanel;
    static LessonPageContent lessonPageContent;
    static boolean hasQuiz = false;
    static int numPages;

    public static void Page(int chapterIndex, boolean continueWhereLeftOff) {
        //get the recent page before it's overwritten to 0 in case the user chose to jump back where they left off
        int recentPage = Main.studentModel.getStudent(Main.username).getRecentPage();
        currentPage = 0;
        hasQuiz = Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle());

        if (hasQuiz) {
            numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages() + 1;
        } else {
            numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages();
        }

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-lesson");
        UIComponents.animate(borderPane,0.2,0.2,-10,0,0,0);

        BorderPane sideBar = new BorderPane();
        sideBar.getStyleClass().add("sidebar");

        //Home icon
        HBox homeBox = UIComponents.createHomeBox();
        homeBox.setOnMouseClicked(e -> {
            if (Main.takingTest) {
                if (UIComponents.confirmClose()) {
                    Main.takingTest = false;
                    HomePage.goHome(borderPane);
                }
            } else {
                HomePage.goHome(borderPane);
            }
        });

         lessonPageContent = new LessonPageContent(chapterIndex);
         lessonPageContent.initialize();

        //Lesson Side Panel
        lessonSidePanel = new LessonSidePanel(chapterIndex, lessonPageContent, Main.lessonModel.getChapter(chapterIndex).getPages());
        lessonSidePanel.initialize();

        sideBar.setTop(homeBox);
        sideBar.setLeft(lessonSidePanel.getSidePanel());

//        sideBar.getChildren().addAll(home, lessonSidePanel.getSidePanel());
//        borderPane.setCenter(lessonPageContent.getPageContent());
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
        UIComponents.animate(lessonPageContent.getPageContent(), 0.15,0.2,0,0,-10,0);

        //Bottom navigation
        BorderPane navigationContent = new BorderPane();
        navigationContent.getStyleClass().add("navigation-content");
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
        UIComponents.animate(navigationContent,0.15,0.3,0,0,0,0);

        lessonArea.setCenter(lessonPageScrollPane);
        lessonArea.setBottom(navigationContent);

        borderPane.setCenter(lessonArea);

        if (continueWhereLeftOff) {
            lessonSidePanel.update(recentPage);
            lessonPageContent.update(recentPage);
        }

        Scene scene = new Scene(borderPane, Main.window.getScene().getWidth(), Main.window.getScene().getHeight());
        scene.getStylesheets().add("com/magis/app/css/style.css");

        Main.setScene(scene, Main.lessonModel.getChapter(chapterIndex).getTitle());
    }

    private static void updatePage(int move) {
        //currentPage += -1 or currentPage += 1
        currentPage += move;
        if (currentPage == numPages - 1 && hasQuiz) {

            lessonPageContent.update(-1);
        } else {
            lessonPageContent.update(currentPage);
        }
        lessonSidePanel.update(currentPage); //extra because we need to always update the position of the current page indicator
    }
}
