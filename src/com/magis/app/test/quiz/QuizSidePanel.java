package com.magis.app.test.quiz;

import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.UI.TestPageContent;
import com.magis.app.UI.UIComponents;
import com.magis.app.lesson.PageLabels;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class QuizSidePanel {
    private int chapterIndex;
    private ArrayList<QuizPageContent> quizPagesContent;
    private int numQuestions;
    private ScrollPane quizPageScrollPane;
    private TestPageContent testPageContent;
    private ScrollPane scrollPane;
    private VBox masterVBox;
    private VBox contentPagesVBox;
    private HBox currentPage;
    private int numQuizQuestionPages;
    private PageLabels pageLabels;
    private Rectangle verticalLine;
    private Rectangle horizontalLine;
    private int currentPageIndex;

    public QuizSidePanel(int chapterIndex, ArrayList<QuizPageContent> quizPages, int numQuestions, ScrollPane quizPageScrollPane, TestPageContent testPageContent) {
        this.chapterIndex = chapterIndex;
        this.quizPagesContent = quizPages;
        this.numQuestions = numQuestions;
        this.quizPageScrollPane = quizPageScrollPane;
        this.scrollPane = new ScrollPane();
        this.masterVBox = new VBox();
        this.pageLabels = new PageLabels(quizPages.size());
        this.testPageContent = testPageContent;
        verticalLine = new Rectangle();
        horizontalLine = new Rectangle();
        currentPageIndex = 0;
    }

    public Node getSidePanel() {
        return scrollPane;
    }

    public void initialize(boolean firstTime) {

        this.masterVBox = new VBox();

        Label chapterTitle = new Label(Main.lessonModel.getChapter(chapterIndex).getTitle());
        chapterTitle.getStyleClass().add("lesson-side-panel-chapter-text");
        chapterTitle.setMaxWidth(300);
        chapterTitle.setWrapText(true);

        horizontalLine.setHeight(2);
        horizontalLine.setWidth(250);
        horizontalLine.getStyleClass().add("lesson-rectangle");

        masterVBox.getChildren().addAll(chapterTitle, horizontalLine);

        masterVBox.setPadding(new Insets(50,0,0,15));
        masterVBox.setSpacing(10);

        this.contentPagesVBox = new VBox();
        this.currentPage = new HBox();
        contentPagesVBox.setSpacing(10);
        verticalLine.setHeight(25);
        verticalLine.setWidth(5);
        verticalLine.getStyleClass().add("lesson-rectangle");
        pageLabels.getLabel(0).setPadding(new Insets( 0, 0, 0, 10));
        if (firstTime) setLabelText(0);

        //listeners
        pageLabels.getLabel(0).setOnMouseClicked(e -> QuizPage.updatePage(0));
        pageLabels.getLabel(0).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(0).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add verticalLine and first page text to hbox
        currentPage.getChildren().addAll(verticalLine, pageLabels.getLabel(0));

        //add first page to the list of pages
        contentPagesVBox.getChildren().add(currentPage);

        for (int i = 1; i < pageLabels.getNumLabels(); i++) {
            int index = i;
            pageLabels.getLabel(i).setPadding(new Insets(0, 0, 0, 15));
            if (firstTime) setLabelText(i);

            //listeners
            pageLabels.getLabel(i).setOnMouseClicked(e -> QuizPage.updatePage(index));
            pageLabels.getLabel(i).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            pageLabels.getLabel(i).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            //add page to the list of pages
            contentPagesVBox.getChildren().add(pageLabels.getLabel(i));

            UIComponents.fadeAndTranslate(pageLabels.getLabel(i), i,0.2, 0.2,-10,0,0,0);
        }

        masterVBox.getChildren().add(contentPagesVBox);

        currentPageIndex = 0;
        scrollPane.setContent(masterVBox);
        JFXScrollPane.smoothScrolling(scrollPane);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("sidebar-scrollpane");
    }

    /**
     * Updates the position of the side marker that indicates which page the user is currently on
     * @param index
     */
    public void update(int index) {
        if (index == currentPageIndex) {
            return; //in case user clicked on current page
        }
        //update the variable the navigation buttons use
        QuizPage.currentPage = index;

        //choose whether or not to display the quiz submit button
        if (QuizPage.currentPage + 1 == QuizPage.numPages) {
            QuizPage.submitButton.setVisible(true);
        } else {
            QuizPage.submitButton.setVisible(false);
        }
        //update padding
        pageLabels.getLabel(currentPageIndex).setPadding(new Insets(0, 0, 0, 15));
        pageLabels.getLabel(index).setPadding(new Insets(0, 0, 0, 10));

        //remove nodes (by making new temporary nodes) so the program doesn't bark about duplicate nodes
        contentPagesVBox.getChildren().set(currentPageIndex, new Label());
        contentPagesVBox.getChildren().set(index, new Label());

        //move current page and current page indicator to new location
        contentPagesVBox.getChildren().set(index, currentPage);
        //set previous current page to just a label with no indicator
        contentPagesVBox.getChildren().set(currentPageIndex, pageLabels.getLabel(currentPageIndex));
        //update the current page indicator label with the correct label
        currentPage.getChildren().add(pageLabels.getLabel(index));

        currentPageIndex = index;
    }

    private void setLabelText(int index) {
        pageLabels.getLabel(index).setText("Page " + (index + 1));
    }

    public void insertCustomPage(int position, String pageName) {
        //create the new label
        Label newLabel = new Label();
        newLabel.setPadding(new Insets( 0, 0, 0, 10));
        newLabel.getStyleClass().add("lesson-side-panel-text");

        pageLabels.getLabels().add(position, newLabel);
        pageLabels.getLabel(position).setText(pageName);
        initialize(false);
    }
}
