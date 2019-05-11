package com.magis.app.home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.UI.IntelligentTutor;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.UI.UIComponents;
import com.magis.app.models.StudentModel;
import com.magis.app.page.HistoryExamPage;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class StatsPage {

    private static StudentModel.Student student;

    private static StackPane master;
    private static ScrollPane scrollPane;
    private static VBox mastervBox;

    /**
     * The Main page for the stats page
     */
    public static void Page() {
        student = Main.studentModel.getStudent();

        UIComponents.GenericPage page = new UIComponents.GenericPage();
        master = page.getMaster();
        scrollPane = page.getScrollPane();
        scrollPane.setVvalue(0); //reset to top every time
        page.getMastervBox().setAlignment(Pos.TOP_CENTER);
        mastervBox = page.getMastervBox();
        page.getBackButton().setOnMouseClicked(e -> HomePage.goHome(scrollPane));
        page.getPageTitle().setText("Statistics");


        /*
        Overall Progress
         */
        Label overallProgressLabel = new Label("Overall Progress");
        overallProgressLabel.getStyleClass().add("section-title");

        HBox progressIndicatorContainer = new HBox();
        progressIndicatorContainer.setAlignment(Pos.CENTER);
        progressIndicatorContainer.setPadding(new Insets(50, 0, 0, 0));

        RingProgressIndicator progressIndicator = new RingProgressIndicator();
        progressIndicator.setScaleX(2);
        progressIndicator.setScaleY(2);
        progressIndicator.setProgress(calculateOverallProgress());

        progressIndicatorContainer.getChildren().add(progressIndicator);
        mastervBox.getChildren().addAll(overallProgressLabel, progressIndicatorContainer);

        /*
        Insights
         */
        Label insightsLabel = new Label("Insights");
        insightsLabel.getStyleClass().add("section-title");
        insightsLabel.setPadding(new Insets(50, 0, 25, 0));
        VBox insightsvBox = IntelligentTutor.generateInsights();

        mastervBox.getChildren().addAll(insightsLabel, insightsvBox);

        /*
        Each chapter's statistics
         */
        Label chaptersLabel = new Label("Chapters");
        chaptersLabel.getStyleClass().add("section-title");
        chaptersLabel.setPadding(new Insets(50, 0, 25, 0));
        VBox chapterBoxes = new VBox();
        chapterBoxes.setAlignment(Pos.CENTER);
        chapterBoxes.setSpacing(25);
        chapterBoxes.setMaxWidth(900);
        int numChapters = Main.lessonModel.getNumChapters();
        for (int i = 0; i < numChapters; i += 3) {
            HBox row = new HBox();
            row.setSpacing(25);
            for (int j = i, counter = 0; counter < 3 && j < numChapters; j++, counter++) {
                VBox chapterBox = buildChapterBox(j);
                UIComponents.fadeOnAndTranslate(chapterBox, j + 0.2, 0, 0, 0, -10, 0);
                row.getChildren().add(chapterBox);
            }
            chapterBoxes.getChildren().add(row);
        }
        mastervBox.getChildren().addAll(chaptersLabel, chapterBoxes);

        Main.setScene(master, "Statistics");
    }

    /**
     * A page to display insights on a selected chapter
     */
    private static void ChapterPage(int chapterIndex) {
         /*
        Middle
         */

        UIComponents.GenericPage page = new UIComponents.GenericPage();
        master = page.getMaster();
        scrollPane = page.getScrollPane();
        scrollPane.setVvalue(0); //reset to top every time
        page.getMastervBox().setAlignment(Pos.TOP_CENTER);
        mastervBox = page.getMastervBox();
        mastervBox.setAlignment(Pos.TOP_CENTER);
        page.getBackButton().setOnMouseClicked(e -> goToStats(scrollPane));
        page.getPageTitle().setText("Chapter " + (chapterIndex + 1) + " - " + Main.lessonModel.getChapter(chapterIndex).getTitle());

        /*
        Overall Progress
         */
        Label overallProgressLabel = new Label("Overall Chapter Progress");
        overallProgressLabel.getStyleClass().addAll("box-title", "text-color");

        HBox progressIndicatorContainer = new HBox();
        progressIndicatorContainer.setAlignment(Pos.CENTER);
        progressIndicatorContainer.setPadding(new Insets(25, 0, 0, 0));

        RingProgressIndicator progressIndicator = new RingProgressIndicator();
        progressIndicator.setScaleX(1.5);
        progressIndicator.setScaleY(1.5);
        progressIndicator.setProgress(calculateProgress(chapterIndex));

        progressIndicatorContainer.getChildren().add(progressIndicator);
        mastervBox.getChildren().addAll(overallProgressLabel, progressIndicatorContainer);

        /*
        Insights
         */
        Label insightsLabel = new Label("Insights");
        insightsLabel.getStyleClass().add("section-title");
        insightsLabel.setPadding(new Insets(50, 0, 25, 0));

        mastervBox.getChildren().addAll(insightsLabel);

        VBox insightsVBox = new VBox();
        insightsVBox.getStyleClass().addAll("chapter-box");
        insightsVBox.setPadding(new Insets(30, 100, 30, 100));
        insightsVBox.setMaxWidth(900);
        insightsVBox.setAlignment(Pos.CENTER);

        TextFlow readingProgress = new TextFlow();
        readingProgress.setTextAlignment(TextAlignment.CENTER);

        Text readingText1 = new Text("You are ");
        readingText1.getStyleClass().addAll("text-no-color", "box-title");
        Text readingText2 = new Text(student.getChapter(chapterIndex).getProgress() + "%");
        readingText2.getStyleClass().addAll("text-color", "box-title");
        Text readingText3 = new Text(" finished reading this chapter.");
        readingText3.getStyleClass().addAll("text-no-color", "box-title");

        readingProgress.getChildren().addAll(readingText1, readingText2, readingText3);

        insightsVBox.getChildren().add(readingProgress);

        /*
        Quizzes
         */
        if (student.hasTakenQuiz(chapterIndex)) { //student has taken a quiz
            HBox dividerContainer = createDivider();
            insightsVBox.getChildren().add(dividerContainer);
            VBox quizBox = new VBox();
            quizBox.setAlignment(Pos.CENTER);
            quizBox.setSpacing(25);

            Label quizLabel = new Label("Your Quiz Results. Tap to view your previous answers");
            quizLabel.setWrapText(true);
            quizLabel.getStyleClass().add("box-title");
            quizBox.getChildren().add(quizLabel);

            int counter = 0;
            for (StudentModel.Student.Attempt attempt : student.getQuiz(chapterIndex).getAttempts()) {
                HBox quiz = new HBox();
                quiz.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
                quiz.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
                int index = counter;
                quiz.setOnMouseClicked(e -> goToHistoryExam(scrollPane, chapterIndex, index, "quiz"));
                quiz.setOnMouseEntered(e -> {
                    Main.scene.setCursor(Cursor.HAND);
                    UIComponents.scale(quiz, 0.1, 1.02, 1.02);
                });
                quiz.setOnMouseExited(e -> {
                    Main.scene.setCursor(Cursor.DEFAULT);
                    UIComponents.scale(quiz, 0.1, 1, 1);
                });

                quiz.setSpacing(50);
                quiz.getStyleClass().addAll("inner-chapter-box", "stats-box");

                VBox score = new VBox();
                score.setAlignment(Pos.CENTER);

                RingProgressIndicator rpi = new RingProgressIndicator();
                rpi.setProgress(student.getQuiz(chapterIndex).getScores().get(counter).intValue());
                Label scoreLabel = new Label("Score");
                scoreLabel.getStyleClass().addAll("text-color", "box-description");

                score.getChildren().addAll(rpi, scoreLabel);

                Label timestamp = new Label("Taken on " + attempt.getTimestamp());
                timestamp.getStyleClass().addAll("text-no-color", "box-description");

                quiz.getChildren().addAll(score, timestamp);
                quizBox.getChildren().add(quiz);
                counter++;
            }
            insightsVBox.getChildren().add(quizBox);
        } else if (Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle())) { //student has yet to take quiz
            HBox dividerContainer = createDivider();
            insightsVBox.getChildren().add(dividerContainer);

            Label quizLabel = new Label("You haven't taken a quiz yet.");
            quizLabel.setWrapText(true);
            quizLabel.getStyleClass().add("box-title");

            Label comeBack = new Label("Come back when you've taken your quiz!");
            comeBack.setWrapText(true);
            comeBack.getStyleClass().add("insight-text");

            insightsVBox.getChildren().addAll(quizLabel, comeBack);
        }

        /*
        Tests
         */
        if (student.hasTakenTest(chapterIndex)) { //student has taken a test
            HBox dividerContainer = createDivider();
            insightsVBox.getChildren().add(dividerContainer);
            VBox testBox = new VBox();
            testBox.setAlignment(Pos.CENTER);
            testBox.setSpacing(25);

            Label testLabel = new Label("Your Test Results. Tap to view your previous answers");
            testLabel.setWrapText(true);
            testLabel.getStyleClass().add("box-title");
            testBox.getChildren().add(testLabel);

            int counter = 0;
            for (StudentModel.Student.Attempt attempt : student.getTest(chapterIndex).getAttempts()) {
                HBox test = new HBox();
                int index = counter;
                test.setOnMouseClicked(e -> goToHistoryExam(scrollPane, chapterIndex, index, "test"));
                test.setOnMouseEntered(e -> {
                    Main.scene.setCursor(Cursor.HAND);
                    UIComponents.scale(test, 0.1, 1.02, 1.02);
                });
                test.setOnMouseExited(e -> {
                    Main.scene.setCursor(Cursor.DEFAULT);
                    UIComponents.scale(test, 0.1, 1, 1);
                });
                test.setSpacing(50);
                test.getStyleClass().addAll("chapter-box", "stats-box");

                VBox score = new VBox();
                score.setAlignment(Pos.CENTER);

                RingProgressIndicator rpi = new RingProgressIndicator();
                rpi.setProgress(student.getQuiz(chapterIndex).getScores().get(counter).intValue());
                Label scoreLabel = new Label("Score");
                scoreLabel.getStyleClass().addAll("text-color", "box-description");

                score.getChildren().addAll(rpi, scoreLabel);

                Label timestamp = new Label("Taken on " + attempt.getTimestamp());
                timestamp.setWrapText(true);
                timestamp.getStyleClass().addAll("text-no-color", "box-description");

                test.getChildren().addAll(score, timestamp);
                testBox.getChildren().add(test);
                counter++;
            }
            insightsVBox.getChildren().add(testBox);
        } else if (Main.testsModel.hasTest(Main.lessonModel.getChapter(chapterIndex).getTitle())) { //student has yet to take test
            HBox dividerContainer = createDivider();
            insightsVBox.getChildren().add(dividerContainer);

            Label testLabel = new Label("You haven't taken a test yet.");
            testLabel.setWrapText(true);
            testLabel.getStyleClass().add("box-title");

            Label comeBack = new Label("Come back when you've taken your test!");
            comeBack.setWrapText(true);
            comeBack.getStyleClass().add("insight-text");

            insightsVBox.getChildren().addAll(testLabel, comeBack);
        }

        mastervBox.getChildren().add(insightsVBox);
        Main.setScene(master);
    }

    private static HBox createDivider() {
        HBox dividerContainer = new HBox();
        dividerContainer.setAlignment(Pos.CENTER);
        dividerContainer.setPadding(new Insets(25, 0, 25, 0));

        Rectangle divider = new Rectangle();
        divider.getStyleClass().add("rectangle-color");
        divider.setWidth(250);
        divider.setHeight(1);

        dividerContainer.getChildren().add(divider);

        return dividerContainer;
    }

    /**
     * Build the chapter box to house the title, completion and scores. Also configure its clicking action
     * @param chapterIndex the chapter's index
     * @return a build vBox for the chapter
     */
    private static VBox buildChapterBox(int chapterIndex) {
        /*
        parent's maxWidth = 900
        spacing = 25
        There are 3 boxes per line
        (900 - (2 * 25)) / 3 = 283.33
         */
        int maxWidth = 283;
        VBox chapterBox = new VBox();
        chapterBox.setAlignment(Pos.CENTER);
        chapterBox.setPrefWidth(maxWidth);
        chapterBox.getStyleClass().addAll("chapter-box", "stats-box");
//        chapterBox.setAlignment(Pos.CENTER);
        chapterBox.setOnMouseClicked(e -> goToChapterInsights(scrollPane, chapterIndex));
        chapterBox.setOnMouseEntered(e -> {
            Main.scene.setCursor(Cursor.HAND);
            UIComponents.scale(chapterBox, 0.1, 1.02, 1.02);
        });
        chapterBox.setOnMouseExited(e -> {
            Main.scene.setCursor(Cursor.DEFAULT);
            UIComponents.scale(chapterBox, 0.1, 1, 1);
        });

        AnchorPane content = new AnchorPane();
        content.setMaxWidth(maxWidth);
        content.setPrefHeight(100);

        //title
        Label chapterTitle = new Label();
        chapterTitle.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT);
        chapterTitle.setWrapText(true);
        chapterTitle.getStyleClass().add("stats-box-title");
        chapterTitle.setText("Chapter " + (chapterIndex + 1) + " - " + Main.lessonModel.getChapter(chapterIndex).getTitle());

        chapterBox.getChildren().addAll(chapterTitle, content);

        /*
        Progress
         */
        HBox progress = new HBox();
        progress.setAlignment(Pos.CENTER);
        progress.setSpacing(25);
        //reading progress
        VBox readingProgress = new VBox();
        readingProgress.setAlignment(Pos.CENTER);

        RingProgressIndicator rpi1 = new RingProgressIndicator();
        rpi1.setProgress(student.getChapter(chapterIndex).getProgress());

        Label readingProgressLabel = new Label("Reading");
        readingProgress.getStyleClass().add("box-description");

        readingProgress.getChildren().addAll(rpi1, readingProgressLabel);

        progress.getChildren().add(readingProgress);

        //best quiz score (if there is one)
        if (student.hasTakenQuiz(chapterIndex) && student.getQuiz(chapterIndex).getBestScore() > -1) {
            VBox quizScore = new VBox();
            quizScore.setAlignment(Pos.CENTER);

            RingProgressIndicator rpi2 = new RingProgressIndicator();
            rpi2.setProgress(student.getQuiz(chapterIndex).getBestScore().intValue());

            Label quizScoreLabel = new Label("Quiz Score");
            quizScoreLabel.setWrapText(true);

            quizScore.getChildren().addAll(rpi2, quizScoreLabel);

            progress.getChildren().add(quizScore);
        }

        //best test score (if there is one)
        if (student.hasTakenTest(chapterIndex) && student.getTest(chapterIndex).getBestScore() > -1) {
            VBox testScore = new VBox();
            testScore.setAlignment(Pos.CENTER);

            RingProgressIndicator rpi2 = new RingProgressIndicator();
            rpi2.setProgress(student.getQuiz(chapterIndex).getBestScore().intValue());

            Label testScoreLabel = new Label("Test Score");
            testScoreLabel.setWrapText(true);

            testScore.getChildren().addAll(rpi2, testScoreLabel);

            progress.getChildren().add(testScore);
        }

        content.getChildren().add(progress);

        AnchorPane.setTopAnchor(chapterTitle, 0.0);
        AnchorPane.setBottomAnchor(progress, 0.0);

        return chapterBox;
    }


    public static int calculateOverallProgress() {
        int progress = 0;
        int numChapters = Main.lessonModel.getNumChapters();
        for (int i = 0; i < numChapters; i++) {
            progress += calculateProgress(i);
        }
        return progress / numChapters;
    }

    private static int calculateProgress(int chapterIndex) {
        String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
        double chapterProgress = 0;
        double scale = 1; //scale to take into account if a quiz/test has been taken
        chapterProgress += Main.studentModel.getStudent().getChapter(chapterIndex).getProgress();
        //if there exists a quiz for this chapter
        if (Main.quizzesModel.hasQuiz(chapterTitle)) {
            //if the student has taken the quiz, then add it to the progress
            if (Main.studentModel.getStudent().hasTakenQuiz(chapterIndex)) {
                chapterProgress += 10; //quiz counts as 10% of the chapter's progress
            }
            scale += 0.1; //scale back by 1.1 because the quiz counts as 10%
        }
        //if there exists a test for this chapter
        if (Main.testsModel.hasTest(chapterTitle)) {
            //if the student has taken the test, then add it to the progress
            if (Main.studentModel.getStudent().hasTakenTest(chapterIndex)) {
                chapterProgress += 20; //test counts as 10% of the chapter's progress
            }
            scale += 0.2; //scale back by 1.2 because the test counts as 20%
        }
        chapterProgress = Math.round(chapterProgress / scale);
        return (int) chapterProgress;
    }

    /**
     * Move up and fade out at the same time the main page before going to the desired chapter's insights page
     *
     * @param node the desired node to fadeOnAndTranslate first
     * @chapterIndex the chapter index
     * @index the index of the exam attempt
     * @type the type of exam (test or quiz)
     * @param chapterIndex the desired chapter to switch scenes to
     */
    private static void goToHistoryExam(Node node, int chapterIndex, int index, String type) {
        if (Main.useAnimations) {
            UIComponents.transitionPage(node).setOnFinished(e -> new HistoryExamPage(chapterIndex, index, type));
        } else {
            new HistoryExamPage(chapterIndex, index, type);
        }
    }

    /**
     * Move up and fade out at the same time the main page before going to the desired chapter's insights page
     *
     * @param node the desired node to fadeOnAndTranslate first
     * @param chapterIndex the desired chapter to switch scenes to
     */
    public static void goToChapterInsights(Node node, int chapterIndex) {
        if (Main.useAnimations) {
            UIComponents.transitionPage(node).setOnFinished(e -> ChapterPage(chapterIndex));
        } else {
            ChapterPage(chapterIndex);
        }
    }

    /**
     * Move up and fade out at the same time the chapter insight page before going to the main page
     *
     * @param node the desired node to fadeOnAndTranslate first
     */
    private static void goToStats(Node node) {
        if (Main.useAnimations) {
            UIComponents.transitionPage(node).setOnFinished(e -> Page());
        } else {
            Page();
        }
    }
}
