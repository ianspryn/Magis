package com.magis.app.UI;

import com.magis.app.Main;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

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

}
