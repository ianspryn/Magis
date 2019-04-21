package com.magis.app.page;

import com.magis.app.Main;
import javafx.scene.control.Label;

public class QuizSidePanel extends PageSidePanel {



    public QuizSidePanel(int chapterIndex) {
        super(chapterIndex);
        /*
        Set the text and clicking action of each page label
         */
        String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
        int numPages = Main.quizzesModel.getChapter(chapterTitle).getNumAvailableQuestions();
        for (int i = 0; i < numPages; i++) {
            Label label = getPageLabel(i);
            label.setText("Page " + i + 1);
            int index = i;
            label.setOnMouseClicked(e -> page.updatePage(index));
        }
    }
}
