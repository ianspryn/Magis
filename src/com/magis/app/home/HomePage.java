package com.magis.app.home;

import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.UI.IntelligentTutor;
import com.magis.app.UI.UIComponents;
import com.magis.app.icons.MaterialIcons;
import com.magis.app.login.Login;
import com.magis.app.models.StudentModel;
import com.magis.app.page.LessonPage;
import com.magis.app.UI.SettingsPage;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Random;

import static com.magis.app.UI.UIComponents.*;


public class HomePage {

    private static HomePage homePage = null;
    private static ScrollPane scrollPane;
    private  static Label greetingLabel;
    private ArrayList<HBox> chapterBoxes;
    private static ArrayList<RingProgressIndicator> ringProgressIndicators;
    private static HBox topBox;
    private static VBox statsBox;
    private static HBox recentBox;
    private HBox bottomBox;
    private HBox settingsBox;
    private HBox signOutBox;
    private VBox masterVbox;
    private VBox vBox;
    private StudentModel.Student student;

    private static String[] greetings = {"Hello:!", "Hey there:!", "Welcome:!", "Good day:!", "How goes it:?", "What's happening:?"};
    private static String[] codeGreetings = {"String message = \":\";", "System.out.println(\":\");"};

    public static HomePage getInstance() {
        if (homePage == null) homePage = new HomePage();
        else update();
        return homePage;
    }

    private static void update() {
        int numChapters = Main.lessonModel.getNumChapters();
        StudentModel.Student student = Main.studentModel.getStudent();
        for (int i = 0; i < numChapters; i++) ringProgressIndicators.get(i).setProgress(student.getChapter(i).getProgress());
        if (student.getRecentChapter() != -1) { //make sure we don't add to the recentBox until student visited a page
            if (!topBox.getChildren().contains(recentBox)) topBox.getChildren().add(0, recentBox); //add the recent box once
            recentBox.getChildren().clear();
            recentBox.getChildren().add(IntelligentTutor.generateRecentActivity());
        }
        greetingLabel.setText(generateGreetingText());
    }

    public void Page() {
        Main.setScene(scrollPane, "Home");
        animate();
    }

    /**
     * Since the home page fades out on every page exit (and the fact that it's an instance, not static and therefore and not regenerated),
     * we must fade everything back in if the user turns off animations.
     * Else, the homepage will remain invisible
     */
    public void disableAnimations() {
        UIComponents.fadeOnAndTranslate(masterVbox, 0, 0.2, 0, 0, -10, 0);
        UIComponents.fadeOnAndTranslate(greetingLabel, 0, 0.2, 0, 0, -10, 0);
        if (student.getRecentChapter() > -1) UIComponents.fadeOnAndTranslate(topBox, 0, 0.2, 0, 0, -10, 0);
        for (HBox chapterBox : chapterBoxes) UIComponents.fadeOnAndTranslate(chapterBox, 0, 0.2, 0, 0, -10, 0);
        UIComponents.fadeOnAndTranslate(bottomBox, 0,0.2,0,0,-10,0);
    }

    private void animate() {
        if (!Main.useAnimations) return;
        UIComponents.fadeOnAndTranslate(masterVbox, 0.3, 0.2, 0, 0, -10, 0);
        UIComponents.fadeOnAndTranslate(greetingLabel, 0.3, 0.2, 0, 0, -10, 0);
        if (student.getRecentChapter() > -1) UIComponents.fadeOnAndTranslate(topBox, 0.3, 0.2, 0, 0, -10, 0);
        for (int i = 0; i < chapterBoxes.size(); i++) UIComponents.fadeOnAndTranslate(chapterBoxes.get(i), i, 0.5, 0.2, 0, 0, -10, 0);
        UIComponents.fadeOnAndTranslate(bottomBox, chapterBoxes.size(), 0.5,0.2,0,0,-10,0);
    }

