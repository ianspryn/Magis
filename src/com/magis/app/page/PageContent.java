package com.magis.app.page;

import com.jfoenix.controls.JFXScrollPane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public abstract class PageContent {
    private ScrollPane scrollPane;

    public PageContent() {
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPaneContent(Node node) {
        scrollPane.setContent(node);
        JFXScrollPane.smoothScrolling(scrollPane);
    }

    abstract void update(int pageIndex);

    abstract boolean buildPage(int index);
}
