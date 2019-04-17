package com.magis.app;

import com.magis.app.UI.UIComponents;
import com.magis.app.login.Login;
import com.magis.app.models.LessonModel;
import com.magis.app.models.QuizzesModel;
import com.magis.app.models.StudentModel;
import com.magis.app.models.TestsModel;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application{

    public static Stage window;
    public static Scene scene;
    public static LessonModel lessonModel;
    public static StudentModel studentModel;
    public static QuizzesModel quizzesModel;
    public static TestsModel testsModel;
    public static String username = "";
    public static boolean takingTest = false; //if true, prompt the user with an alert asking when they click to leave the test
    public static boolean isLoggedIn = false;
    public static double width = -1, height = -1;

    @Override
    public void start(Stage primaryStage) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        width = width * 3 / 4;
        height = screenSize.height;
        height = height * 3 / 4;

        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        scene = new Scene(new BorderPane(), width, height);
        window.setScene(scene);
        //default to light, with pink
        scene.getStylesheets().addAll("com/magis/app/css/style.css", "com/magis/app/css/lightmode.css", "com/magis/app/css/pink.css");

        lessonModel = new LessonModel();
        studentModel = new StudentModel(lessonModel);
        quizzesModel = new QuizzesModel();
        testsModel = new TestsModel();
        Login.Page();
        primaryStage.show();
    }

    public void closeProgram() {
        boolean close = true;
        if (takingTest) {
            close = UIComponents.confirmClose();
        }
        if (isLoggedIn) {
            Main.studentModel.getStudent(Main.username).writePageProgress();
        }
        if (close) window.close();
    }

    public static void setScene(Parent root) {
        scene.setRoot(root);
        window.setTitle("Magis");
    }

    public static void setScene(Parent root, String windowTitle) {
        scene.setRoot(root);
        window.setTitle(windowTitle);
    }

    public static void main(String[] args) { launch(args); }

}