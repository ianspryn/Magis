package test;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class test extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        TextFlow flow = new TextFlow();
        flow.setLineSpacing(5);
        Text text = new Text("if (");
        JFXTextField textField = new JFXTextField();

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        FontMetrics fm = g2d.getFontMetrics();
        textField.setOnKeyReleased(e -> {
            textField.setPrefWidth(fm.stringWidth(textField.getText()));
            System.out.println(fm.stringWidth(textField.getText()));
        });
//        g2d.dispose();
        Text text2 = new Text(") {\n\t//comment\n}");
        flow.getChildren().addAll(text, textField, text2);

        root.getChildren().addAll(flow);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}