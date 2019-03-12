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
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;


public class HomePage {

    public static void Page() {
        /*
        Master
         */
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-home");

        /*
        Top
         */
//        HBox hBox = UIComponents.CreateTitleBar();
//        hBox.setId("toolbar");
//        borderPane.setTop(hBox);

        /*
        Middle
         */
        VBox vBox = new VBox();
        vBox.getStyleClass().add("chapter-box-container");
        vBox.setMaxWidth(750);

        ReadStudentXML readStudentXML = new ReadStudentXML(Main.studentID);
        int numChapters = ReadChapterXML.getNumChapters();
        String firstName = readStudentXML.getFirstName();
        ArrayList<String> images = ReadChapterXML.getChapterImages();
        ArrayList<String> titles = ReadChapterXML.getChapterTitles();
        ArrayList<String> descriptions = ReadChapterXML.getChapterDescriptions();
        ArrayList<Integer> chapterProgresses = readStudentXML.getAllChaptersProgress();

        //for each chapter
        for (int i = 0; i < numChapters; i++) {
            int chapterIndex = i;
            //master box
            HBox chapterBox = new HBox();

            chapterBox.setOnMouseClicked(e -> LessonPage.Page(chapterIndex));
            chapterBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            chapterBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            chapterBox.getStyleClass().add("chapter-box");

            //Left image
            ImageView imageView = new ImageView(images.get(i));
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

            AnchorPane topContent = new AnchorPane();

            //Progress
            RingProgressIndicator progressIndicator = new RingProgressIndicator();
            progressIndicator.setProgress(chapterProgresses.get(i));

            //Title
            Label title = new Label();
            title.getStyleClass().add("chapter-title-text");
            title.setTextAlignment(TextAlignment.RIGHT);
            title.setWrapText(true);
            title.setText(titles.get(i));

            topContent.getChildren().addAll(progressIndicator, title);
            topContent.setLeftAnchor(progressIndicator, 0.0);
            topContent.setRightAnchor(title, 5.0);

            //Text description
            Label description = new Label();
            description.setWrapText(true);
            description.getStyleClass().add("chapter-description-text");
            description.setTextAlignment(TextAlignment.LEFT);
            description.setText(descriptions.get(i));

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
