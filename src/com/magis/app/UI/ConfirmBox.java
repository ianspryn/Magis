package com.magis.app.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ConfirmBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label(message);

        HBox buttons = new HBox();
        buttons.setSpacing(20);
        Button cancelButton = new Button("Cancel");
//        cancelButton.setOnAction(e -> );
        Button continueButton = new Button("Continue");
        buttons.getChildren().addAll(cancelButton, continueButton);

        vBox.getChildren().addAll(label, buttons);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

    }

}
