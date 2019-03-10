package com.magis.app.lesson;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class PageLabels {

    private ArrayList<Label> labels;

    public PageLabels(int numLabels) {
        labels = new ArrayList<>();
        for (int i = 0; i < numLabels; i++) {
            labels.add(new Label());
            labels.get(i).getStyleClass().add("lesson-side-panel-text");
        }
    }

    public Label getLabel(int index) {
        return labels.get(index);
    }
}
