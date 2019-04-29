package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.UI.PageContentContainer;
import com.magis.app.models.LessonModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

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
            pageContent.buildAsTestIntroPage();
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
                    image.setPreserveRatio(true);
                    //scale the image to a max of either 800px or whatever thw width of the scroll pane is (compensating for the scrollbar's thickness itself)
                    image.setFitWidth(Math.min(800, getScrollPane().widthProperty().doubleValue() - 50));
                    getScrollPane().widthProperty().addListener((observable, oldVal, newVal) -> {
                        image.setFitWidth(Math.min(800, newVal.doubleValue() - 50));
                    });
                    //load images in the background
                    Thread thread = new Thread(() -> image.setImage(new Image(lessonPageContent.getContent())));
                    thread.setDaemon(true);
                    thread.start();
                    pageContentContainer.add(image);
                    break;
                default:
                    System.err.println("Unrecognized XML tag <" + type + ">. Defaulting to text field.");
                case "text":
                    formatText(lessonPageContent.getContent(), pageContentContainer, chapterIndex, pageIndex);
                    break;
            }
        }
        pageContentContainer.buildAsLessonPage(pageIndex);
    }

    private void formatText(String content, PageContentContainer pageContentContainer, int chapterIndex, int pageIndex) {

        //Visualisation at: https://regex101.com/r/K5260V/2
        String delimiter = "(?<=```)|(?=```)|(?<=###)|(?=###)|(?<=\\[H[123]])|(?=\\[H[123]])";
        String[] splitStrings = content.split(delimiter);

        Stack<String> stack = new Stack<>();
        for (String subString : splitStrings) {
            Label label = new Label();
            label.setWrapText(true);
            label.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it contains
//            label.setMaxWidth(800);
            pageContentContainer.add(label);
            switch (subString) {
                case "```": //beginning of code segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("```")) stack.pop();
                    else System.err.println("Non-balanced text formatting of type [```] for the chapter \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" on page " + (pageIndex + 1));
                    break;
                case "###": //beginning of code output segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("###")) stack.pop();
                    else System.err.println("Non-balanced text formatting of type [###] for the chapter \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" on page " + (pageIndex + 1));
                    break;
                case "[H1]": //beginning of header 1 segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("[H1]")) stack.pop();
                    else System.err.println("Non-balanced text formatting of type [H1] for the chapter \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" on page " + (pageIndex + 1));
                    break;
                case "[H2]": //beginning of header 2 segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("[H2]")) stack.pop();
                    else System.err.println("Non-balanced text formatting of type [H2] for the chapter \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" on page " + (pageIndex + 1));
                    break;
                case "[H3]": //beginning of header 3 segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("[H3]")) stack.pop();
                    else System.err.println("Non-balanced text formatting of type [H3] for the chapter \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" on page " + (pageIndex + 1));
                    break;
                default: //text only
                    String formatType = stack.isEmpty() ? "" : stack.peek();
                    if (subString.charAt(0) == '\n') subString = subString.substring(1); //get rid of the first new line if it exists because of the way we split the strings
                    label.setText(subString);
                    switch (formatType) {
                        case "```": //we're in code segment
                            label.getStyleClass().add("code-text");
                            break;
                        case "###": //we're in a code output segment
                            label.getStyleClass().addAll("code-output-text", "drop-shadow");
                            break;
                        case "[H1]": //we're in a header 1 segment
                            label.getStyleClass().addAll("lesson-header-one-text", "lesson-text-color");
                            break;
                        case "[H2]": //we're in a header 2 segment
                            label.getStyleClass().addAll("lesson-header-two-text", "lesson-text-color");
                            break;
                        case "[H3]": //we're in a header 3 segment
                            label.getStyleClass().addAll("lesson-header-three-text", "lesson-text-color");
                            break;
                        default: //we are not in any kind of formatting segment
                            label.getStyleClass().addAll("lesson-text", "lesson-text-color");
                    }
            }
        }



    }


}
