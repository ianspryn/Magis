package com.magis.app.quiz;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class QuizPage {

    public static void Page (int chapterIndex) {

        //read all content for given quiz from XML file
        String chapterName = Main.lessonModel.getChapter(chapterIndex).getTitle();
        Main.quizzesModel.initializeQuiz(chapterName);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-quiz");

        AnchorPane sideBar = new AnchorPane();
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

        Grader grader = new Grader();

        int numQuestions = Main.quizzesModel.getChapter(chapterName).getNumQuestions();
        //Save each correct answer into the grader for future grading
        for (int i = 0; i < numQuestions; i++) {
            grader.addCorrectAnswer(i,Main.quizzesModel.getChapter(chapterName).getQuestion(i).getCorrectAnswer());
        }

        int numPages = numQuestions / 2 + 1;
        //Since the user as the ability to jump between pages, we need to store the quiz page in memory to hold the user's answers.
        //That way, if the user decides to go back and change an answer, the answers won't be blank (otherwise even though they answers would be saved, the user wouldn't know that)
        ArrayList<QuizPageContent> quizPages = new ArrayList<>();
        for (int i = 0; i < numPages; i++) {
            QuizPageContent quizPageContent = new QuizPageContent(chapterIndex, grader);
            quizPageContent.initialize(i);
            quizPages.add(quizPageContent);
        }


        //Quiz Content
        ScrollPane quizPageScrollPane = new ScrollPane();
        quizPageScrollPane.setFitToWidth(true);
        quizPageScrollPane.setFitToHeight(true);
        quizPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        quizPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        quizPageScrollPane.setContent(quizPages.get(0).getPageContent());

        //Quiz Side Panel
        QuizSidePanel panel = new QuizSidePanel(quizPages, Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()).getNumQuestions(), quizPageScrollPane);
        panel.initialize();

        sideBar.getChildren().addAll(home, panel.getvBox());
        borderPane.setLeft(sideBar);

        //Quiz area
        BorderPane quizArea = new BorderPane();

        AnchorPane navigationContent = new AnchorPane();

        //Left navigation
        StackPane leftStackPane = new StackPane();
        Circle leftCircle = new Circle();
        leftCircle.getStyleClass().add("navigation-circle");
        leftCircle.setRadius((35));
        leftCircle.setFill(Color.WHITE);
        Text leftChevron = new Text("<");
        leftChevron.getStyleClass().add("navigation-text");
        leftStackPane.getChildren().addAll(leftCircle, leftChevron);
        leftStackPane.setAlignment(Pos.CENTER);

//        StackPane.setMargin(leftChevron, new Insets(0,0,8,0)); //center "<"

        //Right navigation
        StackPane rightStackPane = new StackPane();
        Circle rightCircle = new Circle();
        rightCircle.getStyleClass().add("navigation-circle");
        rightCircle.setRadius((35));
        rightCircle.setFill(Color.WHITE);
        Text rightChevron = new Text(">");
        rightChevron.getStyleClass().add("navigation-text");
        rightStackPane.getChildren().addAll(rightCircle, rightChevron);
        rightStackPane.setAlignment(Pos.CENTER);

//        StackPane.setMargin(rightChevron, new Insets(0,0,8,0)); //center ">"

//        navigationContent.getChildren().addAll(leftStackPane, rightStackPane, new Text("STUFF"));
        navigationContent.setLeftAnchor(leftStackPane, 10.0);
        navigationContent.setRightAnchor(rightStackPane, 10.0);
        //TODO: Add submit button here


        quizArea.setCenter(quizPageScrollPane);
        quizArea.setBottom(navigationContent);

        borderPane.setCenter(quizArea);

        Scene scene = new Scene(borderPane, Main.window.getWidth(), Main.window.getHeight());
        scene.getStylesheets().add("com/magis/app/css/style.css");

        Main.setScene(scene, "Quiz");
    }
}
