package com.magis.app.page;

import com.magis.app.Main;
import javafx.scene.control.Label;

public class ExamSidePanel extends PageSidePanel {
    public ExamSidePanel(int chapterIndex, int numQuestions) {
        super(chapterIndex);
        /*
        Initialize pageLabels
         */
        int numPages = (int) Math.ceil((double) numQuestions / 2);
        pageLabels = new PageLabels(numPages);

        /*
        Generate the side panel
         */
        initialize();

        /*
        Set the text and clicking action of each page label
         */
        for (int i = 0; i < numPages; i++) {
            Label label = pageLabels.getLabel(i);
            label.setText("Page " + (i + 1));
            int index = i;
            label.setOnMouseClicked(e -> page.updatePage(index));
        }
    }
}
