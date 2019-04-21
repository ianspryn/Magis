package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.UI.PageContentContainer;
import com.magis.app.models.LessonModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class LessonPageContent extends PageContent {

    public ArrayList<PageContentContainer> pageContentContainers;
    private int chapterIndex;
    private int numPages;
    private int hasQuiz;
    private int hasTest;

    public LessonPageContent(int chapterIndex) {
        pageContentContainers = new ArrayList<>();
        this.chapterIndex = chapterIndex;
        LessonModel.ChapterModel chapterModel = Main.lessonModel.getChapter(chapterIndex);
        String chapterTitle = chapterModel.getTitle();
        this.hasQuiz = Main.quizzesModel.hasQuiz(chapterTitle) ? 1 : 0;
        this.hasTest = Main.testsModel.hasTest(chapterTitle) ? 1 : 0;
        this.numPages = chapterModel.getNumPages();

        initialize();
    }

    private void initialize() {
        for (int i = 0; i < numPages; i++) {
            int pageIndex = i;
            pageContentContainers.add(new PageContentContainer(chapterIndex));
            new Thread(() -> buildPage(pageIndex)).start();
        }

        if (hasQuiz > 0) {
            PageContentContainer pageContent = new PageContentContainer(chapterIndex);
            pageContent.buildAsQuizIntroPage();
            pageContentContainers.add(pageContent);
        }

        if (hasTest > 0) {
            PageContentContainer pageContent = new PageContentContainer(chapterIndex);
            pageContent.buildAsExamIntroPage();
            pageContentContainers.add(pageContent);
        }
    }

    @Override
    void update(int pageIndex) {
        //Make sure we don't try to mark the quiz/test intro page as a lesson page (we'll get an out of bounds if we do)
        if (pageIndex < Main.lessonModel.getChapter(chapterIndex).getNumPages()) {
            //Mark the page as visited
            Main.studentModel.getStudent().getChapter(chapterIndex).visitPage(pageIndex);
        }
        //Last page visited
        Main.studentModel.getStudent().setRecentPlace(chapterIndex, pageIndex);
        setScrollPaneContent(pageContentContainers.get(pageIndex).getMasterContent());
    }

    @Override
    void buildPage(int pageIndex) {
        ArrayList<LessonModel.ChapterModel.PageModel.LessonContent> lessonContents = Main.lessonModel.getChapter(chapterIndex).getPage(pageIndex).getLessonContent();
        PageContentContainer pageContentContainer = pageContentContainers.get(pageIndex);
        for (LessonModel.ChapterModel.PageModel.LessonContent lessonPageContent : lessonContents) {
            String type = lessonPageContent.getType();
            switch (type) {
                case "image":
                    ImageView image = new ImageView();
                    Thread thread = new Thread(() -> image.setImage(new Image(lessonPageContent.getContent()))); //load images in the background
                    thread.setDaemon(true);
                    thread.start();
                    image.setPreserveRatio(true);
                    pageContentContainer.add(image);
                    break;
                default:
                    System.err.println("Unrecognized XML tag <" + type + ">. Defaulting to text field.");
                case "text":
                    Label textDefault = new Label();
                    textDefault.setText(lessonPageContent.getContent());
                    textDefault.setWrapText(true);
                    textDefault.setPrefWidth(700);
                    textDefault.getStyleClass().add("lesson-text");
                    textDefault.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT);
                    textDefault.setPadding(new Insets(20, 20, 20, 20));
                    pageContentContainer.add(textDefault);
                    break;
            }
        }
        pageContentContainer.buildAsLessonPage(pageIndex);
    }


}
