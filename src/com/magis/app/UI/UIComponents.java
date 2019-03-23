package com.magis.app.UI;

import com.magis.app.Main;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

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

    public static HBox getHomeBox() {
        HBox home = new HBox();
        home.setSpacing(20);
        home.setMinWidth(300);
        home.setPadding(new Insets(15,0,0,20));

        //Home icon
        Button homeButton = UIComponents.getHomeButton();

        //Magis logo
        ImageView magisLogo = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/magis-small.png");
        magisLogo.setPreserveRatio(true);
        magisLogo.setFitWidth(175);

        //listeners
        home.setOnMouseClicked(e -> HomePage.Page());
        home.setOnMouseEntered(e -> Main.scene.setCursor(javafx.scene.Cursor.HAND));
        home.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        home.getChildren().addAll(homeButton, magisLogo);
        return home;
    }
}
