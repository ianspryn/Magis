package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public abstract class Page {

    private PageSidePanel pageSidePanel;
    private PageContent pageContent;
    private StackPane master;
    private BorderPane borderPane;
    private BorderPane sideBar;
    private BorderPane mainArea;
    private HBox homeHBox;
    private BorderPane bottomNavigation;
    private StackPane leftButton;
    private StackPane rightButton;

    private String title;
    private int currentPage;

    public Page(PageSidePanel pageSidePanel, PageContent pageContent, String title) {
        this.pageSidePanel = pageSidePanel;
        this.pageContent = pageContent;

        master = new StackPane();
        borderPane = new BorderPane();
        sideBar = new BorderPane();
        mainArea = new BorderPane();
        bottomNavigation = new BorderPane();
        this.title = title;
        currentPage = 0;

        initialize();
    }

    private void initialize() {
        master.getStyleClass().add("background");

        /*
        master borderPane
         */
        borderPane.getStyleClass().add("borderpane-page");
        UIComponents.fadeAndTranslate(borderPane,0.2,0.2,-10,0,0,0);

        /*
        sidebar
         */
        sideBar.getStyleClass().add("sidebar");
        homeHBox = UIComponents.createHomeBox();
        homeHBox.setOnMouseClicked(e -> {
            if (Main.takingTest) {
                String title = "Exit Test";
                String content = "Are you sure you want to exit? All test progress will be lost!";
                if (UIComponents.confirmMessage(title, content)) {
                    Main.takingTest = false;
                    HomePage.goHome(borderPane);
                }
            } else {
                HomePage.goHome(borderPane);
            }
        });
        sideBar.setTop(homeHBox);
        sideBar.setLeft(pageSidePanel.getScrollPane());
        borderPane.setLeft(sideBar);

        /*
        main area
         */
        mainArea.setCenter(pageContent.getScrollPane());
        UIComponents.fadeAndTranslate(pageContent.getScrollPane(),0.15,0.2,0,0,-10,0);

        /*
        navigation
         */
        bottomNavigation.getStyleClass().add("navigation-content");
        bottomNavigation.setPadding(new Insets(10,10,10,10));

        //left navigation
        leftButton = UIComponents.createNavigationButton("<");
        leftButton.setVisible(false);
        leftButton.setOnMouseClicked(e -> {
            if (currentPage > 0) updatePage(currentPage - 1);
        });

        //right navigation
        rightButton = UIComponents.createNavigationButton(">");
        rightButton.setOnMouseClicked(e -> {
            if (currentPage < pageSidePanel.getNumPages() - 1) updatePage(currentPage + 1);
        });

        bottomNavigation.setLeft(leftButton);
        bottomNavigation.setRight(rightButton);
        UIComponents.fadeAndTranslate(bottomNavigation,0.15,0.3,0,0,0,0);

        mainArea.setCenter(pageContent.getScrollPane());
        mainArea.setBottom(bottomNavigation);
        borderPane.setCenter(mainArea);
        master.getChildren().add(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        Main.setScene(master, title);
    }

    public PageSidePanel getPageSidePanel() {
        return pageSidePanel;
    }

    public PageContent getPageContent() {
        return pageContent;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void updateNavigation(int currentPage) {
        if (currentPage == 0) leftButton.setVisible(false);
        else leftButton.setVisible(true);
        if (currentPage == pageSidePanel.getNumPages() - 1) rightButton.setVisible(false);
        else rightButton.setVisible(true);
    }

    abstract void updatePage(int pageIndex);
}
