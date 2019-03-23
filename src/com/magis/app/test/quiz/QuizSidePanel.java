package com.magis.app.test.quiz;

import com.magis.app.Main;
import com.magis.app.UI.TestPageContent;
import com.magis.app.lesson.PageLabels;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class QuizSidePanel {
    private ArrayList<QuizPageContent> quizPagesContent;
    private int numQuestions;
    private ScrollPane quizPageScrollPane;
    private TestPageContent testPageContent;
    private VBox vBox;
    private HBox currentPage;
    private int numQuizQuestionPages;
    private PageLabels pageLabels;
    private ImageView line;
    private int currentPageIndex;

    public QuizSidePanel(ArrayList<QuizPageContent> quizPages, int numQuestions, ScrollPane quizPageScrollPane, TestPageContent testPageContent) {
        this.quizPagesContent = quizPages;
        this.numQuestions = numQuestions;
        this.quizPageScrollPane = quizPageScrollPane;
        this.vBox = new VBox();
        this.currentPage = new HBox();
        this.pageLabels = new PageLabels(quizPages.size());
        this.testPageContent = testPageContent;
        line = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/pink400.png");
        currentPageIndex = 0;
    }

    public Node getvBox() {
        return vBox;
    }

    public void initialize(boolean firstTime) {

        this.vBox = new VBox();
        this.currentPage = new HBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(80,0,0,15));
        line.setPreserveRatio(false);
        line.setFitHeight(25);
        line.setFitWidth(5);
        pageLabels.getLabel(0).setPadding(new Insets( 0, 0, 0, 10));
        if (firstTime) {
            setLabelText(0);
        }

        //listeners
        pageLabels.getLabel(0).setOnMouseClicked(e -> {
            quizPageScrollPane.setContent(testPageContent.getPageContent(0));
            update(0);
        });
        pageLabels.getLabel(0).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(0).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add line and first page text to hbox
        currentPage.getChildren().addAll(line, pageLabels.getLabel(0));

        //add first page to the list of pages
        vBox.getChildren().add(currentPage);

        for (int i = 1; i < pageLabels.getNumLabels(); i++) {
            int index = i;
            pageLabels.getLabel(i).setPadding(new Insets(0, 0, 0, 15));
            if (firstTime) {
                setLabelText(i);
            }

            //listeners
            pageLabels.getLabel(i).setOnMouseClicked(e -> {
                quizPageScrollPane.setContent(testPageContent.getPageContent(index));
                update(index);
            });
            pageLabels.getLabel(i).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            pageLabels.getLabel(i).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            //add page to the list of pages
            vBox.getChildren().add(pageLabels.getLabel(i));
        }
        currentPageIndex = 0;
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
        vBox.getChildren().set(currentPageIndex, new Label());
        vBox.getChildren().set(index, new Label());

        //move current page and current page indicator to new location
        vBox.getChildren().set(index, currentPage);
        //set previous current page to just a label with no indicator
        vBox.getChildren().set(currentPageIndex, pageLabels.getLabel(currentPageIndex));
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
