package com.magis.app.home;

import com.magis.app.Main;
import com.magis.app.UI.RingProgressIndicator;
import com.magis.app.UI.UIComponents;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class HomePage {
    public static Scene Page() {
//        Stage window = new Stage();
//        window.setTitle("Home");

        /*
        Master
         */
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-home");

        /*
        Top
         */
        HBox hBox = UIComponents.CreateTitleBar();
        hBox.setId("toolbar");
        borderPane.setTop(hBox);

        /*
        Middle
         */
        VBox vBox = new VBox();
        vBox.getStyleClass().add("chapter-box-container");
        vBox.setMaxWidth(750);


        ArrayList<String> images = new ArrayList<String>();
        images.add("https://i.imgur.com/vtFDQjM.jpg");
        images.add("https://i.imgur.com/GaXWWQv.jpg");
        images.add("https://i.imgur.com/XbYAHWT.png");
        images.add("https://i.imgur.com/EluEuRa.jpg");
        images.add("https://i.imgur.com/FAZsYAa.png");
        images.add("https://i.imgur.com/xTPmAr8.jpg");
        images.add("https://i.imgur.com/agyRhd6.jpg");
        images.add("https://i.imgur.com/VsSM73J.png");
        images.add("https://i.imgur.com/3GaAzX5.jpg");
        images.add("https://i.imgur.com/ME7VfCR.jpg");


        //for each chapter
        for (int i = 0; i < 10; i++) {
            int lol = i;
            //master box
            HBox chapterBox = new HBox();

            //TODO: go to chapter
            chapterBox.setOnMouseClicked(e -> {
                System.out.println("Clicked on " + lol);
            });
            chapterBox.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            chapterBox.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

            chapterBox.getStyleClass().add("chapter-box");

            //Left image
            ImageView imageView = new ImageView(images.get(i));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);

            //Separator
            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            separator.setMaxHeight(200);
            separator.setPadding(new Insets(0, 35, 0, 35));

            //Progress and text description
            VBox chapterInfo = new VBox();
            chapterBox.setAlignment(Pos.CENTER_LEFT);

            //Progress
            RingProgressIndicator indicator = new RingProgressIndicator();
            indicator.setProgress(25);

            //text
            Button button = new Button("I AM TEXT # " + i);

            chapterInfo.getChildren().addAll(indicator, button);

            chapterBox.getChildren().addAll(imageView, separator, chapterInfo);
            vBox.getChildren().add(chapterBox);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.getStyleClass().add("chapter-box-scrollpane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        StackPane contentHolder = new StackPane(vBox);
        contentHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        scrollPane.setContent(contentHolder);
        borderPane.setCenter(scrollPane);




        Scene scene = new Scene(borderPane, 300, 200);
        scene.getStylesheets().add("com/magis/app/css/style.css");

//        window.setScene(scene);
//        window.show();

        return scene;
    }

    public void centerNodeInScrollPane(ScrollPane scrollPane, Node node) {
        double h = scrollPane.getContent().getBoundsInLocal().getHeight();
        double y = (node.getBoundsInParent().getMaxY() +
                node.getBoundsInParent().getMinY()) / 2.0;
        double v = scrollPane.getViewportBounds().getHeight();
        scrollPane.setVvalue(scrollPane.getVmax() * ((y - 0.5 * v) / (h - v)));
    }
}
