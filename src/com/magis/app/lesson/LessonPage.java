package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
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
        String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
        int hasQuiz = Main.quizzesModel.hasQuiz(chapterTitle) ? 1 : 0;
        int hasTest = Main.testsModel.hasTest(chapterTitle) ? 1 : 0;

        numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages() + hasQuiz + hasTest;

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-lesson");
        UIComponents.fadeAndTranslate(borderPane,0.2,0.2,-10,0,0,0);

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

        lessonPageContent = new LessonPageContent(lessonPageScrollPane, chapterIndex, hasQuiz, hasTest);
        lessonPageContent.initialize();

        UIComponents.fadeAndTranslate(lessonPageContent.getPageContent(), 0.15,0.2,0,0,-10,0);

        //Lesson Side Panel
        lessonSidePanel = new LessonSidePanel(chapterIndex, lessonPageContent, Main.lessonModel.getChapter(chapterIndex).getPages(), hasQuiz, hasTest);
        lessonSidePanel.initialize();

        sideBar.setTop(homeBox);
        sideBar.setLeft(lessonSidePanel.getSidePanel());

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
        UIComponents.fadeAndTranslate(navigationContent,0.15,0.3,0,0,0,0);

        lessonArea.setCenter(lessonPageScrollPane);
        lessonArea.setBottom(navigationContent);

        borderPane.setCenter(lessonArea);

        if (continueWhereLeftOff) {
            lessonSidePanel.update(recentPage);
            lessonPageContent.update(recentPage);
        }
        Main.setScene(borderPane, Main.lessonModel.getChapter(chapterIndex).getTitle());
    }

    private static void updatePage(int move) {
        //currentPage += -1;      or      currentPage += 1;
        currentPage += move;
        lessonPageContent.update(currentPage);
        lessonSidePanel.update(currentPage);
    }
}
