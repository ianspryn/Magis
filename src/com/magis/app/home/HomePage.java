package com.magis.app.home;

import com.magis.app.Main;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.UI.SmartContinue;
import com.magis.app.UI.UIComponents;
import com.magis.app.lesson.LessonPage;
import com.magis.app.models.LessonModel;
import com.magis.app.models.StudentModel;
import com.sun.jndi.dns.DnsUrl;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

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
        UIComponents.animate(greetingText,0.3,0.2,0,0,-10,0);
        vBox.getChildren().add(greetingText);


        //Last Activity
        if (student.getRecentChapter() > -1) {
            //master box
            HBox recentBox = new HBox();
            recentBox.getStyleClass().add("recent-box");
            recentBox.setMaxWidth(350);
            recentBox.setMinHeight(100);
            recentBox.setAlignment(Pos.CENTER_LEFT);

            recentBox.setOnMouseClicked(e -> goToLessonPage(vBox, student.getRecentChapter(), true));
            recentBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            recentBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            recentBox.getChildren().add(SmartContinue.generate());

            vBox.getChildren().add(recentBox);
            UIComponents.animate(recentBox,0.3,0.2,0,0,-10,0);
        }

        int numChapters = Main.lessonModel.getChapters().size();

        //Progress, title, and text description
        //for each chapter
        for (int i = 0; i < numChapters; i++) {
            int chapterIndex = i;
            //master box
            HBox chapterBox = new HBox();
            chapterBox.getStyleClass().add("chapter-box");
            UIComponents.animate(chapterBox, i, 0.3, 0.2, 0,0,-10,0);

            chapterBox.setOnMouseClicked(e -> goToLessonPage(vBox, chapterIndex, false));
            chapterBox.setOnMouseEntered(e -> {
                Main.scene.setCursor(Cursor.HAND);
                scaleBox(chapterBox,1.02);
            });
            chapterBox.setOnMouseExited(e -> {
                Main.scene.setCursor(Cursor.DEFAULT);
                scaleBox(chapterBox,1);
            });


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

    private static void scaleBox(Node node, double end) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1), node);
        scaleTransition.setFromX(node.getScaleX());
        scaleTransition.setToX(end);
        scaleTransition.setFromY(node.getScaleY());
        scaleTransition.setToY(end);

        scaleTransition.play();
    }

    /**
     * Animate going to the lesson page by fading out the home page content first
     * @param node the node to fade out
     * @param chapterIndex the desired chapter to switch scenes to
     */
    private static void goToLessonPage(Node node, int chapterIndex, boolean continueWhereLeftOff) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        //move up
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), node);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10);

        //move down and fade in at the same time
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);

        parallelTransition.play();
        parallelTransition.setOnFinished(e -> LessonPage.Page(chapterIndex, continueWhereLeftOff));
    }


    public static void goHome(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        //move up
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), node);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10);

        //move down and fade in at the same time
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);

        parallelTransition.play();

        parallelTransition.setOnFinished(e -> HomePage.Page());
    }
}
