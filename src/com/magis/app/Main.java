package com.magis.app;

import com.magis.app.home.HomePage;
import com.magis.app.models.LessonModel;
import com.magis.app.models.QuizzesModel;
import com.magis.app.models.StudentModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application{

    public static Stage window;
    public static Scene scene;
    public static LessonModel lessonModel;
    public static StudentModel studentModel;
    public static QuizzesModel quizzesModel;
    public static String username = "ianspryn";

    public static int width = 900, height = 700;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        lessonModel = new LessonModel();
        studentModel = new StudentModel(lessonModel);
        int result = studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.initializeStudent("ianspryn");
        System.out.println(result);
        quizzesModel = new QuizzesModel();
//        Button button = new Button();
//        button.setOnAction(e -> System.out.println("hi"));
//        button.setText("woo");
//
//        StackPane layout = new StackPane();
//        layout.getChildren().add(button);

//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Magis");

        HomePage.Page();
        primaryStage.show();
    }

    public static void setScene(Scene newScene) {
        scene = newScene;
        window.setScene(newScene);
    }

    public static void setScene(Scene newScene, String windowTitle) {
        scene = newScene;
        window.setScene(newScene);
        window.setTitle(windowTitle);
    }

    public static void main(String[] args) { launch(args); }

}