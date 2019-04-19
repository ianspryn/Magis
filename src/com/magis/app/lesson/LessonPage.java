package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class LessonPage {

    static int currentPage;
    static LessonSidePanel lessonSidePanel;
    static LessonPageContent lessonPageContent;
    static ScrollPane lessonPageScrollPane;
    public  static StackPane leftButton;
    public static StackPane rightButton;
    static boolean hasQuiz = false;
    static int numPages;

    public static void Page(int chapterIndex, boolean continueWhereLeftOff) {
        //get the recent page before it's overwritten to 0 in case the user chose to jump back where they left off
        int recentPage = Main.studentModel.getStudent().getRecentPage();
        currentPage = 0;
        String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
        int hasQuiz = Main.quizzesModel.hasQuiz(chapterTitle) ? 1 : 0;
        int hasTest = Main.testsModel.hasTest(chapterTitle) ? 1 : 0;

        numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages() + hasQuiz + hasTest;

        StackPane master = new StackPane();
        master.getStyleClass().add("background");

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-lesson");
        UIComponents.fadeAndTranslate(borderPane,0.2,0.2,-10,0,0,0);

        BorderPane sideBar = new BorderPane();
        sideBar.getStyleClass().add("sidebar");

        //Home icon
        HBox homeBox = UIComponents.createHomeBox();
        homeBox.setOnMouseClicked(e -> {
            if (Main.takingTest) {
                String title = "Exit Test";
                String content = "Are you sure you want to exit? All test progress will be lost!";
                if (UIComponents.confirmMessage(title, content)) {
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
        lessonPageScrollPane = new ScrollPane();
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
        leftButton = UIComponents.createNavigationButton("<");
        leftButton.setVisible(false); //default to invisible since we're on the first page
        leftButton.setOnMouseClicked(e -> {
            if (currentPage > 0) {
                updatePage(currentPage - 1);
            }
        });

        //Right navigation
        rightButton = UIComponents.createNavigationButton(">");
        rightButton.setOnMouseClicked(e -> {
            if (currentPage < numPages - 1) {
                updatePage(currentPage + 1);
            }
        });

        navigationContent.setLeft(leftButton);
        navigationContent.setRight(rightButton);
        UIComponents.fadeAndTranslate(navigationContent,0.15,0.3,0,0,0,0);

        lessonArea.setCenter(lessonPageScrollPane);
        lessonArea.setBottom(navigationContent);

        borderPane.setCenter(lessonArea);

        if (continueWhereLeftOff) updatePage(recentPage);

        master.getChildren().add(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        Main.setScene(master, Main.lessonModel.getChapter(chapterIndex).getTitle());
    }

    /**
     * Update the page page, including the side panel and the page content
     * @param page the new page index
     */
    public static void updatePage(int page) {
        currentPage = page;
        lessonPageScrollPane.setVvalue(0); //reset the scroll bar to the top
        lessonPageContent.update(currentPage);
        lessonSidePanel.update(currentPage);
        //hide the some of the navigation buttons if we're at the beginning or end of the pages
        if (currentPage == 0) leftButton.setVisible(false);
        else leftButton.setVisible(true);
        if (currentPage == numPages - 1) rightButton.setVisible(false);
        else rightButton.setVisible(true);
    }
}
