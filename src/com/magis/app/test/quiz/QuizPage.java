package com.magis.app.test.quiz;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.UI.TestPageContent;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.test.Grader;
import com.magis.app.test.TestResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class QuizPage {

    static JFXButton submitButton;
    static int currentPage;
    static int numPages;
    static QuizSidePanel sidePanel;
    static ScrollPane quizPageScrollPane;
    static TestPageContent testPageContent;
    private static StackPane leftButton;
    private static StackPane rightButton;

    public static void Page (int chapterIndex) {
        ArrayList<Integer> usedBankQuestions = new ArrayList<>();;
        testPageContent = new TestPageContent();
        currentPage = 0;
        //get the title of the current chapter
        String chapterName = Main.lessonModel.getChapter(chapterIndex).getTitle();
        //initialize the quiz
        Main.quizzesModel.initializeQuiz(chapterName);

        /*
        Master
         */
        StackPane master = new StackPane();
        master.getStyleClass().add("background");
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-test");

        //create instance of sideBar
        BorderPane sideBar = new BorderPane();
        sideBar.getStyleClass().add("sidebar");
        sideBar.setPrefWidth(300);

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
        sideBar.setTop(homeBox);

        int numQuestions = 7;

        Grader grader = new Grader(numQuestions);
        numPages = (int) Math.ceil((double) numQuestions / 2);

        //Since the user as the ability to jump between pages, we need to store the test page in memory to hold the user's answers.
        //That way, if the user decides to go back and change an answer, the answers won't be blank (otherwise even though they answers would be saved, the user wouldn't know that)
        ArrayList<QuizPageContent> quizPages = new ArrayList<>();
        for (int i = 0; i < numPages; i++) {
            QuizPageContent quizPageContent = new QuizPageContent(numQuestions, numPages, chapterIndex, grader, testPageContent, usedBankQuestions);
            quizPageContent.initialize(i);
            quizPages.add(quizPageContent);
        }


        //Quiz Content
        quizPageScrollPane = new ScrollPane();
        quizPageScrollPane.setFitToWidth(true);
        quizPageScrollPane.setFitToHeight(true);
        quizPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        quizPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        quizPageScrollPane.setContent(testPageContent.getPageContent(0));
        JFXScrollPane.smoothScrolling(quizPageScrollPane);
        UIComponents.fadeAndTranslate(testPageContent.getPageContent(0), 0.15,0.2,0,0,-10,0);

        //Quiz Side Panel
        sidePanel = new QuizSidePanel(chapterIndex, quizPages, numQuestions, quizPageScrollPane, testPageContent);
        sidePanel.initialize(true);

        sideBar.setLeft(sidePanel.getSidePanel());
        borderPane.setLeft(sideBar);

        //Quiz area
        BorderPane quizArea = new BorderPane();
        //remove the thin border around the nodes
        quizArea.setStyle("-fx-box-border: transparent");

        //Bottom navigation
        BorderPane navigationContent = new BorderPane();
        navigationContent.getStyleClass().add("navigation-content");
        navigationContent.setPadding(new Insets(10,10,10,10));

        //Left navigation
        leftButton = UIComponents.createNavigationButton("<");
        leftButton.setVisible(false); //default to invisible since we're on the first page
        leftButton.setOnMouseClicked(e -> {
            if (currentPage > 0) updatePage(currentPage - 1);
        });
        //Right navigation
        rightButton = UIComponents.createNavigationButton(">");
        rightButton.setOnMouseClicked(e -> {
            if (currentPage < numPages - 1) updatePage(currentPage + 1);
        });

        //Quiz submit button
        submitButton = new JFXButton("Submit");
        submitButton.getStyleClass().addAll("submit-test-button", "jfx-button-raised", "jfx-button-raised-color");
        submitButton.setVisible(false);
        submitButton.setOnAction(e -> confirmSubmit(grader, chapterIndex, navigationContent, sideBar, quizPages, quizPageScrollPane, sidePanel, testPageContent));

        navigationContent.setLeft(leftButton);
        navigationContent.setRight(rightButton);
        navigationContent.setCenter(submitButton);
        UIComponents.fadeAndTranslate(navigationContent,0.15,0.3,0,0,0,0);

        quizArea.setCenter(quizPageScrollPane);
        quizArea.setBottom(navigationContent);

        borderPane.setCenter(quizArea);
        master.getChildren().add(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);
        Main.setScene(master, "Quiz");
    }
    /**
     * A popup to confirm to submit the quiz
     * @param grader the current Grader Class used to grade this quiz
     * @param chapterIndex used to add the grade score to the correct chapter should the user confirm submission
     * @param navigationContent the navigation bar that contains the submit button. Pass this parameter in to remove the submit button
     * @param sideBar the sidebar to be updated once the test is submitted
     * @param quizPages the class containing the questions that will be greyed out (disabled)
     * @param quizPageScrollPane the main content box to update with the quiz results
     * @param sidePanel the side panel to insert the new results tab into
     * @param testPageContent the class containing the pages that will also receive the new results page
     */
    private static void confirmSubmit(Grader grader, int chapterIndex, BorderPane navigationContent, BorderPane sideBar, ArrayList<QuizPageContent> quizPages, ScrollPane quizPageScrollPane, QuizSidePanel sidePanel, TestPageContent testPageContent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Submit Quiz");
        alert.setContentText("Do you wish to submit your quiz?");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        ButtonType okButton = new ButtonType("Submit", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(cancelButton, okButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getText().equals("Submit")) {
                navigationContent.setCenter(null);
                grader.grade();
                Main.studentModel.getStudent().getQuiz(chapterIndex + 1).addScore(grader.getGrade());
                //to recycle some code, instead of creating an entirely new QuizPage, simply insert an additional page
                //create the new pageContent
                VBox result = TestResult.createTestResultPage(Main.lessonModel.getChapter(chapterIndex).getTitle(), "quiz", grader.getGrade());
                //add new page to testPageContent
                testPageContent.add(0, result);
                //add new page to the side panel
                sidePanel.insertCustomPage(0,"Results");
                //reinitialize with the added page
                sidePanel.initialize(false);
                //set the new page
                sideBar.setLeft(sidePanel.getSidePanel());
                //we added a new page, so increment the number of pages
                numPages++;
                //disable all the buttons so the user can't change it
                for (QuizPageContent quizPage : quizPages) {
                    quizPage.disableInput();
                }
                //color code the questions to show the user which ones they answered correctly or wrong
                for (int i = 0; i < quizPages.size(); i++) {
                    quizPages.get(i).colorize(grader, i);
                }
                //reset currentPage
                currentPage = 0;
                //jump to the new page
                quizPageScrollPane.setContent(testPageContent.getPageContent(currentPage));
                //we're done taking the test
                Main.takingTest = false;
            }
        });
    }

    /**
     * Update the page page, including the side panel and the page content
     * @param page the new page index
     */
    public static void updatePage(int page) {
        //increment or decrement currentPage value
        currentPage = page;
        //update the page with new content
        quizPageScrollPane.setContent(testPageContent.getPageContent(currentPage));
        JFXScrollPane.smoothScrolling(quizPageScrollPane);
        //update the side panel to reflect the page change
        sidePanel.update(currentPage);
        //hide the some of the navigation buttons if we're at the beginning or end of the pages
        if (currentPage == 0) leftButton.setVisible(false);
        else leftButton.setVisible(true);
        if (currentPage == numPages - 1) rightButton.setVisible(false);
        else rightButton.setVisible(true);
    }
}