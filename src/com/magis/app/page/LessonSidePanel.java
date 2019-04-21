package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.lesson.LessonPage;
import com.magis.app.models.LessonModel;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class LessonSidePanel extends PageSidePanel {

    private ArrayList<LessonModel.ChapterModel.PageModel> pages;

    public LessonSidePanel(int chapterIndex) {
        super(chapterIndex);
        pages = Main.lessonModel.getChapter(chapterIndex).getPages();

        /*
        Set the number of pages
         */
        String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
        int hasQuiz = Main.quizzesModel.hasQuiz(chapterTitle) ? 1 : 0;
        int hasTest = Main.testsModel.hasTest(chapterTitle) ? 1 : 0;
        int numLessonPages = Main.lessonModel.getChapter(chapterIndex).getNumPages();
        setNumPages(Main.lessonModel.getChapter(chapterIndex).getNumPages() + hasQuiz + hasTest);

        /*
        Generate the side panel
         */
        initialize();

        /*
        Populate the side panel
        Set the text and clicking action of each page label
         */
        for (int i = 0; i < numLessonPages; i++) {
            Label sidePanelLabel = getPageLabel(i);
            getAndSetLabelText(i);
            int index = i;
            sidePanelLabel.setOnMouseClicked(e -> page.updatePage(index));
        }

        if (hasQuiz > 0) {
            Label quizLabel = getPageLabel(numLessonPages);
            quizLabel.setText("Quiz");
            quizLabel.setOnMouseClicked(e -> page.updatePage(numLessonPages));
        }

        if (hasTest > 0) {
            int index = numLessonPages + hasQuiz;
            Label testLabel = getPageLabel(index);
            testLabel.setText("Test");
            testLabel.setOnMouseClicked(e -> page.updatePage(index));
        }
    }
    private void getAndSetLabelText(int index) {
        String title = pages.get(index).getTitle();
        if (title != null) {
            if (title.length() > 23) {
                title = title.substring(0, 24);
            }
            getPageLabel(index).setText(title);
        } else {
            getPageLabel(index).setText("Page " + (index + 1));
        }
    }
}
