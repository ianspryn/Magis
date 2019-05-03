package com.magis.app.UI;

import com.magis.app.Main;
import com.magis.app.home.StatsPage;
import com.magis.app.models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class IntelligentTutor {

    private static StudentModel.Student student;
    private static LessonModel.ChapterModel chapter;
    private static QuizzesModel quizzesModel;
    private static TestsModel testsModel;
    private static int progress;
    private static ArrayList<Integer> visitedPages;

    private static ArrayList<Text> texts;
    private static Label recentActivityTitle;
    private static Label chapterTitleText;
    private static Text text;
    private static boolean hasTakenQuiz;
    private static boolean hasTakenTest;

    private static int newChapter;
    private static int newPage;

    /**
     * Generate a box that will dynamically suggest to the student where to pick up
     * NOTE: This method must only be called after the VBox exists on the home page and that recentPage and recentChapter != -1
     * @return a VBox filled with dynamically generated text
     */
    public static VBox generateRecentActivity() {
        student = Main.studentModel.getStudent();
        chapter = Main.lessonModel.getChapter(student.getRecentChapter());
        quizzesModel = Main.quizzesModel;
        testsModel = Main.testsModel;

        newPage = student.getRecentPage();
        newChapter = student.getRecentChapter();

        /*
        determine if the recent page was a lesson page (not a "begin quiz/test" page)
         */
        boolean onLessonPage = student.getRecentPage() < chapter.getNumPages();
        /*
        Check of the page has a title
        If so, we might use it later
         */
        boolean hasPageTitle = onLessonPage && chapter.getPage(student.getRecentPage()).getTitle() != null;
        /*
        Determine if we are on a "begin quiz" page
        First check if the chapter has a quiz
        Then check of the page index matches the index of the quiz page for that lesson
         */
        boolean onQuizPage = quizzesModel.hasQuiz(chapter.getTitle()) && student.getRecentPage() == chapter.getNumPages();
        /*
        Determine if we are on a "begin test" page
        First check if the chapter has a test
        Then check of the page index matches the index of the test page for that lesson
        Note: We add 1 if we have a quiz page, because that pushes the index of the test page up one more
         */
        int testPageIndex = ((quizzesModel.hasQuiz(chapter.getTitle()) ? 1 : 0) + chapter.getNumPages());
        boolean onTestPage = testsModel.hasTest(chapter.getTitle()) && student.getRecentPage() == testPageIndex;

        //if there exists a quiz
        if (quizzesModel.hasQuiz(chapter.getTitle())) {
            hasTakenQuiz = student.hasTakenQuiz(student.getRecentChapter());
        } else {
            hasTakenQuiz = true; //default to true
        }
        //if there exists a test
        if (testsModel.hasTest(chapter.getTitle())) {
            hasTakenTest = student.hasTakenTest(student.getRecentChapter());
        } else {
            hasTakenTest = true; //default to true
        }

        progress = student.getChapter(student.getRecentChapter()).getProgress();
        visitedPages = student.getChapter(student.getRecentChapter()).getPageVisited();

        /*
        Build the box
         */
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        //Title
        recentActivityTitle = new Label();
        recentActivityTitle.getStyleClass().add("box-title");
        recentActivityTitle.setTextAlignment(TextAlignment.LEFT);
        recentActivityTitle.setWrapText(true);

        //Chapter Title
        chapterTitleText = new Label("Chapter " + (student.getRecentChapter() + 1) + " - " + chapter.getTitle());
        chapterTitleText.setPadding(new Insets(25,0,0,0));
        chapterTitleText.getStyleClass().add("box-description");
        chapterTitleText.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

        //Text description
        TextFlow recentActivitySubText = new TextFlow();
        recentActivitySubText.setPadding(new Insets(25,0,0,0));
        recentActivitySubText.setTextAlignment(TextAlignment.LEFT);

        texts = new ArrayList<>();

        /*
        Populate the box
         */
        //if they are completely done with this chapter
        if (progress == 100 && hasTakenQuiz && hasTakenTest) {
            recentActivityTitle.setText("Onward!");
            text = new Text();
            texts.add(text);
            text.setText("You've completely finished this chapter! ");
            text.getStyleClass().add("box-description");

            //only suggest the user to continue learning if we're not 100% done with everything
            if (StatsPage.calculateOverallProgress() < 100) {
                //Find the next chapter to suggest the user to pick up with
                ArrayList<StudentModel.Student.ChapterModel> chapters = student.getChapters();
                for (int chapter = 0; chapter < chapters.size(); chapter++) {
                    StudentModel.Student.ChapterModel chapterModel = chapters.get(chapter);
                    if (chapterModel.getProgress() < 100) {
                        ArrayList<Integer> pagesVisited = chapterModel.getPageVisited();
                        for (int page = 0; page < pagesVisited.size(); page++) {
                            if (pagesVisited.get(page) == 0) {
                                newPage = page;
                                newChapter = chapter;
                                break;
                            }
                        }
                        //if there's an unfinished chapter that is earlier than the current chapter
                        if (newChapter < student.getRecentChapter()) {
                            text = new Text();
                            texts.add(text);
                            if (student.getChapter(newChapter).getProgress() == 0) {
                                text.setText("You haven't done ");
                            } else {
                                text.setText("You haven't finished ");
                            }
                            text.getStyleClass().add("box-description");

                            text = new Text();
                            texts.add(text);
                            text.setText("Chapter " + (newChapter + 1) + ": " + Main.lessonModel.getChapter(newChapter).getTitle());
                            text.getStyleClass().add("box-description");
                            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

                            text = new Text();
                            texts.add(text);
                            text.setText(" yet. Want to do it now?");
                            text.getStyleClass().add("box-description");
                        } else { //it's an unfinished chapter after their current chapter
                            text = new Text();
                            texts.add(text);
                            text.setText("Ready for the next chapter? If so, click to go to ");
                            text.getStyleClass().add("box-description");

                            text = new Text();
                            texts.add(text);
                            text.setText("Chapter " + (newChapter + 1) + ": " + Main.lessonModel.getChapter(newChapter).getTitle());
                            text.getStyleClass().add("box-description");
                            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

                            //if the student already made progress in the next chapter, then indicate which page they should jump to
                            if (student.getChapter(newChapter).getProgress() > 0) {
                                ArrayList<Integer> pagesVisited2 = student.getChapter(newChapter).getPageVisited();
                                for (int page = 0; page < pagesVisited2.size(); page++) {
                                    if (pagesVisited2.get(page) == 0) {
                                        text = new Text();
                                        texts.add(text);
                                        text.setText(" on ");
                                        text.getStyleClass().add("box-description");

                                        text = new Text();
                                        texts.add(text);
                                        if (Main.lessonModel.getChapter(newChapter).getPage(newPage).getTitle() != null) {
                                            text.setText("page " + (page + 1) + ": " + Main.lessonModel.getChapter(newChapter).getPage(newPage).getTitle());
                                        } else {
                                            text.setText("page " + (page + 1));
                                        }
                                        text.getStyleClass().add("box-description");
                                        text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    } else if (Main.quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapter).getTitle()) && !student.hasTakenQuiz(chapter)) {
                        newPage = Main.lessonModel.getChapter(chapter).getNumPages();
                        newChapter = chapter;

                        //if there's an unfinished chapter that is earlier than the current chapter
                        text = new Text();
                        texts.add(text);
                        text.setText("You haven't taken the quiz for ");
                        text.getStyleClass().add("box-description");

                        text = new Text();
                        texts.add(text);
                        text.setText("Chapter " + (newChapter + 1) + ": " + Main.lessonModel.getChapter(newChapter).getTitle());
                        text.getStyleClass().add("box-description");
                        text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

                        text = new Text();
                        texts.add(text);
                        text.setText(" yet. Want to do it now?");
                        text.getStyleClass().add("box-description");
                        break;
                    } else if (Main.testsModel.hasTest(Main.lessonModel.getChapter(chapter).getTitle()) && !student.hasTakenTest(chapter)) {
                        newPage = Main.lessonModel.getChapter(chapter).getNumPages() + (quizzesModel.hasQuiz(Main.lessonModel.getChapter(chapter).getTitle()) ? 1 : 0);
                        newChapter = chapter;

                        //if there's an unfinished chapter that is earlier than the current chapter
                        text = new Text();
                        texts.add(text);
                        text.setText("You haven't taken the test for ");
                        text.getStyleClass().add("box-description");

                        text = new Text();
                        texts.add(text);
                        text.setText("Chapter " + (newChapter + 1) + ": " + Main.lessonModel.getChapter(newChapter).getTitle());
                        text.getStyleClass().add("box-description");
                        text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

                        text = new Text();
                        texts.add(text);
                        text.setText(" yet. Want to do it now?");
                        text.getStyleClass().add("box-description");
                        break;
                    }
                }
            }
            chapterTitleText.setText("Chapter " + (student.getRecentChapter() + 1) + " - " + chapter.getTitle());
        } else if (onQuizPage || (progress == 100 && !hasTakenQuiz)) {
            if (!checkForIncompleteProgress("quiz")) {
                newPage = chapter.getNumPages();
                recentActivityTitle.setText("Ready to take your quiz?");
                text = new Text();
                texts.add(text);
                text.setText("You finished reading ");
                text.getStyleClass().add("box-description");

                text = new Text();
                texts.add(text);
                text.setText(chapter.getTitle());
                text.getStyleClass().add("box-description");
                text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

                text = new Text();
                texts.add(text);
                text.setText(" chapter! If you're ready to take your quiz, click here.");
                text.getStyleClass().add("box-description");
            }
        } else if (onTestPage || (progress == 100 || !hasTakenTest)) {
            //make sure the student has read everything first
            if (!checkForIncompleteProgress("test")) {
                //make sure the student has taken the quiz (if there is one)
                if (!checkForIncompleteQuiz()) {
                    newPage = chapter.getNumPages() + (quizzesModel.hasQuiz(chapter.getTitle()) ? 1 : 0);
                    recentActivityTitle.setText("Ready to take your test?");
                    text = new Text();
                    texts.add(text);
                    text.setText("You finished reading ");
                    text.getStyleClass().add("box-description");

                    text = new Text();
                    texts.add(text);
                    text.setText(chapter.getTitle());
                    text.getStyleClass().add("box-description");
                    text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

                    text = new Text();
                    texts.add(text);
                    text.setText(" chapter! If you're ready to take your test, click here.");
                    text.getStyleClass().add("box-description");
                }
            }
        } else if (onLessonPage) {
            recentActivityTitle.setText("Pick up where you left off?");
            if (progress > 80) {
                text = new Text();
                texts.add(text);
                text.setText("You're almost done with this chapter! ");
                text.getStyleClass().add("box-description");
                text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
            }

            text = new Text();
            texts.add(text);
            text.setText("Click here to return to your next page on ");
            text.getStyleClass().add("box-description");

            ArrayList<Integer> pagesVisited = student.getChapter(student.getRecentChapter()).getPageVisited();
            for (int page = 0; page < pagesVisited.size(); page++) {
                if (pagesVisited.get(page) == 0) {
                    newPage = page;
                    break;
                }
            }

            text = new Text();
            texts.add(text);
            text.getStyleClass().add("box-description");
            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
            if (hasPageTitle) {
                text.setText("Page " + (newPage + 1) + ": " + chapter.getPage(newPage).getTitle());
            } else {
                text.setText("Page " + (student.getRecentPage() + 1));
            }
        }

        for (Text text : texts) {
            recentActivitySubText.getChildren().add(text);
        }
        box.getChildren().addAll(recentActivityTitle, chapterTitleText, recentActivitySubText);
        return box;
    }

    private static boolean checkForIncompleteQuiz() {
        //if the student hasn't taken a quiz
        if (hasTakenQuiz) {
            text = new Text();
            texts.add(text);
            text.setText("\nYou also haven't taken your quiz yet.");
            text.getStyleClass().add("box-description");
            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

            text = new Text();
            texts.add(text);
            text.setText(" Be sure to take it so you are fully prepared for the test!");
            text.getStyleClass().add("box-description");
            return true;
        }
        return false;
    }

    /**
     * Checks for incomplete progress before taking an exam, and if there is incomplete progress, add text to encourage student to go back and read
     * @return true if there are pages that were not read, and false otherwise
     */
    private static boolean checkForIncompleteProgress(String examType) {
        if (progress < 60) {
            recentActivityTitle.setText("Before you take your " + examType + "...");
            text = new Text();
            texts.add(text);
            if (progress < 40) {
                text.setText("You haven't read much very material from this chapter. Unless you were already familiar with this material beforehand, it's best that you to continue reading. Pick up at ");
            } else { //then we're between 40% and 60% progress
                text.setText("You haven't read a considerable amount from this chapter. Unless you were already familiar with this material beforehand, it's best that you to continue reading. Pick up at ");
            }
            text.getStyleClass().add("box-description");

            //find the earliest page that the student did NOT visit and set newPage to that
            for (int i = 0; i < visitedPages.size(); i++) {
                if (visitedPages.get(i) == 0) {
                    newPage = i;
                    break;
                }
            }

            text = new Text();
            texts.add(text);
            text.getStyleClass().add("box-description");
            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
            if (chapter.getPage(newPage) != null && chapter.getPage(newPage).getTitle() != null) {
                text.setText("page " + (newPage + 1) + ": " + chapter.getPage(newPage).getTitle());
            } else {
                text.setText("page " + (newPage + 1));
            }
        } else if (progress < 100) {
            recentActivityTitle.setText("Before you take your " + examType + "...");
            text = new Text();
            texts.add(text);
            text.getStyleClass().add("box-description");

            if (progress < 80) {
                text.setText("You haven't quite finished reading the material for this chapter. Unless you were already familiar with this material beforehand, it's best that you to continue reading. You skipped ");
            } else {
                text.setText("You're so close to finishing reading! Unless you were already familiar with this material beforehand, why not finish up ");
            }


            ArrayList<String> pageTitles = new ArrayList<>();
            for (int i = 0; i < visitedPages.size(); i++) {
                LessonModel.ChapterModel.PageModel page = chapter.getPage(i);
                boolean hasPageTitle2 = page.getTitle() != null;

                String pageTitle = "page " + (i + 1);

                //if page not visited
                if (visitedPages.get(i) == 0) {
                    if (hasPageTitle2) {
                        pageTitle += ": " + page.getTitle() + ", ";
                    }
                }

                pageTitles.add(pageTitle);
            }

            //the last one is a special case, because we insert a non-bold word "and"
            StringBuilder titles = new StringBuilder();
            for (int i = 0; i < pageTitles.size() - 1; i++) {
                titles.append(pageTitles.get(i));
            }

            text = new Text();
            texts.add(text);
            text.setText(titles.toString());
            text.getStyleClass().add("box-description");
            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

            //make sure we don't stick an "and" in if there's only one page! If that could ever happen...
            if (pageTitles.size() > 1) {
                text = new Text();
                texts.add(text);
                text.setText(" and ");
                text.getStyleClass().add("box-description");
            }

            //add the last page, now that we've stuck "and" between the list of pages
            text = new Text();
            texts.add(text);
            text.setText(pageTitles.get(pageTitles.size() - 1));
            text.getStyleClass().add("box-description");
            text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");

            //a phrase to go with the beginning phrase that is more encouraging and light if the student is close to being done
            if (progress >= 80) {
                text = new Text();
                texts.add(text);
                text.setText("real quick?");
                text.getStyleClass().add("box-description");
                text.setStyle("-fx-font-family: \"Roboto Mono Bold\"; -fx-font-size: 11px");
            }

            //find the earliest page that the student did NOT visit and set newPage to that
            for (int i = 0; i < visitedPages.size(); i++) {
                if (visitedPages.get(i) == 0) {
                    newPage = i;
                    break;
                }
            }
        }
        return progress != 100;
    }

    public static VBox generateInsights() {
        VBox insights = new VBox();

        return insights;
    }

    public static int getNewPage() {
        return newPage;
    }

    public static int getNewChapter() {
        return newChapter;
    }
}
