package com.magis.app.login;

import com.jfoenix.controls.*;
import com.magis.app.Main;
import com.magis.app.UI.Alert;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Login {
    private static Rectangle rectangle;
    private static ImageView magisLogo;
    private static GridPane gridPane;
    private static JFXTextField userNameTextField;
    private static JFXTextField firstNameTextField;
    private static JFXTextField lastNameTextField;
    private static JFXButton SignInUp;
    private static JFXButton bottomButton;

    public static void Page() {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);

        StackPane boxBackground = new StackPane();
        boxBackground.getStyleClass().addAll("drop-shadow");

        rectangle = new Rectangle();
        rectangle.setHeight(450);
        rectangle.setWidth(450);
//        rectangle.setFill(Color.valueOf("#ededed"));
        rectangle.setFill(Color.valueOf("#ddd")); //more contrast if needed

        magisLogo = new ImageView("https://res.cloudinary.com/ianspryn/image/upload/Magis/magis-color-small.png");
        magisLogo.getStyleClass().addAll("drop-shadow");

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(25,25,25,25));

        /*
        Sign-in area
         */
        userNameTextField = new JFXTextField();
        userNameTextField.setPromptText("Username");
        userNameTextField.getStyleClass().add("sign-in-field");

        userNameTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) attemptSignIn(userNameTextField.getText());
        });

        /*
        Sign-up area
         */
        firstNameTextField = new JFXTextField();
        firstNameTextField.setPromptText("First name");
        firstNameTextField.getStyleClass().add("sign-in-field");
        firstNameTextField.setVisible(false);

        lastNameTextField = new JFXTextField();
        lastNameTextField.setPromptText("Last name");
        lastNameTextField.getStyleClass().add("sign-in-field");
        lastNameTextField.setVisible(false);

        lastNameTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) attemptSignUp(userNameTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText());
        });

        gridPane.add(userNameTextField, 0, 1);
        gridPane.add(firstNameTextField,0,2);
        gridPane.add(lastNameTextField,0,3);

        SignInUp = new JFXButton("Sign In");
        SignInUp.setOnMouseClicked(e -> attemptSignIn(userNameTextField.getText()));

        bottomButton = new JFXButton("Create Account");
        bottomButton.getStyleClass().add("create-account-button");
        bottomButton.setOnMouseClicked(e -> showSignUp());

        boxBackground.getChildren().addAll(rectangle, magisLogo, gridPane, SignInUp, bottomButton);
        StackPane.setAlignment(magisLogo, Pos.TOP_CENTER);
        StackPane.setMargin(magisLogo, new Insets(50,0,0,0));

        StackPane.setAlignment(gridPane, Pos.CENTER);

        StackPane.setAlignment(SignInUp, Pos.CENTER);
        StackPane.setMargin(SignInUp, new Insets(120,0,0,0));

        StackPane.setAlignment(bottomButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(bottomButton, new Insets(0,0,20,0));

        content.getChildren().add(boxBackground);

        Main.setScene(content, "Magis");
    }

    private static void attemptSignIn(String username) {
        Main.studentModel.initializeStudent(username);
        if (username.length() == 0) {
            Alert.showAlert("Error", "Please enter a username.");
        } else if (Main.studentModel.getStudent(username) != null) { //if that student exists
            Main.username = username;
            Main.isLoggedIn = true;
            HomePage.getInstance().Page();
        } else { //Spit out an error and tell the student to try again
            Alert.showAlert("Error", "Username not found. Please try again.");
        }
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

    private static void showSignUp() {
        grow();
        userNameTextField.setOnKeyPressed(null);
        userNameTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                attemptSignIn(userNameTextField.getText());
            }
        });
        firstNameTextField.setVisible(true);
        lastNameTextField.setVisible(true);
        SignInUp.setText("Sign up");
        SignInUp.setOnMouseClicked(e -> attemptSignUp(userNameTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText()));
        bottomButton.setText("Back");
        bottomButton.setOnMouseClicked(e -> showSignIn());
    }

    private static void showSignIn() {
        shrink();
        userNameTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                attemptSignIn(userNameTextField.getText());
            }
        });
        firstNameTextField.setVisible(false);
        lastNameTextField.setVisible(false);
        SignInUp.setText("Sign in");
        SignInUp.setOnMouseClicked(e -> attemptSignIn(userNameTextField.getText()));
        bottomButton.setText("Create Account");
        bottomButton.setOnMouseClicked(e -> showSignUp());
    }

    private static void grow() {
        UIComponents.translate(magisLogo, 0.3,0,0,0,-50);
        UIComponents.scale(rectangle,0.3,1.222);
        UIComponents.translate(gridPane,0.3,0,0,0,-25);
        UIComponents.translate(SignInUp,0.3,0,0,0,50);
        UIComponents.translate(bottomButton,0.3,0,0,0,50);
    }

    private static void shrink() {
        UIComponents.translate(magisLogo, 0.3,0,0,-50,0);
        UIComponents.scale(rectangle,0.3,1);
        UIComponents.translate(gridPane,0.3,0,0,-25,0);
        UIComponents.translate(SignInUp,0.3,0,0,50,0);
        UIComponents.translate(bottomButton,0.3,0,0,50,0);
    }

}
