package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.models.LessonModel;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class LessonSidePanel {

    private int chapterIndex;
    private LessonPageContent lessonPageContent;
    private ArrayList<LessonModel.ChapterModel.PageModel> pages;
    private int hasQuiz;
    private int hasTest;
    private ScrollPane scrollPane;
    private VBox masterVBox;
    private VBox contentPagesVBox;
    private HBox currentPage;
    private PageLabels pageLabels;
    private ImageView verticalLine;
    private ImageView horizontalLine;

    private int currentPageIndex;


    public LessonSidePanel(int chapterIndex, LessonPageContent lessonPageContent, ArrayList<LessonModel.ChapterModel.PageModel> pages, int hasQuiz, int hasTest) {
        this.chapterIndex = chapterIndex;
        this.lessonPageContent = lessonPageContent;
        this.pages = pages;
        scrollPane = new ScrollPane();
        masterVBox = new VBox();
        contentPagesVBox = new VBox();
        currentPage = new HBox();
        pageLabels = new PageLabels(pages.size() + hasQuiz + hasTest);
        this.hasQuiz = hasQuiz;
        this.hasTest = hasTest;
        verticalLine = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/pink400.png");
        horizontalLine = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/pink400.png");
        currentPageIndex = 0;
    }

    public ScrollPane getSidePanel() {
        return scrollPane;
    }

    public void initialize() {
        Label chapterTitle = new Label(Main.lessonModel.getChapter(chapterIndex).getTitle());
        chapterTitle.getStyleClass().add("lesson-side-panel-chapter-text");
        chapterTitle.setMaxWidth(300);
        chapterTitle.setWrapText(true);

        horizontalLine.setPreserveRatio(false);
        horizontalLine.setFitHeight(2);
        horizontalLine.setFitWidth(250);

        UIComponents.animate(chapterTitle, 0.15, 0.2,-10,0,0,0);
        UIComponents.animate(horizontalLine, 0.15, 0.2,-10,0,0,0);

        masterVBox.getChildren().addAll(chapterTitle, horizontalLine);

        masterVBox.setPadding(new Insets(50,0,0,15));
        masterVBox.setSpacing(10);
        contentPagesVBox.setSpacing(10);

        verticalLine.setPreserveRatio(false);
        verticalLine.setFitHeight(25);
        verticalLine.setFitWidth(5);



        pageLabels.getLabel(0).setPadding(new Insets( 0, 0, 0, 10));
        pageLabels.getLabel(0).getStyleClass().add("studentlesson-side-panel-text");
        getAndSetLabelText(0);

        //listeners
        pageLabels.getLabel(0).setOnMouseClicked(e -> {
            lessonPageContent.update(0);
            update(0);
        });
        pageLabels.getLabel(0).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(0).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add verticalLine and first page text to hbox
        currentPage.getChildren().addAll(verticalLine, pageLabels.getLabel(0));

        UIComponents.animate(currentPage,0.2,0.2,-10,0,0,0);

        //add first page to the list of pages
        contentPagesVBox.getChildren().add(currentPage);

        for (int i = 1; i < pages.size(); i++) {
            configureLabel(i);
            getAndSetLabelText(i);
        }
        masterVBox.getChildren().add(contentPagesVBox);
        currentPageIndex = 0;

        //if there exists a quiz for this chapter
        if (hasQuiz > 0) {
            //then add it to the side lessonSidePanel
            configureLabel(pages.size()); //the index where the quiz will be
            pageLabels.getLabel(pages.size()).setText("Quiz");
        }
        //if there exists a test for this chapter
        if (hasTest > 0) {
            //then add it to the side lessonSidePanel
            configureLabel(pages.size() + hasQuiz); //the index where the test will be
            pageLabels.getLabel(pages.size() + hasQuiz).setText("Test");
        }

        scrollPane.setContent(masterVBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("sidebar-scrollpane");
    }

    private void configureLabel(int index) {
        pageLabels.getLabel(index).setPadding(new Insets(0, 0, 0, 15));
        pageLabels.getLabel(index).getStyleClass().add("lesson-side-panel-text");

        //listeners
        pageLabels.getLabel(index).setOnMouseClicked(e -> {
            lessonPageContent.update(index);
            update(index);
        });
        pageLabels.getLabel(index).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(index).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add page to the list of pages
        contentPagesVBox.getChildren().add(pageLabels.getLabel(index));

        UIComponents.animate(pageLabels.getLabel(index), index, 0.15, 0.2, -10,0,0, 0);
    }


    /**
     * Updates the position of the side marker that indicates which page the user is currently on
     * @param index the page index, starting at 0
     */
    public void update(int index) {
        if (index == currentPageIndex) {
            return; //in case user clicked on current page
        }
        LessonPage.currentPage = index;
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

    private void getAndSetLabelText(int index) {
        String title = pages.get(index).getTitle();
        if (title != null) {
            if (title.length() > 23) {
                title = title.substring(0, 24);
            }
            pageLabels.getLabel(index).setText(title);
        } else {
            pageLabels.getLabel(index).setText("Page " + (index + 1));
        }
    }

}
