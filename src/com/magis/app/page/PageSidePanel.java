package com.magis.app.page;

import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.models.StudentModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class PageSidePanel {
    protected Page page;
    protected ScrollPane scrollPane;
    protected VBox masterVBox;
    protected VBox contentPagesVBox;
    protected HBox currentPage;
    protected Rectangle verticalLine;
    protected Rectangle horizontalLine;
    protected HBox verticalLineContainer;
    protected StudentModel.Student student;
    protected PageLabels pageLabels;
    protected Label title;

    protected int chapterIndex;
    protected int currentPageIndex;


    public PageSidePanel(int chapterIndex) {
        scrollPane = new ScrollPane();
        masterVBox = new VBox();
        contentPagesVBox = new VBox();
        currentPage = new HBox();
        verticalLine = new Rectangle();
        horizontalLine = new Rectangle();
        verticalLineContainer = new HBox();
        student = Main.studentModel.getStudent();
        title = new Label();
        this.chapterIndex = chapterIndex;
        currentPageIndex = 0;
    }

    public void initialize() {
        scrollPane.getStyleClass().add("side-panel-scroll-bar");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //master VBox
        masterVBox.setPadding(new Insets(50,0,0,15));
        masterVBox.setSpacing(10);

        //chapter title
        title.setText(Main.lessonModel.getChapter(chapterIndex).getTitle());
        title.getStyleClass().add("side-panel-title");
        title.setMaxWidth(300);
        title.setWrapText(true);
        UIComponents.fadeOnAndTranslate(title, 0.15, 0.2,-10,0,0,0);

        //divider
        horizontalLine.setHeight(2);
        horizontalLine.setWidth(250);
        horizontalLine.getStyleClass().add("rectangle-color");
        UIComponents.fadeOnAndTranslate(verticalLineContainer, 0.15, 0.2,-10,0,0,0);

        //current page indicator
        verticalLine.setHeight(25);
        verticalLine.setWidth(5);
        verticalLine.getStyleClass().add("rectangle-color");
        verticalLineContainer.getChildren().add(verticalLine);
        verticalLineContainer.setPadding(new Insets(0,0,0,-5));
        currentPage.getChildren().addAll(verticalLineContainer, pageLabels.getLabel(0));
        UIComponents.fadeOnAndTranslate(currentPage,0.3,0.2,-10,0,0,0);

        //pages
        contentPagesVBox.setSpacing(10);

        //add the current page label and the other pages to the contentPages VBox
        contentPagesVBox.getChildren().add(currentPage);
        for (int i = 1; i < pageLabels.getNumLabels(); i++) {
            contentPagesVBox.getChildren().add(pageLabels.getLabel(i));
            UIComponents.fadeOnAndTranslate(pageLabels.getLabel(i), i, 0.3, 0.2, -10,0,0, 0);
        }

        //add everything to the master VBox
        masterVBox.getChildren().addAll(title, horizontalLine, contentPagesVBox);
        scrollPane.setContent(masterVBox);
        JFXScrollPane.smoothScrolling(scrollPane);
    }

    public void update(int index) {
        if (index == currentPageIndex) return;

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

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    protected Label getPageLabel(int index) {
        return pageLabels.getLabel(index);
    }

    public void setPageClass(Page page) {
        this.page = page;
    }
}
