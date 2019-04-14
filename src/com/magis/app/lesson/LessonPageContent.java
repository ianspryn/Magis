package com.magis.app.lesson;

import com.magis.app.Main;
import com.magis.app.UI.PageContent;
import com.magis.app.models.LessonModel;
import com.magis.app.test.quiz.QuizPage;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.stream.IntStream;


public class LessonPageContent {

    private ScrollPane parentScrollPane;
    private ArrayList<PageContent> pageContents;
    private VBox masterContent;
    private int chapterIndex;
    private int hasQuiz;
    private int hasTest;

    public LessonPageContent(ScrollPane scrollPane, int chapterIndex, int hasQuiz, int hasTest) {
        parentScrollPane = scrollPane;
        pageContents = new ArrayList<>();
        this.chapterIndex = chapterIndex;
        this.hasQuiz = hasQuiz;
        this.hasTest = hasTest;
    }

    public VBox getPageContent() {
        return masterContent;
    }

    public void initialize() {
        LessonModel.ChapterModel chapter = Main.lessonModel.getChapter(chapterIndex);
        int numPages = chapter.getNumPages();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numPages; i++) {
            int pageIndex = i;
            pageContents.add(new PageContent(chapterIndex));
            Thread thread = new Thread(() -> buildPage(pageIndex));
            thread.start();
            threads.add(thread);
        }

        //wait for the threads to finish, else we *sometimes* get a "Not on FX application thread" error. Only sometimes. And only on one page. Very weird bug.
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        if (hasQuiz > 0) {
            PageContent pageContent = new PageContent(chapterIndex);
            pageContent.buildAsTestIntroPage("quiz");
            pageContents.add(pageContent);
        }

        if (hasTest > 0) {
            PageContent pageContent = new PageContent(chapterIndex);
            pageContent.buildAsTestIntroPage("test");
            pageContents.add(pageContent);
        }
        update(0);
    }

    /**
     * Update the page content of the lesson page
     * @param pageIndex the page to load.
     */
    public void update(int pageIndex) {
        //Make sure we don't try to mark the quiz/test intro page as a lesson page (we'll get an out of bounds if we do)
        if (pageIndex < Main.lessonModel.getChapter(chapterIndex).getNumPages()) {
            //Mark the page as visited
            Main.studentModel.getStudent(Main.username).getChapter(chapterIndex).visitPage(pageIndex);
        }
        //Last page visited
        Main.studentModel.getStudent(Main.username).setRecentPlace(chapterIndex, pageIndex);

        //set the page content
        masterContent = pageContents.get(pageIndex).getMasterContent();
        parentScrollPane.setContent(masterContent);
    }

    /**
     * @param pageIndex the index of the lesson page for the current chapter
     */
    private void buildPage(int pageIndex) {
        ArrayList<LessonModel.ChapterModel.PageModel.LessonContent> lessonContents = Main.lessonModel.getChapter(chapterIndex).getPage(pageIndex).getLessonContent();
        PageContent pageContent = pageContents.get(pageIndex);
        for (LessonModel.ChapterModel.PageModel.LessonContent lessonPageContent : lessonContents) {
            String type = lessonPageContent.getType();
            switch (type) {
                case "image":
                    ImageView image = new ImageView(lessonPageContent.getContent());
                    image.setPreserveRatio(true);
                    pageContent.add(image);
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
                    pageContent.add(textDefault);
                    break;
            }
        }
        pageContent.buildAsLessonPage(pageIndex);
    }
}
