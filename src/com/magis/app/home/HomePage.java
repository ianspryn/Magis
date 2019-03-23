package com.magis.app.home;

import com.magis.app.Main;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.UI.UIComponents;
import com.magis.app.lesson.LessonPage;
import com.magis.app.resources.ReadChapterXML;
import com.magis.app.resources.ReadStudentXML;
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
import javafx.stage.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class HomePage {

    static String[] greetings = {"Hello:!", "Hey there:!", "Welcome:!", "Good day:!", "How goes it:?", "What's happening:?"};
    static String[] codeGreetings = {"String message = \":\";", "System.out.println(\":\");"};

    public static void Page() {
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
            greeting = codeGreetingComponents[0] + greetingComponents[0] + ", " + Main.studentModel.getStudent(Main.username).getFirstName() + greetingComponents[1] + codeGreetingComponents[1];
        } else {
            greeting = greetingComponents[0] + ", " + Main.studentModel.getStudent(Main.username).getFirstName() + greetingComponents[1];
        }

        Label greetingText = new Label(greeting);
        greetingText.getStyleClass().add("greeting-text");
        vBox.getChildren().add(greetingText);

        int numChapters = Main.lessonModel.getChapters().size();

        //for each chapter
        for (int i = 0; i < numChapters; i++) {
            int chapterIndex = i;
            //master box
            HBox chapterBox = new HBox();
            chapterBox.getStyleClass().add("chapter-box");

            chapterBox.setOnMouseClicked(e -> LessonPage.Page(chapterIndex));
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
            progressIndicator.setProgress(Main.studentModel.getStudent(Main.username).getChapter(i).getProgress());

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
            description.setMinWidth(550);
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



        Scene scene = new Scene(borderPane, Main.window.getWidth(), Main.window.getHeight());
        scene.getStylesheets().add("com/magis/app/css/style.css");

        Main.setScene(scene, "Home");
    }
}
