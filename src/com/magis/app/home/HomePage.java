package com.magis.app.home;

import com.magis.app.Main;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.UI.SmartContinue;
import com.magis.app.UI.UIComponents;
import com.magis.app.lesson.LessonPage;
import com.magis.app.models.StudentModel;
import com.magis.app.settings.SettingsPage;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javax.sound.midi.Receiver;
import java.util.ArrayList;
import java.util.Random;


public class HomePage {

    private static HomePage homePage = null;
    private static BorderPane borderPane;
    private  static Label greetingLabel;
    private ArrayList<HBox> chapterBoxes;
    private static ArrayList<RingProgressIndicator> ringProgressIndicators;
    private static HBox recentBox;
    private HBox settingsBox;
    private VBox masterVbox;
    private VBox vBox;
    private StudentModel.Student student;
    private ArrayList<Thread> threads;

    private static String[] greetings = {"Hello:!", "Hey there:!", "Welcome:!", "Good day:!", "How goes it:?", "What's happening:?"};
    private static String[] codeGreetings = {"String message = \":\";", "System.out.println(\":\");"};

    public static HomePage getInstance() {
        if (homePage == null) {
            homePage = new HomePage();
        } else {
            update();
        }
        return homePage;
    }

    private static void update() {
        int numChapters = Main.lessonModel.getNumChapters();
        StudentModel.Student student = Main.studentModel.getStudent(Main.username);
        for (int i = 0; i < numChapters; i++) {
            ringProgressIndicators.get(i).setProgress(student.getChapter(i).getProgress());
        }
        if (student.getRecentPage() != -1) { //make sure we don't add to the recentBox until student visited a page
            recentBox.setVisible(true);
            recentBox.getChildren().clear();
            recentBox.getChildren().add(SmartContinue.generate());
            greetingLabel.setText(generateGreetingText());
        }
    }

    public void Page() {
        Main.setScene(borderPane, "Home");
        animate();
    }

    private void animate() {
        UIComponents.fadeAndTranslate(borderPane, 0.3, 0.2, 0, 0, -10, 0);
        UIComponents.fadeAndTranslate(greetingLabel, 0.3, 0.2, 0, 0, -10, 0);
        if (student.getRecentChapter() > -1) {
            UIComponents.fadeAndTranslate(recentBox, 0.3, 0.2, 0, 0, -10, 0);
        }
        for (int i = 0; i < chapterBoxes.size(); i++) {
            UIComponents.fadeAndTranslate(chapterBoxes.get(i), i, 0.5, 0.2, 0, 0, -10, 0);
        }
        UIComponents.fadeAndTranslate(settingsBox, chapterBoxes.size(), 0.5,0.2,0,0,-10,0);
    }

