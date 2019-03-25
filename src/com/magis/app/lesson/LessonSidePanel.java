package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.models.LessonModel;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class LessonSidePanel {

    private int chapterIndex;
    private LessonPageContent lessonPageContent;
    private ArrayList<LessonModel.ChapterModel.PageModel> pages;
    private ScrollPane scrollPane;
    private VBox masterVBox;
    private VBox contentPagesVBox;
    private HBox currentPage;
    private PageLabels pageLabels;
    private ImageView verticalLine;
    private ImageView horizontalLine;

    private int currentPageIndex;


    public LessonSidePanel(int chapterIndex, LessonPageContent lessonPageContent, ArrayList<LessonModel.ChapterModel.PageModel> pages) {
        this.chapterIndex = chapterIndex;
        this.lessonPageContent = lessonPageContent;
        this.pages = pages;
        this.scrollPane = new ScrollPane();
        this.masterVBox = new VBox();
        this.contentPagesVBox = new VBox();
        this.currentPage = new HBox();
        if (Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle())) {
            this.pageLabels = new PageLabels(pages.size() + 1);
        } else {
            this.pageLabels = new PageLabels(pages.size());
        }
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

        masterVBox.getChildren().addAll(chapterTitle, horizontalLine);

        masterVBox.setPadding(new Insets(50,0,0,15));
        masterVBox.setSpacing(10);
        contentPagesVBox.setSpacing(10);

        verticalLine.setPreserveRatio(false);
        verticalLine.setFitHeight(25);
        verticalLine.setFitWidth(5);



        pageLabels.getLabel(0).setPadding(new Insets( 0, 0, 0, 10));
        pageLabels.getLabel(0).getStyleClass().add("studentlesson-side-panel-text");
        setLabelText(0);

        //listeners
        pageLabels.getLabel(0).setOnMouseClicked(e -> {
            lessonPageContent.update(0);
            update(0);
        });
        pageLabels.getLabel(0).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(0).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add verticalLine and first page text to hbox
        currentPage.getChildren().addAll(verticalLine, pageLabels.getLabel(0));

        //add first page to the list of pages
        contentPagesVBox.getChildren().add(currentPage);

        for (int i = 1; i < pages.size(); i++) {
            int index = i;
            pageLabels.getLabel(i).setPadding(new Insets(0, 0, 0, 15));
            pageLabels.getLabel(i).getStyleClass().add("sistudentlesson-side-panel-text");
            setLabelText(i);

            //listeners
            pageLabels.getLabel(i).setOnMouseClicked(e -> {
                lessonPageContent.update(index);
                update(index);
            });
            pageLabels.getLabel(i).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            pageLabels.getLabel(i).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            //add page to the list of pages
            contentPagesVBox.getChildren().add(pageLabels.getLabel(i));
        }
        masterVBox.getChildren().add(contentPagesVBox);
        currentPageIndex = 0;

        //if there exists a test for this chapter
        if (Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle())) {
            //then add it to the side lessonSidePanel
            pageLabels.getLabel(pages.size()).setPadding(new Insets(0, 0, 0, 15));
            pageLabels.getLabel(pages.size()).getStyleClass().add("lesson-side-panel-test-text");
            pageLabels.getLabel(pages.size()).setText("Quiz");

            //listeners
            pageLabels.getLabel(pages.size()).setOnMouseClicked(e -> {
                lessonPageContent.update(-1);
                update(pages.size());
            });
            pageLabels.getLabel(pages.size()).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            pageLabels.getLabel(pages.size()).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            contentPagesVBox.getChildren().addAll(pageLabels.getLabel(pages.size()));
        }

        scrollPane.setContent(masterVBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("sidebar-scrollpane");
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

    private void setLabelText(int index) {
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
