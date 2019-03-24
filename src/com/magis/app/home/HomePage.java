package com.magis.app.home;

import com.magis.app.Main;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.lesson.LessonPage;
import com.magis.app.models.StudentModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.Random;


public class HomePage {

    private static String[] greetings = {"Hello:!", "Hey there:!", "Welcome:!", "Good day:!", "How goes it:?", "What's happening:?"};
    private static String[] codeGreetings = {"String message = \":\";", "System.out.println(\":\");"};

    public static void Page() {
        StudentModel.Student student = Main.studentModel.getStudent(Main.username);
        Random rand = new Random();
        /*
        Master
         */
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-home");

        /*
        Middle
         */
        VBox vBox = new VBox();
        vBox.getStyleClass().add("chapter-box-container");
        vBox.setMaxWidth(750);

        //Greeting
        String greeting = greetings[rand.nextInt(greetings.length)];
        String[] greetingComponents = greeting.split(":");

        //add the code greeting 1/3 of the time
        int code = rand.nextInt(3);
        if (code == 0) {
            String codeGreeting = codeGreetings[rand.nextInt(codeGreetings.length)];
            String[] codeGreetingComponents = codeGreeting.split(":");
            greeting = codeGreetingComponents[0] + greetingComponents[0] + ", " + student.getFirstName() + greetingComponents[1] + codeGreetingComponents[1];
        } else {
            greeting = greetingComponents[0] + ", " + student.getFirstName() + greetingComponents[1];
        }

        Label greetingText = new Label(greeting);
        greetingText.getStyleClass().add("greeting-text");
        vBox.getChildren().add(greetingText);


        //Last Activity
        if (student.getRecentChapter() > -1) {
            //master box
            HBox recentBox = new HBox();
            recentBox.getStyleClass().add("recent-box");
            recentBox.setMaxWidth(350);
            recentBox.setMinHeight(100);
            recentBox.setAlignment(Pos.CENTER_LEFT);

            recentBox.setOnMouseClicked(e -> LessonPage.Page(student.getRecentChapter(), true));
            recentBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            recentBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            //Progress, title, and text description
            VBox lastPlace = new VBox();
            recentBox.setAlignment(Pos.CENTER_LEFT);

            //Title
            Label lastPlaceText = new Label();
            lastPlaceText.getStyleClass().add("chapter-title-text");
            lastPlaceText.setTextAlignment(TextAlignment.LEFT);
            lastPlaceText.setWrapText(true);
            lastPlaceText.setText("Pick up where you left off?");

            //Text description
            TextFlow lastPlaceSubText = new TextFlow();
            lastPlaceSubText.setPadding(new Insets(25,0,0,0));
            lastPlaceSubText.getStyleClass().add("chapter-description-text");
            lastPlaceSubText.setTextAlignment(TextAlignment.LEFT);

            Text text1 = new Text("Tap to return to your last activity with ");
            Text text2, text3, text4, text5;
            if (Main.lessonModel.getChapter(student.getRecentChapter()).getPage(student.getRecentPage()).getTitle() != null) {
                text2 = new Text(Main.lessonModel.getChapter(student.getRecentChapter()).getTitle());
                text2.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text3 = new Text(" on the page ");
                text4 = new Text(Main.lessonModel.getChapter(student.getRecentChapter()).getPage(student.getRecentPage()).getTitle());
                text4.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text5 = new Text(".");
            } else {
                text2 = new Text(Main.lessonModel.getChapter(student.getRecentChapter()).getTitle());
                text2.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text3 = new Text(" on ");
                text4 = new Text("page " + Integer.toString((student.getRecentPage() + 1)));
                text4.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                text5 = new Text(".");
            }
            lastPlaceSubText.getChildren().addAll(text1, text2, text3, text4, text5);
            lastPlace.getChildren().addAll(lastPlaceText, lastPlaceSubText);

            recentBox.getChildren().addAll(lastPlace);

            vBox.getChildren().add(recentBox);
        }

        int numChapters = Main.lessonModel.getChapters().size();

        //for each chapter
        for (int i = 0; i < numChapters; i++) {
            int chapterIndex = i;
            //master box
            HBox chapterBox = new HBox();
            chapterBox.getStyleClass().add("chapter-box");

            chapterBox.setOnMouseClicked(e -> LessonPage.Page(chapterIndex, false));
            chapterBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            chapterBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));


            //Left image
            ImageView imageView = new ImageView(Main.lessonModel.getChapter(i).getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);

            //Separator
            Separator separator = new Separator();
            separator.getStyleClass().add("separator-home");
            separator.setOrientation(Orientation.VERTICAL);
            separator.setMaxHeight(200);
            separator.setPadding(new Insets(0, 35, 0, 15));

            //Progress, title, and text description
            VBox chapterInfo = new VBox();
            chapterBox.setAlignment(Pos.CENTER_LEFT);

            //Progress
            RingProgressIndicator progressIndicator = new RingProgressIndicator();
            progressIndicator.setProgress(student.getChapter(i).getProgress());

            //Title
            Label title = new Label();
            title.getStyleClass().add("chapter-title-text");
            title.setTextAlignment(TextAlignment.RIGHT);
            title.setWrapText(true);
            title.setText(Main.lessonModel.getChapter(i).getTitle());

            AnchorPane topContent = new AnchorPane();
            topContent.getChildren().addAll(progressIndicator, title);
            topContent.setLeftAnchor(progressIndicator, 0.0);
            topContent.setRightAnchor(title, 5.0);

            //Text description
            Label description = new Label();
            description.setPrefWidth(550);
            description.setWrapText(true);
            description.getStyleClass().add("chapter-description-text");
            description.setTextAlignment(TextAlignment.LEFT);
            description.setText(Main.lessonModel.getChapter(i).getDescription());

            chapterInfo.getChildren().addAll(topContent, description);

            chapterBox.getChildren().addAll(imageView, separator, chapterInfo);

            vBox.getChildren().add(chapterBox);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.getStyleClass().add("chapter-box-scrollpane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        StackPane contentHolder = new StackPane(vBox);
        contentHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        scrollPane.setContent(contentHolder);
        borderPane.setCenter(scrollPane);


//        Scene oldScene = Main.window.getScene();
//        Scene newScene = (oldScene == null ? new Scene(borderPane, Main.width, Main.height) : new Scene(borderPane, oldScene.getWidth(), oldScene.getHeight()));
        Scene scene = new Scene(borderPane, Main.window.getScene().getWidth(), Main.window.getScene().getHeight());
        scene.getStylesheets().add("com/magis/app/css/style.css");

        Main.setScene(scene, "Home");
    }
}
