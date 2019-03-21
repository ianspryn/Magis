package com.magis.app.UI;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TestPageContent {

    private ArrayList<VBox> pageContent;

    public VBox getPageContent(int index) {
        return pageContent.get(index);
    }

    public TestPageContent() {
        pageContent = new ArrayList<>();
    }

    public void add(VBox pageContent) {
        this.pageContent.add(pageContent);
    }

    public void add(int index, VBox pageContent) {
        this.pageContent.add(index, pageContent);
    }

}
