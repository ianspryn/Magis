package com.magis.app.lesson;

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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class LessonPage {

    static int currentPage = 0;
    static LessonSidePanel panel;
    static LessonPageContent lessonPageContent;
    static boolean hasQuiz = false;
    static int numPages;

    public static void Page(int chapterIndex) {
        hasQuiz = Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle());

        if (hasQuiz) {
            numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages() + 1;
        } else {
            numPages = Main.lessonModel.getChapter(chapterIndex).getNumPages();
        }

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-lesson");

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
        home.setOnMouseEntered(e -> Main.scene.setCursor(javafx.scene.Cursor.HAND));
        home.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        sideBar.setTopAnchor(home, 0.0);

         lessonPageContent = new LessonPageContent(chapterIndex);
         lessonPageContent.initialize();

        //Lesson Side Panel
        panel = new LessonSidePanel(chapterIndex, lessonPageContent, Main.lessonModel.getChapter(chapterIndex).getPages());
        panel.initialize();

        sideBar.getChildren().addAll(home, panel.getvBox());
        borderPane.setLeft(sideBar);

        //Lesson area
        BorderPane lessonArea = new BorderPane();

        //Lesson content
        ScrollPane lessonPageScrollPane = new ScrollPane();
        lessonPageScrollPane.setFitToWidth(true);
        lessonPageScrollPane.setFitToHeight(true);
        lessonPageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        lessonPageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        lessonPageScrollPane.setContent(lessonPageContent.getPageContent());

        //Lesson navigation
        //Bottom navigation
        AnchorPane navigationContent = new AnchorPane();
        navigationContent.setPadding(new Insets(10,10,10,10));

        //Left navigation
        StackPane leftStackPane = new StackPane();
        Circle leftCircle = new Circle();
        leftCircle.getStyleClass().add("navigation-circle");
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
        rightCircle.getStyleClass().add("navigation-circle");
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
        rightStackPane.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
        });
        rightStackPane.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
        });

        navigationContent.getChildren().addAll(leftStackPane, rightStackPane);
        navigationContent.setLeftAnchor(leftStackPane, 10.0);
        navigationContent.setRightAnchor(rightStackPane, 10.0);

        lessonArea.setCenter(lessonPageScrollPane);
        lessonArea.setBottom(navigationContent);

        borderPane.setCenter(lessonArea);

        Scene scene = new Scene(borderPane, Main.window.getWidth(), Main.window.getHeight());
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
