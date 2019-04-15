package com.magis.app.login;

import com.jfoenix.controls.JFXButton;
import com.magis.app.Main;
import com.magis.app.UI.Alert;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Signup {
    public static void Page(String username) {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);

        StackPane boxBackground = new StackPane();
        boxBackground.getStyleClass().addAll("drop-shadow");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(550);
        rectangle.setWidth(550);
        rectangle.setFill(Color.valueOf("#ededed"));
//        rectangle.setFill(Color.valueOf("#dedede")); //more contrast if needed

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
        userNameTextField.setText(username);
        userNameTextField.getStyleClass().add("sign-in-field");

        Label firstName = new Label("First name:");
        TextField firstNameTextField = new TextField();
        firstNameTextField.getStyleClass().add("sign-in-field");

        Label lastName = new Label("Last name:");
        TextField lastNameTextField = new TextField();
        lastNameTextField.getStyleClass().add("sign-in-field");

        gridPane.add(userName, 0, 1);
        gridPane.add(userNameTextField,1,1);
        gridPane.add(firstName,0,2);
        gridPane.add(firstNameTextField,1,2);
        gridPane.add(lastName,0,3);
        gridPane.add(lastNameTextField,1,3);
//        namePane.add(firstName,0,1);
//        namePane.add(firstNameTextField,0,1);
//        namePane.add(lastName,2,1);
//        namePane.add(lastNameTextField,1,1);

        JFXButton signUp = new JFXButton("Sign Up");
        signUp.setOnMouseClicked(e -> attemptSignUp(userNameTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText()));

        JFXButton back = new JFXButton("Back");
        back.setOnMouseClicked(e -> Login.Page());





        boxBackground.getChildren().addAll(rectangle, magisLogo, gridPane, signUp, back);
        StackPane.setAlignment(magisLogo, Pos.TOP_CENTER);
        StackPane.setMargin(magisLogo, new Insets(50,0,0,0));

        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setMargin(gridPane, new Insets(0,57,25,0));


        StackPane.setAlignment(signUp, Pos.CENTER);
        StackPane.setMargin(signUp, new Insets(200,0,0,0));

        StackPane.setAlignment(back, Pos.BOTTOM_CENTER);
        StackPane.setMargin(back, new Insets(0,0,20,0));

        content.getChildren().add(boxBackground);
        Main.setScene(content, "Magis");
    }

    private static void attemptSignUp(String username, String firstName, String lastName) {
        //if that student exists
        if (username.length() == 0 || firstName.length() == 0 || lastName.length() == 0) {
            Alert.showAlert("Error", "Please fill in all of the fields.");
            return;
        }

        //capitalize the first letter of the name
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

        int result = Main.studentModel.addStudent(username, firstName, lastName);

        if (result == -1) {
            Alert.showAlert("Error", "Username already exists. Please try another username.");
        } else {
            Main.studentModel.initializeStudent(username);
            Main.username = username;
            Main.isLoggedIn = true;
            HomePage.getInstance().Page();
        }
    }
}
