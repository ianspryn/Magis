package com.magis.app.test.quiz;

import com.magis.app.Main;
import com.magis.app.UI.TestPageContent;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import com.magis.app.test.Grader;
import com.magis.app.test.TestResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class QuizPage {

    public static boolean notSubmitted = true;
    static ArrayList<QuizPageContent> quizPages;
    static ScrollPane quizPageScrollPane;
    static QuizSidePanel sidePanel;
    static TestPageContent testPageContent;
    static AnchorPane sideBar;
    static Button submitButton;
    static int currentPage;
    static int numPages;

    public static void Page (int chapterIndex) {
        testPageContent = new TestPageContent();
        currentPage = 0;

//        Main.window.setOnCloseRequest(e -> );

        //read all content for given test from XML file
        String chapterName = Main.lessonModel.getChapter(chapterIndex).getTitle();
        Main.quizzesModel.initializeQuiz(chapterName);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-test");

        sideBar = new AnchorPane();
        sideBar.getStyleClass().add("sidebar");
        sideBar.setPrefWidth(300);

        //Home icon
        Button homeButton = UIComponents.CreateSVGIconButton(MaterialIcons.home, 50);

        //Magis logo
        ImageView magisLogo = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/magis-small.png");
        magisLogo.setPreserveRatio(true);
        magisLogo.setFitWidth(175);

        HBox home = new HBox();
        home.setPickOnBounds(true);
        home.setSpacing(20);
        home.setMinWidth(300);
        home.setPadding(new Insets(15,0,0,20));
        home.getChildren().addAll(homeButton, magisLogo);

        //listeners
        home.setOnMouseClicked(e -> HomePage.Page());
        home.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        home.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        sideBar.setTopAnchor(home, 0.0);

        Grader grader = new Grader(Main.quizzesModel.getChapter(chapterName).getNumQuestions());

        int numQuestions = Main.quizzesModel.getChapter(chapterName).getNumQuestions();
        numPages = numQuestions / 2 + 1;
        //Save each correct answer into the grader for future grading
        for (int i = 0; i < numQuestions; i++) {
            grader.addCorrectAnswer(i,Main.quizzesModel.getChapter(chapterName).getQuestion(i).getCorrectAnswer());
        }

        //Since the user as the ability to jump between pages, we need to store the test page in memory to hold the user's answers.
        //That way, if the user decides to go back and change an answer, the answers won't be blank (otherwise even though they answers would be saved, the user wouldn't know that)
        quizPages = new ArrayList<>();
        for (int i = 0; i < numPages; i++) {
            QuizPageContent quizPageContent = new QuizPageContent(chapterIndex, grader, testPageContent);
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

        //Quiz Side Panel
        sidePanel = new QuizSidePanel(quizPages, Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()).getNumQuestions(), quizPageScrollPane, testPageContent);
        sidePanel.initialize(true);


        sideBar.getChildren().addAll(home, sidePanel.getvBox());
        borderPane.setLeft(sideBar);

        //Quiz area
        BorderPane quizArea = new BorderPane();
        //remove the thin border around the nodes
        quizArea.setStyle("-fx-box-border: transparent");

        //Bottom navigation
        AnchorPane navigationContent = new AnchorPane();
        navigationContent.setPadding(new Insets(10,10,10,10));

        //Left navigation
        StackPane leftStackPane = new StackPane();
        Circle leftCircle = new Circle();
        leftCircle.getStyleClass().add("circle-shadow");
        leftCircle.setRadius((35));
        leftCircle.setFill(Paint.valueOf("ffffff"));
        Text leftChevron = new Text("<");
        leftChevron.getStyleClass().add("navigation-text");
        leftStackPane.getChildren().addAll(leftCircle, leftChevron);
        leftStackPane.setAlignment(Pos.CENTER);
        StackPane.setMargin(leftChevron, new Insets(0,0,8,0)); //center "<"
        leftStackPane.setOnMouseClicked(e -> {
            if (currentPage > 0) {
                updatePage(-1);
            }
        });
        leftStackPane.setOnMousePressed(e ->  leftCircle.setFill(Paint.valueOf("ededed")));
        leftStackPane.setOnMouseReleased(e ->  leftCircle.setFill(Paint.valueOf("ffffff")));
        leftStackPane.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
        });
        leftStackPane.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
        });

        //Right navigation
        StackPane rightStackPane = new StackPane();
        Circle rightCircle = new Circle();
        rightCircle.getStyleClass().add("circle-shadow");
        rightCircle.setRadius((35));
        rightCircle.setFill(Paint.valueOf("ffffff"));
        Text rightChevron = new Text(">");
        rightChevron.getStyleClass().add("navigation-text");
        rightStackPane.getChildren().addAll(rightCircle, rightChevron);
        rightStackPane.setAlignment(Pos.CENTER);
        StackPane.setMargin(rightChevron, new Insets(0,0,8,0)); //center ">"
        rightStackPane.setOnMouseClicked(e -> {
            if (currentPage < numPages - 1) {
                updatePage(1);
            }
        });
        rightStackPane.setOnMousePressed(e ->  rightCircle.setFill(Paint.valueOf("ededed")));
        rightStackPane.setOnMouseReleased(e ->  rightCircle.setFill(Paint.valueOf("ffffff")));
        rightStackPane.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        rightStackPane.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        submitButton = new Button();
        submitButton.setVisible(false);
        submitButton.setOnAction(e -> confirmSubmit(grader, chapterIndex));

        navigationContent.getChildren().addAll(leftStackPane, submitButton, rightStackPane);
        navigationContent.setTopAnchor(submitButton,0.0);
        navigationContent.setLeftAnchor(leftStackPane, 10.0);
        navigationContent.setRightAnchor(rightStackPane, 10.0);



        quizArea.setCenter(quizPageScrollPane);
        quizArea.setBottom(navigationContent);

        borderPane.setCenter(quizArea);

        Scene scene = new Scene(borderPane, Main.window.getWidth(), Main.window.getHeight());
        scene.getStylesheets().add("com/magis/app/css/style.css");

        Main.setScene(scene, "Quiz");
    }

    /**
     * A popup to confirm to submit the quiz
     * @param grader the current Grader Class used to grade this quiz
     * @param chapterIndex used to add the grade score to the correct chapter should the user confirm submission
     */
    private static void confirmSubmit(Grader grader, int chapterIndex) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Submit Quiz");
        alert.setContentText("Do you wish to submit your quiz?");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        ButtonType okButton = new ButtonType("Submit", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(cancelButton, okButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getText().equals("Submit")) {
                notSubmitted = true;
                grader.grade();
                Main.studentModel.getStudent(Main.username).getQuiz(chapterIndex + 1).addScore(grader.getGrade());

                /*
                to recycle some code, instead of creating an entirely new QuizPage, simply insert an additional page
                 */
                //create the new pageContent
                VBox result = TestResult.createTestResultPage(Main.lessonModel.getChapter(chapterIndex).getTitle(), "quiz", grader.getGrade());
                //add new page to testPageContent
                testPageContent.add(0, result);
                //add new page to the side panel
                sidePanel.insertCustomPage(0,"Results");
                //reinitialize with the added page
                sidePanel.initialize(false);
                //set the new page
                sideBar.getChildren().set(1, sidePanel.getvBox());
                //we added a new page, so increment the number of pages
                numPages++;
                //disable all the buttons so the user can't change it
                for (QuizPageContent quizPage : quizPages) {
                    quizPage.disableInput();
                }

                //color code the questions to show the user which ones they answered correctly or wrong
                for (QuizPageContent quizPage : quizPages) {
                    quizPage.colorize(grader);
                }
                //reset currentPage
                currentPage = 0;
                //jump to the new page
                quizPageScrollPane.setContent(testPageContent.getPageContent(currentPage));

            }
        });
    }

    /**
     * A method used by the navigation buttons to change pages
     * @param move positive or negative 1, depending on the direction of page changing
     */
    private static void updatePage(int move) {
        //increment or decrement currentPage value
        currentPage += move;
        //update the page with new content
        quizPageScrollPane.setContent(testPageContent.getPageContent(currentPage));
        //update the side panel to reflect the page change
        sidePanel.update(currentPage);
        if (currentPage + 1 == numPages && notSubmitted) {
            submitButton.setVisible(true);
        } else {
            submitButton.setVisible(false);
        }
    }
}
