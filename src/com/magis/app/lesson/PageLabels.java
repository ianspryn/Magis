package com.magis.app.lesson;

import com.magis.app.Main;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class PageLabels {

    private ArrayList<Label> labels;
    private int numLabels;

    public PageLabels(int numLabels) {
        labels = new ArrayList<>();
        this.numLabels = numLabels;
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < numLabels; i++) {
            Label label = new Label();
            label.setPadding(new Insets(0, 0, 0, 15));
            label.getStyleClass().add("lesson-side-panel-text");
            label.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            label.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            labels.add(label);
        }
    }

    public int getNumLabels() {
        return labels.size();
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public Label getLabel(int index) {
        return labels.get(index);
    }
}
