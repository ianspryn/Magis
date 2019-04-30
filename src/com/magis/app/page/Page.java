package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public abstract class Page {

    protected PageSidePanel pageSidePanel;
    protected PageContent pageContent;
    protected StackPane master;
    protected BorderPane borderPane;
    protected BorderPane sideBar;
    protected BorderPane mainArea;
    protected HBox homeHBox;
    protected BorderPane bottomNavigation;
    protected StackPane leftButton;
    protected StackPane rightButton;

    protected String title;
    protected int currentPage;
    protected int numPages;

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

    protected void initialize() {
        master.getStyleClass().add("background");

        /*
        master borderPane
         */
        borderPane.getStyleClass().add("borderpane-page");
        UIComponents.fadeOnAndTranslate(borderPane,0.2,0.2,-10,0,0,0);

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
        UIComponents.fadeOnAndTranslate(pageContent.getScrollPane(),0.15,0.2,0,0,-10,0);

        /*
        navigation
         */
        bottomNavigation.getStyleClass().add("navigation-content");
        bottomNavigation.setPadding(new Insets(10,10,10,10));


        //keyboard navigation
        /*
        Currently disabled because there is no known way to capture key presses when the scene isn't focused
        Which, the scene immediately becomes out of focus the moment the user clicks anything
         */
//        Main.scene.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.LEFT) {
//                System.out.println(currentPage);
//                System.out.println(currentPage > 0);
//                System.out.println();
//                if (currentPage > 0) updatePage(currentPage - 1);
//            }
//
//            if (e.getCode() == KeyCode.RIGHT) {
//                System.out.println(currentPage);
//                System.out.println(numPages);
//                System.out.println(currentPage < numPages - 1);
//                System.out.println();
//                if (currentPage < numPages - 1) updatePage(currentPage + 1);
//            }
//        });

        //left navigation
        leftButton = UIComponents.createNavigationButton("<");
        leftButton.setOnMouseClicked(e -> {
            if (currentPage > 0) updatePage(currentPage - 1);
        });

        //right navigation
        rightButton = UIComponents.createNavigationButton(">");
        rightButton.setOnMouseClicked(e -> {
            if (currentPage < numPages - 1) updatePage(currentPage + 1);
        });

        bottomNavigation.setLeft(leftButton);
        bottomNavigation.setRight(rightButton);
        UIComponents.fadeOnAndTranslate(bottomNavigation,0.15,0.3,0,0,0,0);

        mainArea.setCenter(pageContent.getScrollPane());
        mainArea.setBottom(bottomNavigation);
        borderPane.setCenter(mainArea);
        master.getChildren().add(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        Main.setScene(master, title);
    }

    public void updateNavigation(int currentPage) {
        if (currentPage == 0) leftButton.setVisible(false);
        else leftButton.setVisible(true);
        if (currentPage == numPages - 1) rightButton.setVisible(false);
        else rightButton.setVisible(true);
    }

    abstract void updatePage(int pageIndex);
}