    private HomePage() {
        student = Main.studentModel.getStudent(Main.username);
        ringProgressIndicators = new ArrayList<>();
        threads = new ArrayList<>();
        /*
        Master
         */
        borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-home");

        masterVbox = new VBox();
        masterVbox.setAlignment(Pos.CENTER);

        /*
        Middle
         */
        vBox = new VBox();
        vBox.getStyleClass().add("chapter-box-container");
        vBox.setMaxWidth(750);

        //Greeting
        greetingLabel = new Label(generateGreetingText());
        greetingLabel.getStyleClass().add("greeting-text");
        greetingLabel.setPadding(new Insets(50,0,0,0));
        masterVbox.getChildren().add(greetingLabel);


        //Last Activity
        recentBox = new HBox();
        recentBox.setVisible(false);
        vBox.getChildren().add(recentBox);
        //master box
        recentBox.getStyleClass().add("recent-box");
        recentBox.setMaxWidth(350);
        recentBox.setMinHeight(100);
        recentBox.setAlignment(Pos.CENTER_LEFT);

        recentBox.setOnMouseClicked(e -> goToLesson(borderPane, student.getRecentChapter(), true));
        recentBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        recentBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        if (student.getRecentChapter() > -1) {
            recentBox.getChildren().add(SmartContinue.generate());
            recentBox.setVisible(true);
        }

        int numChapters = Main.lessonModel.getChapters().size();

        chapterBoxes = new ArrayList<>();

        //Progress, title, and text description
        //for each chapter
        for (int i = 0; i < numChapters; i++) {
            int chapterIndex = i;
            HBox chapterBox = new HBox();

            vBox.getChildren().add(chapterBox);
            chapterBoxes.add(chapterBox);

            Thread thread = new Thread(() -> buildChapterBox(chapterIndex, chapterBoxes.get(chapterIndex)));
            thread.start();
            threads.add(thread);
//            Platform.runLater(() -> buildChapterBox(chapterIndex, chapterBoxes.get(chapterIndex)));
        }
        //wait for the threads to finish, else we *sometimes* get a "Not on FX application thread" error. Only sometimes. And only on one page. Very weird bug.
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

         /*
        Settings box
         */
        settingsBox = new HBox();
        settingsBox.getStyleClass().add("settings-box");
        settingsBox.setMaxWidth(250);
        settingsBox.setMinHeight(50);
        settingsBox.setAlignment(Pos.CENTER_LEFT);

        settingsBox.setOnMouseClicked(e -> goToSettings(borderPane));
        settingsBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        settingsBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        settingsBox.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
            UIComponents.scale(settingsBox, 0.1,1.02);
        });
        settingsBox.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
            UIComponents.scale(settingsBox,0.1, 1);
        });

        //Left image
        ImageView imageView = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/Homepage/settings-black.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);

        //Text description
        Label description = new Label("Settings");
        description.setPrefWidth(350);
        description.setWrapText(true);
        description.getStyleClass().add("settings-text");
        description.setTextAlignment(TextAlignment.CENTER);

        settingsBox.getChildren().addAll(imageView, description);

        vBox.getChildren().add(settingsBox);

        masterVbox.getChildren().add(vBox);

        ScrollPane scrollPane = new ScrollPane();;
        scrollPane.getStyleClass().add("master-scrollpane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        StackPane contentHolder = new StackPane(masterVbox);
        contentHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        scrollPane.setContent(contentHolder);
        borderPane.setCenter(scrollPane);


//        Scene oldScene = Main.window.getScene();
//        Scene newScene = (oldScene == null ? new Scene(borderPane, Main.width, Main.height) : new Scene(borderPane, oldScene.getWidth(), oldScene.getHeight()))
    }

    private static String generateGreetingText() {
        StudentModel.Student student = Main.studentModel.getStudent(Main.username);
        Random rand = new Random();
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
        return greeting;
    }

    private void buildChapterBox(int chapterIndex, HBox chapterBox) {
        //master box
        chapterBox.getStyleClass().add("chapter-box");

        chapterBox.setOnMouseClicked(e -> goToLesson(borderPane, chapterIndex, false));
        chapterBox.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
            UIComponents.scale(chapterBox, 0.1,1.02);
        });
        chapterBox.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
            UIComponents.scale(chapterBox, 0.1,1);
        });

        //Left image
        ImageView imageView = new ImageView(Main.lessonModel.getChapter(chapterIndex).getImage());
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
        progressIndicator.setProgress(student.getChapter(chapterIndex).getProgress());
        ringProgressIndicators.add(progressIndicator);

        //Title
        Label title = new Label();
        title.getStyleClass().add("chapter-title-text");
        title.setTextAlignment(TextAlignment.RIGHT);
        title.setWrapText(true);
        title.setText(Main.lessonModel.getChapter(chapterIndex).getTitle());

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
        description.setText(Main.lessonModel.getChapter(chapterIndex).getDescription());

        chapterInfo.getChildren().addAll(topContent, description);

        chapterBox.getChildren().addAll(imageView, separator, chapterInfo);
    }

    /**
     * Move up and fade out at the same time the home page before going to the lesson page
     * @param node the desired node to fadeAndTranslate first
     * @param chapterIndex the desired chapter to switch scenes to
     */
    private static void goToLesson(Node node, int chapterIndex, boolean continueWhereLeftOff) {
        ParallelTransition parallelTransition = new ParallelTransition(getFadeTransition(node), getTranslateTransition(node));
        parallelTransition.play();
        parallelTransition.setOnFinished(e -> LessonPage.Page(chapterIndex, continueWhereLeftOff));
    }

    /**
     * Move up and fade out at the same time the home page before going to the lesson page
     * @param node the desired node to fadeAndTranslate first
     */
    private static void goToSettings(Node node) {
        ParallelTransition pt = new ParallelTransition(getFadeTransition(node), getTranslateTransition(node));
        pt.play();
        pt.setOnFinished(e -> SettingsPage.Page());
    }

    /**
     * Move up and fade out at the same time the current page before going to the home page
     * @param node the desired node to fadeAndTranslate first
     */
    public static void goHome(Node node) {
        ParallelTransition parallelTransition = new ParallelTransition(getFadeTransition(node), getTranslateTransition(node));
        parallelTransition.play();
        parallelTransition.setOnFinished(e -> getInstance().Page());
    }


    private static FadeTransition getFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        return fadeTransition;
    }

    private static TranslateTransition getTranslateTransition(Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), node);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10); //move up

        return translateTransition;
    }
}
