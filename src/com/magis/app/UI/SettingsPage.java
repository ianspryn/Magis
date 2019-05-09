package com.magis.app.UI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.magis.app.Main;
import com.magis.app.UI.Alert;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import com.magis.app.login.Login;
import com.magis.app.login.Password;
import com.magis.app.models.StudentModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsPage {

    private static StudentModel.Student student;
    private static VBox passwordSettings;
    private static JFXButton changePassword;
    private static AtomicBoolean validPassword;
    private static Label notSatisfied;
    private static JFXPasswordField oldPassword;
    private static JFXPasswordField newPassword;

    public static void Page() {
        student = Main.studentModel.getStudent();

        UIComponents.GenericPage page = new UIComponents.GenericPage();

        page.getMastervBox().setAlignment(Pos.TOP_CENTER);

        page.getPageTitle().setText("Settings");
        page.getBackButton().setOnMouseClicked(e -> HomePage.goHome(page.getScrollPane()));


        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(500);
        vBox.setPadding(new Insets(25,25,25,25));
        vBox.setSpacing(25);

        page.getMastervBox().getChildren().add(vBox);
        /*
        Profile
         */
        AnchorPane profileAnchorPane = new AnchorPane();

        /*
        Name settings
         */
        HBox nameSettings = new HBox();
        nameSettings.setAlignment(Pos.CENTER);

        //declare
        Label fullName = new Label(student.getFullName());
        Button editName = UIComponents.CreateSVGIconButton(MaterialIcons.edit, 20);
        JFXTextField firstName = new JFXTextField(student.getFirstName());
        firstName.getStyleClass().add("jfx-edit-name-field");
        JFXTextField lastName = new JFXTextField(student.getLastName());
        lastName.getStyleClass().add("jfx-edit-name-field");
        JFXButton submitName = new JFXButton("Submit");
        JFXButton cancelName = new JFXButton("Cancel");
        VBox editNameBox = new VBox();
        HBox topName = new HBox();
        HBox nameButtons = new HBox();
        editNameBox.setAlignment(Pos.CENTER);
        editNameBox.setSpacing(25);
        editNameBox.getChildren().addAll(topName, nameButtons);
        topName.getChildren().addAll(firstName, lastName);
        topName.setAlignment(Pos.CENTER);
        topName.setSpacing(25);
        nameButtons.getChildren().addAll(submitName, cancelName);
        nameButtons.setAlignment(Pos.CENTER);
        nameButtons.setSpacing(25);

        //only show full name and edit button at first
        nameSettings.getChildren().addAll(fullName, editName);

        fullName.getStyleClass().add("settings-name");

        //edit button
        editName.getStyleClass().add("material-icons-dark");
        editName.setOnMouseClicked(e -> {
            nameSettings.getChildren().clear();
            nameSettings.getChildren().addAll(editNameBox);
        });
        editName.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        editName.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        //submit button
        submitName.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");
        submitName.setOnMouseClicked(e -> {
            if (firstName.getText().length() == 0 || lastName.getText().length() == 0) {
                Alert.showAlert("Error", "Name cannot be empty!");
            } else {
                student.setFirstName(firstName.getText());
                student.setLastName(lastName.getText());
                nameSettings.getChildren().clear();
                nameSettings.getChildren().addAll(fullName, editName);
                fullName.setText(student.getFullName());

            }
        });

        //cancel button
        cancelName.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");
        cancelName.setOnMouseClicked(e -> {
            nameSettings.getChildren().clear();
            nameSettings.getChildren().addAll(fullName, editName);
        });

        /*
        Password Settings
         */
        GridPane passwordVerifierGridPane = UIComponents.createPasswordVerifierHelper();
        notSatisfied = (Label) passwordVerifierGridPane.getChildren().get(8);

        passwordSettings = new VBox();
        passwordSettings.setSpacing(25);
        passwordSettings.setAlignment(Pos.CENTER);

        VBox passwordFields = new VBox();
        passwordFields.setSpacing(25);
        passwordFields.setPadding(new Insets(55,0,0,0));
        HBox passwordFieldAndHelper = new HBox();
        passwordFieldAndHelper.setSpacing(25);
        passwordFieldAndHelper.setAlignment(Pos.CENTER);

        HBox passwordButtons = new HBox();
        passwordButtons.setSpacing(25);
        passwordButtons.setAlignment(Pos.CENTER);

        changePassword = new JFXButton("Change Password");
        changePassword.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");
        JFXButton submitPassword = new JFXButton("Change Password");
        submitPassword.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");
        JFXButton cancelPassword = new JFXButton("Cancel");
        cancelPassword.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");
        oldPassword = new JFXPasswordField();
        oldPassword.setPromptText("Old password");
        oldPassword.setPrefWidth(150);
        newPassword = new JFXPasswordField();
        newPassword.setPromptText("New password");
        newPassword.setPrefWidth(150);

        passwordButtons.getChildren().addAll(submitPassword, cancelPassword);

        //only show "change password" at first
        passwordSettings.getChildren().addAll(changePassword);

        validPassword = new AtomicBoolean(false);

        changePassword.setOnMouseClicked(e -> {
            passwordFields.getChildren().clear();
            passwordFieldAndHelper.getChildren().clear();
            passwordSettings.getChildren().clear();

            passwordFields.getChildren().addAll(oldPassword, newPassword);
            passwordFieldAndHelper.getChildren().addAll(passwordFields, passwordVerifierGridPane);
            passwordSettings.getChildren().addAll(passwordFieldAndHelper, passwordButtons);
        });

        newPassword.setOnKeyReleased(e -> {
            validPassword.set(Password.checkRequirements(newPassword.getText(), passwordVerifierGridPane));
            if (validPassword.get()) {
                notSatisfied.setVisible(false);
            }
        });

        newPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && validPassword.get()) {
                attemptPasswordChange();
            } else if (e.getCode() == KeyCode.ENTER && !validPassword.get()) {
                notSatisfied.setVisible(true);
            }
        });

        submitPassword.setOnMouseClicked(e -> attemptPasswordChange());

        cancelPassword.setOnMouseClicked(e -> {
            //Clear any password info entered so it's empty if the user comes back
            oldPassword.setText("");
            newPassword.setText("");
            passwordSettings.getChildren().clear();
            passwordSettings.getChildren().add(changePassword);
            notSatisfied.setVisible(false);
            Password.checkRequirements("", passwordVerifierGridPane); //reset to all red

        });

        profileAnchorPane.getChildren().addAll(nameSettings, passwordSettings);
        AnchorPane.setLeftAnchor(nameSettings,0.0);
        AnchorPane.setRightAnchor(changePassword,0.0);

        vBox.getChildren().addAll(nameSettings, passwordSettings);

        /*
        Dark mode
         */
        AnchorPane darkModeAnchorPane = new AnchorPane();
        HBox darkModeTextContainer = new HBox();
        darkModeTextContainer.setPadding(new Insets(20,0,0,0));

        Label darkModeText = new Label("Use dark mode");
        darkModeText.getStyleClass().add("settings-page-text");

        darkModeTextContainer.getChildren().add(darkModeText);

        JFXToggleButton darkModeToggle = new JFXToggleButton();
        darkModeToggle.setSelected(student.getDarkMode());
        darkModeToggle.setOnAction(e -> {
            //Remove the current light or dark css
            String lightOrDark = student.getDarkMode() ? "com/magis/app/css/darkmode.css" : "com/magis/app/css/lightmode.css";
            Main.scene.getStylesheets().remove(lightOrDark);

            student.setDarkMode(darkModeToggle.isSelected());

            //apply the new light or dark css
            lightOrDark = student.getDarkMode() ? "com/magis/app/css/darkmode.css" : "com/magis/app/css/lightmode.css";
            Main.scene.getStylesheets().add(lightOrDark);
        });

        darkModeAnchorPane.getChildren().addAll(darkModeTextContainer, darkModeToggle);
        AnchorPane.setLeftAnchor(darkModeText, 0.0);
        AnchorPane.setRightAnchor(darkModeToggle,0.0);

        vBox.getChildren().add(darkModeAnchorPane);

        /*
        Theme color
         */
        AnchorPane themeAnchorPane = new AnchorPane();

        Label themeText = new Label("Change theme color");
        themeText.getStyleClass().add("settings-page-text");

        HBox themeBox = new HBox();

        Rectangle pinkTheme = colorRectangle("#D81B60", "pink"); /* Pink 600 */
        Rectangle purpleTheme = colorRectangle("#8E24AA", "purple"); /* Purple 600 */
        Rectangle cyanTheme = colorRectangle("#00ACC1", "cyan"); /* Cyan 600 */
        Rectangle greenTheme = colorRectangle("#43A047", "green"); /* Green 600 */
        Rectangle grayTheme = colorRectangle("#546E7A", "blue-gray"); /* blue gray 600 */

        themeBox.getChildren().addAll(pinkTheme, purpleTheme, cyanTheme, greenTheme, grayTheme);

        themeAnchorPane.getChildren().addAll(themeText, themeBox);
        AnchorPane.setLeftAnchor(themeText, 0.0);
        AnchorPane.setRightAnchor(themeBox, 0.0);

        vBox.getChildren().add(themeAnchorPane);

        /*
        Disable Animations
         */
        AnchorPane animationsAnchorPane = new AnchorPane();
        HBox animationTextContainer = new HBox();
        animationTextContainer.setPadding(new Insets(20,0,0,0));

        Label animationText = new Label("Use animations");
        animationText.getStyleClass().add("settings-page-text");

        animationTextContainer.getChildren().add(animationText);

        JFXToggleButton animationToggle = new JFXToggleButton();
        animationToggle.setSelected(student.useAnimations());
        animationToggle.setOnAction(e -> {
            if (!animationToggle.isSelected()) HomePage.getInstance().disableAnimations();
            student.setAnimations(animationToggle.isSelected());
            Main.useAnimations = animationToggle.isSelected();
        });

        animationsAnchorPane.getChildren().addAll(animationTextContainer, animationToggle);
        AnchorPane.setLeftAnchor(animationText, 0.0);
        AnchorPane.setRightAnchor(animationToggle,0.0);

        vBox.getChildren().add(animationsAnchorPane);

        /*
        Delete user
         */
        HBox deleteUserContainer = new HBox();
        deleteUserContainer.setPadding(new Insets(150,0,0,0));
        deleteUserContainer.setAlignment(Pos.CENTER);

        JFXButton deleteUser = new JFXButton("Delete Account");
        deleteUser.getStyleClass().add("delete-user-button");
        deleteUser.setOnMouseClicked(e -> {
            if (UIComponents.confirmMessage("Delete user", "Do you wish to delete your profile?")) { //confirm user wants to deleteUser
                if (UIComponents.confirmMessage("Delete user", "Are you sure? This action cannot be undone!")) { //double confirm with user
                    Main.studentModel.deleteUser(Main.username);
                    Login.Page();
                }
            }
        });

        deleteUserContainer.getChildren().add(deleteUser);
        vBox.getChildren().add(deleteUserContainer);

        Main.setScene(page.getMaster(), "Settings");
    }

    private static void attemptPasswordChange() {
        if (!validPassword.get()) {
            notSatisfied.setVisible(true);
        }
        else if (!Password.passwordMatches(student, oldPassword.getText())) {
            Alert.showAlert("Incorrect password", "Old password does not match current password. Please try again.");
        }
        //make sure it is not the same as the old password
        else if (newPassword.getText().equals(oldPassword.getText())) {
            Alert.showAlert("Password must be unique", "New password cannot be the same as current password.");
        }
        //success
        else {
            String salt = student.getSalt();
            String passwordHash = Password.hash(newPassword.getText(), salt); //hash the password
            student.setPasswordHash(passwordHash);
            //Clear any password info entered so it's empty if the user comes back
            oldPassword.setText("");
            newPassword.setText("");
            passwordSettings.getChildren().clear();
            passwordSettings.getChildren().add(changePassword);
            Alert.showAlert("Success!", "Password successfully changed!");
        }
    }

    private static Rectangle colorRectangle(String hexColor, String name) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        rectangle.setOnMouseClicked(e -> {
            //remove old theme
            Main.scene.getStylesheets().remove("com/magis/app/css/" + student.getTheme() + ".css");
            student.setTheme(name);
            //apply new theme
            Main.scene.getStylesheets().add("com/magis/app/css/" + name + ".css");
            UIComponents.applyShadowColor();
        });
        rectangle.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        rectangle.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        rectangle.setFill(Color.valueOf(hexColor));

        return rectangle;

    }
}
