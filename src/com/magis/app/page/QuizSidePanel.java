package com.magis.app.page;

import com.magis.app.Main;
import javafx.scene.control.Label;

public class QuizSidePanel extends PageSidePanel {



    public QuizSidePanel(int chapterIndex, int numPages) {
        super(chapterIndex);

        setNumPages(numPages);

        /*
        Generate the side panel
         */
        initialize();

        /*
        Set the text and clicking action of each page label
         */
        for (int i = 0; i < getNumPages(); i++) {
            Label label = getPageLabel(i);
            label.setText("Page " + (i + 1));
            int index = i;
            label.setOnMouseClicked(e -> page.updatePage(index));
        }
    }
}
