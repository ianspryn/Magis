package com.magis.app.settings;

import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.icons.MaterialIcons;
import com.magis.app.models.StudentModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsPage {

    public static void Page() {
        StudentModel.Student student = Main.studentModel.getStudent(Main.username);

        /*
        Master
         */
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-settings");

        /*
        Middle
         */
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(900);

        HBox profileSettings = new HBox();

        Label name = new Label(student.getFullName());
        Button editName = UIComponents.CreateSVGIconButton(MaterialIcons.edit, 20);
        Button changePassword = new Button("Change Password");

        profileSettings.getChildren().addAll(name, editName, changePassword);

        vBox.getChildren().addAll(profileSettings);

        borderPane.setCenter(vBox);

        Main.setScene(borderPane, "Settings");
    }
}
