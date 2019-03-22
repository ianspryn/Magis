package com.magis.app;

import com.magis.app.home.HomePage;
import com.magis.app.login.Login;
import com.magis.app.models.LessonModel;
import com.magis.app.models.QuizzesModel;
import com.magis.app.models.StudentModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application{

    public static Stage window;
    public static Scene scene;
    public static LessonModel lessonModel;
    public static StudentModel studentModel;
    public static QuizzesModel quizzesModel;
    public static String username = "ianspryn";
    public static double width, height;

    @Override
    public void start(Stage primaryStage) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        width = width * 3 / 4;
        height = screenSize.height;
        height = height * 3 / 4;

        window = primaryStage;
        lessonModel = new LessonModel();
        studentModel = new StudentModel(lessonModel);
        int result = studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.initializeStudent("ianspryn");
        quizzesModel = new QuizzesModel();
//        Button button = new Button();
//        button.setOnAction(e -> System.out.println("hi"));
//        button.setText("woo");
//
//        StackPane layout = new StackPane();
//        layout.getChildren().add(button);

//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.setTitle("Magis");

//        HomePage.Page();
        Login.Page();
        primaryStage.show();
    }

    public static void setScene(Scene newScene) {
        scene = newScene;
        window.setScene(newScene);
        window.setTitle("Magis");
    }

    public static void setScene(Scene newScene, String windowTitle) {
        scene = newScene;
        window.setScene(newScene);
        window.setTitle(windowTitle);
    }

    public static void main(String[] args) { launch(args); }

}