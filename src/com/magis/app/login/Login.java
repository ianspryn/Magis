package com.magis.app.login;

import com.magis.app.Main;
import com.magis.app.UI.Alert;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;

public class Login {

    public static void Page() {
        VBox content = new VBox();
        content.getStyleClass().add("welcome-box-container");

        StackPane boxBackground = new StackPane();
        boxBackground.getStyleClass().addAll("drop-shadow");


        ImageView magisLogo = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/magis-small.png");

        /*
        Sign-in area
         */
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));

        Label userName = new Label("Username:");
        TextField userTextField = new TextField();

        gridPane.add(userName, 0, 1);
        gridPane.add(userTextField,1,1);

        Button signIn = new Button("Sign in");
        signIn.setOnMouseClicked(e -> {
            String username = userTextField.getText();
            //if that student exists
            if (Main.studentModel.getStudent(username) != null) {
                Main.username = username;
                HomePage.Page();
            } else {
                //Spit out an error and tell the student to try again
                Alert.showAlert("Error", "Username not found. Please try again.");
            }
        });

        Scene scene = new Scene(content, Main.width, Main.height);
        scene.getStylesheets().add("com/magis/app/css/style.css");
        Main.setScene(scene, "Login");
    }

}
