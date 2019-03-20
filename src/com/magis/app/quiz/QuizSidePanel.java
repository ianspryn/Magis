package com.magis.app.quiz;

import com.magis.app.Main;
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
    private ArrayList<QuizPageContent> quizPages;
    private int numQuestions;
    private ScrollPane quizPageScrollPane;
    private VBox vBox;
    private HBox currentPage;
    private int numPages;
    private PageLabels pageLabels;
    private ImageView line;
    private int currentPageIndex;

    public QuizSidePanel(ArrayList<QuizPageContent> quizPages, int numQuestions, ScrollPane quizPageScrollPane) {
        this.quizPages = quizPages;
        this.numQuestions = numQuestions;
        this.quizPageScrollPane = quizPageScrollPane;
        this.vBox = new VBox();
        this.currentPage = new HBox();
        this.numPages = numQuestions / 2 + 1;
        this.pageLabels = new PageLabels(numPages);
        line = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/pink400.png");
        currentPageIndex = 0;
    }

    public Node getvBox() {
        return vBox;
    }

    public void initialize() {
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(200,0,0,15));
        line.setPreserveRatio(false);
        line.setFitHeight(25);
        line.setFitWidth(5);
        pageLabels.getLabel(0).setPadding(new Insets( 0, 0, 0, 10));
        pageLabels.getLabel(0).getStyleClass().add("lesson-side-panel-text");
        setLabelText(0);

        //listeners
        pageLabels.getLabel(0).setOnMouseClicked(e -> {
            quizPageScrollPane.setContent(quizPages.get(0).getPageContent());
            update(0);
        });
        pageLabels.getLabel(0).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(0).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add line and first page text to hbox
        currentPage.getChildren().addAll(line, pageLabels.getLabel(0));

        //add first page to the list of pages
        vBox.getChildren().add(currentPage);

        for (int i = 1; i < numPages; i++) {
            int index = i;
            pageLabels.getLabel(i).setPadding(new Insets(0, 0, 0, 15));
            pageLabels.getLabel(i).getStyleClass().add("lesson-side-panel-text");
            setLabelText(i);

            //listeners
            pageLabels.getLabel(i).setOnMouseClicked(e -> {
                quizPageScrollPane.setContent(quizPages.get(index).getPageContent());
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
    private void update(int index) {
        if (index == currentPageIndex) {
            return; //in case user clicked on current page
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
}