    private HomePage() {
        student = Main.studentModel.getStudent();
        ringProgressIndicators = new ArrayList<>();
        /*
        Master
         */

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

        /*
        Last Activity and statistics box
         */

        //Last Activity
        recentBox = new HBox();
        recentBox.getStyleClass().add("recent-stats-box");
        recentBox.setMaxWidth(350);
        recentBox.setMinHeight(100);

        recentBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        recentBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        if (student.getRecentChapter() > -1) {
            recentBox.getChildren().add(IntelligentTutor.generateRecentActivity());
            recentBox.setOnMouseClicked(e -> goToLesson(masterVbox, IntelligentTutor.getNewChapter(), IntelligentTutor.getNewPage()));
        }

        //Activity and Statistics Page
        statsBox = new VBox();
        statsBox.getStyleClass().add("recent-stats-box");
        statsBox.setMaxWidth(350);
        statsBox.setPrefWidth(350);
        statsBox.setMinHeight(100);
        statsBox.setAlignment(Pos.TOP_LEFT);

        Label statsTitle = new Label("Statistics");
        statsTitle.getStyleClass().add("box-title");
        statsTitle.setWrapText(true);

        Label statsText = new Label("Click here to view statistics, insights, feedback, history, and more.");
        statsText.getStyleClass().add("box-description");
        statsText.setPadding(new Insets(25,0,0,0));
        statsText.setWrapText(true);
        statsText.setTextAlignment(TextAlignment.LEFT);

        statsBox.getChildren().addAll(statsTitle, statsText);

        statsBox.setOnMouseClicked(e -> goToStats(masterVbox));
        statsBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        statsBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //Main box for last activity and statistics page
        topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setSpacing(50);
        if (Main.studentModel.getStudent().getRecentChapter() > -1) topBox.getChildren().add(recentBox);
        topBox.getChildren().add(statsBox);
        vBox.getChildren().add(topBox);


        int numChapters = Main.lessonModel.getChapters().size();

        chapterBoxes = new ArrayList<>();

        //Progress, title, and text description
        //for each chapter
        for (int i = 0; i < numChapters; i++) {
            HBox chapterBox = new HBox();

            vBox.getChildren().add(chapterBox);
            chapterBoxes.add(chapterBox);

            buildChapterBox(i, chapterBoxes.get(i));
        }

        /*
        Bottom box
         */
        bottomBox = new HBox();
        bottomBox.setSpacing(25);
        bottomBox.setAlignment(Pos.CENTER);

         /*
        Settings box
         */
        settingsBox = new HBox();
        settingsBox.getStyleClass().add("small-box");
        settingsBox.setMaxWidth(250);
        settingsBox.setMinHeight(75);
        settingsBox.setAlignment(Pos.CENTER_LEFT);

        settingsBox.setOnMouseClicked(e -> goToSettings(masterVbox));
        settingsBox.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
            UIComponents.scale(settingsBox, 0.1,1.02,1.02);
        });
        settingsBox.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
            UIComponents.scale(settingsBox,0.1, 1,1);
        });

        //Left image
        HBox settingsIconContainer = new HBox();
        settingsIconContainer.setPadding(new Insets(7,12,0,20));

        SVGPath settingsIcon = new SVGPath();
        settingsIcon.getStyleClass().add("icon-no-color");
        settingsIcon.setContent(MaterialIcons.SETTINGS);
        // scale to size 350x350
        Bounds settingsBounds = settingsIcon.getBoundsInLocal();
        double settingsScaleFactor = 50 / Math.max(settingsBounds.getWidth(), settingsBounds.getHeight());
        settingsIcon.setScaleX(settingsScaleFactor);
        settingsIcon.setScaleY(settingsScaleFactor);

        settingsIconContainer.getChildren().add(settingsIcon);

        //Text description
        Label settingsDescription = new Label("Settings");
        settingsDescription.setPrefWidth(350);
        settingsDescription.setWrapText(true);
        settingsDescription.getStyleClass().add("bottom-home-text");
        settingsDescription.setTextAlignment(TextAlignment.CENTER);

        settingsBox.getChildren().addAll(settingsIconContainer, settingsDescription);

         /*
        Sign out box
         */
        signOutBox = new HBox();
        signOutBox.getStyleClass().add("small-box");
        signOutBox.setMaxWidth(250);
        signOutBox.setMinHeight(75);
        signOutBox.setAlignment(Pos.CENTER);

        signOutBox.setOnMouseClicked(e -> signOut(masterVbox));
        signOutBox.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
            UIComponents.scale(signOutBox, 0.1,1.02,1.02);
        });
        signOutBox.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
            UIComponents.scale(signOutBox,0.1, 1,1);
        });

        //Left image
        HBox signOutContainer = new HBox();
        signOutContainer.setPadding(new Insets(7,12,0,20));

        SVGPath personIcon = new SVGPath();
        personIcon.getStyleClass().add("icon-no-color");
        personIcon.setContent(MaterialIcons.PERSON);
        // scale to size 350x350
        Bounds signOutBounds = personIcon.getBoundsInLocal();
        double signOutScaleFactor = 40 / Math.max(signOutBounds.getWidth(), signOutBounds.getHeight());
        personIcon.setScaleX(signOutScaleFactor);
        personIcon.setScaleY(signOutScaleFactor);

        signOutContainer.getChildren().add(personIcon);

        //Text description
        Label signOutDescription = new Label("Sign out");
        signOutDescription.setPrefWidth(350);
        signOutDescription.setWrapText(true);
        signOutDescription.getStyleClass().add("bottom-home-text");
        signOutDescription.setTextAlignment(TextAlignment.CENTER);

        signOutBox.getChildren().addAll(signOutContainer, signOutDescription);

        /*
        Add to the bottom box
         */
        bottomBox.getChildren().addAll(settingsBox, signOutBox);

        vBox.getChildren().add(bottomBox);

        masterVbox.getChildren().add(vBox);

        scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("master-scrollpane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        StackPane contentHolder = new StackPane(masterVbox);
        contentHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        scrollPane.setContent(contentHolder);
        JFXScrollPane.smoothScrolling(scrollPane);
    }

    private static String generateGreetingText() {
        StudentModel.Student student = Main.studentModel.getStudent();
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
        chapterBox.getStyleClass().addAll("chapter-box", "home-chapter-box");

        chapterBox.setOnMouseClicked(e -> goToLesson(masterVbox, chapterIndex));
        chapterBox.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
            UIComponents.scale(chapterBox, 0.1,1.02,1.02);
        });
        chapterBox.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
            UIComponents.scale(chapterBox, 0.1,1,1);
        });

        //Left image
        ImageView imageView = new ImageView();
        Thread thread = new Thread(() -> imageView.setImage(new Image(Main.lessonModel.getChapter(chapterIndex).getImage()))); //load images in the background
        thread.setDaemon(true);
        thread.start();
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
        title.getStyleClass().add("box-title");
        title.setTextAlignment(TextAlignment.RIGHT);
        title.setWrapText(true);
        title.setText(Main.lessonModel.getChapter(chapterIndex).getTitle() + " - " + (chapterIndex + 1));

        AnchorPane topContent = new AnchorPane();
        topContent.getChildren().addAll(progressIndicator, title);
        topContent.setLeftAnchor(progressIndicator, 0.0);
        topContent.setRightAnchor(title, 5.0);

        //Text description
        Label description = new Label();
        description.setPrefWidth(550);
        description.setWrapText(true);
        description.getStyleClass().add("box-description");
        description.setTextAlignment(TextAlignment.LEFT);
        description.setText(Main.lessonModel.getChapter(chapterIndex).getDescription());

        chapterInfo.getChildren().addAll(topContent, description);

        chapterBox.getChildren().addAll(imageView, separator, chapterInfo);
    }

    /**
     * Move up and fade out at the same time the home page before going to the statistics page
     * @param node the desired node to fadeOnAndTranslate first
     */
    private void goToStats(Node node) {
        if (Main.useAnimations) {
            transitionPage(node).setOnFinished(e -> StatsPage.Page());
        } else {
            StatsPage.Page();
        }
    }

    /**
     * Move up and fade out at the same time the home page before going to the lesson page
     * @param node the desired node to fadeOnAndTranslate first
     * @param chapterIndex the desired chapter to switch scenes to
     */
    private static void goToLesson(Node node, int chapterIndex) {
        if (Main.useAnimations) {
            transitionPage(node).setOnFinished(e -> new LessonPage(chapterIndex));
        } else {
            new LessonPage(chapterIndex);
        }
    }

    /**
     * Move up and fade out at the same time the home page before going to the lesson page
     * @param node the desired node to fadeOnAndTranslate first
     * @param chapterIndex the desired chapter to switch scenes to
     * @param page the page within the chapter to navigate to
     */
    private static void goToLesson(Node node, int chapterIndex, int page) {
        if (Main.useAnimations) {
            transitionPage(node).setOnFinished(e -> new com.magis.app.page.LessonPage(chapterIndex, page));
        } else {
            new com.magis.app.page.LessonPage(chapterIndex, page);
        }
    }

    /**
     * Move up and fade out at the same time the home page before going to the lesson page
     * @param node the desired node to fadeOnAndTranslate first
     */
    private static void goToSettings(Node node) {
        if (Main.useAnimations) {
            transitionPage(node).setOnFinished(e -> SettingsPage.Page());
        } else {
            SettingsPage.Page();
        }
    }

    /**
     * Move up and fade out at the same time the current page before going to the home page
     * @param node the desired node to fadeOnAndTranslate first
     */
    public static void goHome(Node node) {
        if (Main.useAnimations) {
            transitionPage(node).setOnFinished(e -> getInstance().Page());
        } else {
            getInstance().Page();
        }
    }

    /**
     * Sign out of the app
     * @param node the node to transition and fade out before go to the sign in page
     */
    private void signOut(Node node) {

        if (Main.useAnimations) {
            transitionPage(node).setOnFinished(e -> Login.Page());
        } else {
            Login.Page();
        }
        /*
        Reset stuff
         */
        //Delete student from the class
        Main.studentModel.removeStudent();
        Main.studentModel = new StudentModel(Main.lessonModel);
        //remove the student's custom styling
        Main.scene.getStylesheets().removeAll();
        //default to light, with pink
        Main.scene.getStylesheets().addAll("com/magis/app/css/style.css", "com/magis/app/css/lightmode.css", "com/magis/app/css/pink.css");
        Main.isLoggedIn = false;
        Main.useAnimations = true;
    }
}
