package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.models.LessonModel;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class LessonSidePanel {

    private int chapterIndex;
    private LessonPageContent lessonPageContent;
    private ArrayList<LessonModel.ChapterModel.PageModel> pages;
    private VBox vBox;
    private HBox currentPage;
    private PageLabels pageLabels;
    private ImageView line;

    private int currentPageIndex;


    public LessonSidePanel(int chapterIndex, LessonPageContent lessonPageContent, ArrayList<LessonModel.ChapterModel.PageModel> pages) {
        this.chapterIndex = chapterIndex;
        this.lessonPageContent = lessonPageContent;
        this.pages = pages;
        this.vBox = new VBox();
        this.currentPage = new HBox();
        if (Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle())) {
            this.pageLabels = new PageLabels(pages.size() + 1);
        } else {
            this.pageLabels = new PageLabels(pages.size());
        }
        line = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/pink400.png");
        currentPageIndex = 0;
    }

    public VBox getvBox() {
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
            lessonPageContent.update(0);
            update(0);
        });
        pageLabels.getLabel(0).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        pageLabels.getLabel(0).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //add line and first page text to hbox
        currentPage.getChildren().addAll(line, pageLabels.getLabel(0));

        //add first page to the list of pages
        vBox.getChildren().add(currentPage);

        for (int i = 1; i < pages.size(); i++) {
            int index = i;
            pageLabels.getLabel(i).setPadding(new Insets(0, 0, 0, 15));
            pageLabels.getLabel(i).getStyleClass().add("lesson-side-panel-text");
            setLabelText(i);

            //listeners
            pageLabels.getLabel(i).setOnMouseClicked(e -> {
                lessonPageContent.update(index);
                update(index);
            });
            pageLabels.getLabel(i).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            pageLabels.getLabel(i).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            //add page to the list of pages
            vBox.getChildren().add(pageLabels.getLabel(i));
        }
        currentPageIndex = 0;

        //if there exists a quiz for this chapter
        if (Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapterIndex).getTitle())) {
            //then add it to the side panel
            pageLabels.getLabel(pages.size()).setPadding(new Insets(0, 0, 0, 15));
            pageLabels.getLabel(pages.size()).getStyleClass().add("lesson-side-panel-text");
            pageLabels.getLabel(pages.size()).setText("Quiz");



//            Label quizLabel = new Label();
//            quizLabel.setPadding(new Insets(0, 0, 0, 15));
//            quizLabel.getStyleClass().add("lesson-side-panel-text");
//            quizLabel.setText("Quiz");

            //listeners
            pageLabels.getLabel(pages.size()).setOnMouseClicked(e -> {
                lessonPageContent.update(-1);
                update(pages.size());
            });
            pageLabels.getLabel(pages.size()).setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            pageLabels.getLabel(pages.size()).setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            vBox.getChildren().addAll(pageLabels.getLabel(pages.size()));
        }
    }

    /**
     * Updates the position of the side marker that indicates which page the user is currently on
     * @param index the page index, starting at 0
     */
    public void update(int index) {
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
        String title = pages.get(index).getTitle();
        if (title != null) {
            pageLabels.getLabel(index).setText(title);
        } else {
            pageLabels.getLabel(index).setText("Page " + (index + 1));
        }
    }

}
