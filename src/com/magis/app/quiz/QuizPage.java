package com.magis.app.quiz;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class QuizPage {
    public static void Page (int chapterIndex) {

        //read all content for given quiz from XML file
        Main.quizzesModel.initializeQuiz(chapterIndex + 1);

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

        QuizPageContent quizPageContent = new QuizPageContent(chapterIndex);
        quizPageContent.initialize();
    }
}
