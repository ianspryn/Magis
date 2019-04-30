package com.magis.app.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import com.magis.app.login.Login;
import com.magis.app.models.StudentModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SettingsPage {

    private static StudentModel.Student student;

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

        HBox nameSettings = new HBox();
        Label name = new Label(student.getFullName());
        name.getStyleClass().add("settings-name");
        Button editName = UIComponents.CreateSVGIconButton(MaterialIcons.edit, 20);
        editName.getStyleClass().add("material-icons-dark");
        editName.setPadding(new Insets(12,0,0,0));
//        editName.setOnMouseClicked(e -> goToLesson(borderPane, student.getRecentChapter(), true));
        editName.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        editName.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        nameSettings.getChildren().addAll(name, editName);
        JFXButton changePassword = new JFXButton("Change Password");
        changePassword.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");

        profileAnchorPane.getChildren().addAll(nameSettings, changePassword);
        AnchorPane.setLeftAnchor(nameSettings,0.0);
        AnchorPane.setRightAnchor(changePassword,0.0);

        vBox.getChildren().add(profileAnchorPane);

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
        });
        rectangle.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        rectangle.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        rectangle.setFill(Color.valueOf(hexColor));

        return rectangle;

    }
}
