package com.magis.app.UI;

import com.magis.app.Main;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class UIComponents {

    public static HBox CreateTitleBar() {
        HBox hBox = new HBox();

        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(5);
        hBox.getStyleClass().add("title-bar");

        Button minimize = UIComponents.CreateSVGIconButton(com.magis.app.icons.MaterialIcons.minimize, 12);
        minimize.setOnAction(e -> Main.window.setIconified(true));
        Button maximize = UIComponents.CreateSVGIconButton(com.magis.app.icons.MaterialIcons.maximize, 12);
        maximize.setOnAction(e -> Main.window.setFullScreen(!Main.window.isFullScreen()));
        Button close = UIComponents.CreateSVGIconButton(com.magis.app.icons.MaterialIcons.close, 12);

        hBox.getChildren().addAll(minimize, maximize, close);

        return hBox;
    }

    public static StackPane createNavigationButton(String chevron) {
        StackPane button = new StackPane();
        Circle leftCircle = new Circle();
        leftCircle.getStyleClass().add("drop-shadow");
        leftCircle.setRadius((35));
        leftCircle.setFill(Paint.valueOf("ffffff"));
        Text leftChevron = new Text(chevron);
        leftChevron.getStyleClass().add("navigation-text");
        button.getChildren().addAll(leftCircle, leftChevron);
        button.setAlignment(Pos.CENTER);
        button.setOnMousePressed(e ->  leftCircle.setFill(Paint.valueOf("ededed")));
        button.setOnMouseReleased(e ->  leftCircle.setFill(Paint.valueOf("ffffff")));
        button.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        button.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        StackPane.setMargin(leftChevron, new Insets(0,0,8,0)); //center "<"

        return button;
    }

    public static Button CreateSVGIconButton(String svg, float scale) {
        SVGPath path = new SVGPath();
        path.setContent(svg);
        path.getStyleClass().add("button-icon");

        // scale to size 20x20 (max)
        Bounds bounds = path.getBoundsInLocal();
        double scaleFactor = scale / Math.max(bounds.getWidth(), bounds.getHeight());
        path.setScaleX(scaleFactor);
        path.setScaleY(scaleFactor);

        Button button = new Button();
        button.setPickOnBounds(true);
        button.setGraphic(path);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("icon-button");

        return button;
    }

    public static Button getHomeButton() {
        Button button = CreateSVGIconButton(MaterialIcons.home, 50);
        button.setDisable(true);
        button.setStyle("-fx-opacity: 1.0");
        return button;
    }

    /**
     * build the home box that goes in the top left corner
     * @return the HBox object that holds the home box
     */
    public static HBox createHomeBox() {
        HBox home = new HBox();
        home.setSpacing(20);
        home.setMinWidth(300);
        home.setPadding(new Insets(15,0,0,20));

        //Home icon and text
        VBox homeVBox = new VBox();

        Button homeButton = UIComponents.getHomeButton();

        Label text = new Label("Home");
        text.getStyleClass().add("home-text");

        homeVBox.getChildren().addAll(homeButton, text);


        //Magis logo
        ImageView magisLogo = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/magis-white-small-v2.png");
        magisLogo.setPreserveRatio(true);
        magisLogo.setFitWidth(175);

        //listeners
        home.setOnMouseEntered(e -> Main.scene.setCursor(javafx.scene.Cursor.HAND));
        home.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));


        home.getChildren().addAll(homeVBox, magisLogo);
        return home;
    }

    /**
     * Pop up box confirming the user's action of exiting
     * @return the user's choice, yes/true = exit, no/false = stay
     */
    public static boolean confirmClose() {
        AtomicBoolean result = new AtomicBoolean(false);
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Test");
        alert.setContentText("Are you sure you want to exit? All test progress will be lost!");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(cancelButton, okButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getText().equals("Yes")) result.set(true);
        });
        return result.get();
    }

    /**
     * Fade in the side panel labels
     * @param node the current label to fade in
     * @param i the current index of the label. Used to add incremental delay of fade in
     * @param delay how long before the animation begins
     * @param duration how long to animate
     * @param fromX where the node should start in its animation for the x-axis
     * @param fromY where the node should start in its animation for the y-axis
     */
    public static void animate(Node node, int i, double delay, double duration, double fromX, double fromY) {
        //fade in
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        //"hack" to hide all the chapters
        fadeTransition.play();
        fadeTransition.pause();

        //move down
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(0);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(0);

        //move down and fade in at the same time
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);

        //wait a certain delay, then animate in
        SequentialTransition sequentialTransition = new SequentialTransition(new PauseTransition(Duration.seconds(delay + ((float)i / 30))), parallelTransition);
        sequentialTransition.play();
    }

    /**
     * Animate a node
     * @param node the node to animate
     * @param delay how long before the animation begins
     * @param duration how long to animate
     * @param fromX where the node should start in its animation for the x-axis
     * @param fromY where the node should start in its animation for the y-axis
     */
    public static void animate(Node node, double delay, double duration, float fromX, float fromY) {
        //fade in
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        //"hack" to initially hide the component
        fadeTransition.play();
        fadeTransition.pause();

        //move down
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(0);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(0);

        //move down and fade in at the same time
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);

        //wait a certain delay, then animate in
        SequentialTransition sequentialTransition = new SequentialTransition(new PauseTransition(Duration.seconds(delay)), parallelTransition);
        sequentialTransition.play();
    }
}
