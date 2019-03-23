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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Login {

    public static void Page() {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);

        StackPane boxBackground = new StackPane();
        boxBackground.getStyleClass().addAll("drop-shadow");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(450);
        rectangle.setWidth(450);
        rectangle.setFill(Color.valueOf("#eee"));

        ImageView magisLogo = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/magis-color-small.png");
        magisLogo.getStyleClass().addAll("drop-shadow");

        /*
        Sign-in area
         */
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));

        Label userName = new Label("Username:");
        TextField userNameTextField = new TextField();
        userNameTextField.getStyleClass().add("sign-in-field");


        userNameTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                attemptSignIn(userNameTextField.getText());
            }
        });
        gridPane.add(userName, 0, 1);
        gridPane.add(userNameTextField,1,1);
        Button signIn = new Button("Sign In");
        signIn.setOnMouseClicked(e -> attemptSignIn(userNameTextField.getText()));

        Button createAccount = new Button("Create Account");
        createAccount.setOnMouseClicked(e -> Signup.Page(userNameTextField.getText()));

        boxBackground.getChildren().addAll(rectangle, magisLogo, gridPane, signIn, createAccount);
        StackPane.setAlignment(magisLogo, Pos.TOP_CENTER);
        StackPane.setMargin(magisLogo, new Insets(50,0,0,0));

        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setMargin(gridPane, new Insets(0,57,0,0));

        StackPane.setAlignment(signIn, Pos.CENTER);
        StackPane.setMargin(signIn, new Insets(120,0,0,0));

        StackPane.setAlignment(createAccount, Pos.BOTTOM_CENTER);
        StackPane.setMargin(createAccount, new Insets(0,0,20,0));

        content.getChildren().add(boxBackground);
        Scene scene = new Scene(content, Main.width, Main.height);
        scene.getStylesheets().add("com/magis/app/css/style.css");
        Main.setScene(scene, "Magis");
    }

    private static void attemptSignIn(String username) {
        Main.studentModel.initializeStudent(username);
        if (username.length() == 0) {
            Alert.showAlert("Error", "Please enter a username.");
        } else if (Main.studentModel.getStudent(username) != null) { //if that student exists
            Main.username = username;
            HomePage.Page();
        } else { //Spit out an error and tell the student to try again
            Alert.showAlert("Error", "Username not found. Please try again.");
        }
    }

}
