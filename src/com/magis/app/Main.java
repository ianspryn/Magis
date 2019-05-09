package com.magis.app;


import com.magis.app.UI.UIComponents;
import com.magis.app.login.Login;
import com.magis.app.models.LessonModel;
import com.magis.app.models.QuizzesModel;
import com.magis.app.models.StudentModel;
import com.magis.app.models.TestsModel;
import com.magis.app.test.diff_match_patch;
import com.magis.app.test.questions.generator.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Main extends Application {

    public static Stage window;
    public static Scene scene;
    public static LessonModel lessonModel;
    public static StudentModel studentModel;
    public static QuizzesModel quizzesModel;
    public static TestsModel testsModel;
    public static HashMap<Integer, QuestionGenerator> questionGenerator;
    public static HashMap<String, Integer> numQuestionsPerQuiz;
    public static HashMap<String, Integer> numQuestionsPerTest;
    public static String username;
    public static boolean takingExam = false; //if true, prompt the user with an alert asking when they click to leave the test
    public static boolean isLoggedIn = false; //used to prevent writing to XML file when only on login page (else errors will occur)
    public static boolean useAnimations = true; //used because we animate the login page, and we need a default state (because the variable is before the student gets initialized)
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
        numQuestionsPerQuiz = new HashMap<>();
        testsModel = new TestsModel();
        numQuestionsPerTest = new HashMap<>();
        populateQuestionGenerator();
        Configure.values(); //apply any custom settings to the program
        Login.Page();
        primaryStage.show();
    }

    private void populateQuestionGenerator() {
        questionGenerator = new HashMap<>();
        questionGenerator.put(0, new CommentQuestions());
        questionGenerator.put(1, new DataTypeQuestions());
        questionGenerator.put(2, new OperatorQuestions());
        questionGenerator.put(3, new ObjectComparisonQuestions());
        questionGenerator.put(4, new VariableQuestions());
        questionGenerator.put(5, new EscapeSequenceQuestions());
        questionGenerator.put(6, new MethodQuestions());
        questionGenerator.put(7, new InputOutputQuestions());
        questionGenerator.put(8, new ArraysQuestions());
        questionGenerator.put(9, new ControlStatementQuestions());
        questionGenerator.put(10, new InterfacesQuestions());
        questionGenerator.put(11, new MiscOOPQuestions());
//        questionGenerator.put(8, new ExceptionsQuestions());
//        questionGenerator.put(9, new PackagesQuestions());

    }

    public void closeProgram() {
        boolean close = true;
        if (takingExam) {
            String title = "Exit Exam";
            String content = "Are you sure you want to exit? All exam progress will be lost!";
            close = UIComponents.confirmMessage(title, content);
        }
        if (isLoggedIn) Main.studentModel.getStudent().writePageProgress();
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

//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Hello World!");
//        TextFlow textFlow = new TextFlow();
//
//        Text correctPart = new Text("int lolol haha");
//        Text incorrectPart = new Text ("u suk");
//        Text correctPart2 = new Text("Emily suks too");
//
//        correctPart.setStyle("-fx-fill: #00ff00;");
//        correctPart2.setStyle("-fx-fill: #00ff00;");
//        incorrectPart.setStyle("-fx-fill: #ff0000;");
//        textFlow.setStyle("-fx-background-color: #66BB6A;");
//        textFlow.setMaxHeight(TextFlow.USE_PREF_SIZE);
//        textFlow.setMaxWidth(TextFlow.USE_PREF_SIZE);
//        textFlow.getChildren().addAll(correctPart, incorrectPart, correctPart2);
//
//        StackPane root = new StackPane();
//        root.getChildren().add(textFlow);
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();
//    }
}