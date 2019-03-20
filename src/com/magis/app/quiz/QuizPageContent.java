package com.magis.app.quiz;

import com.magis.app.Main;
import com.magis.app.models.QuizzesModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class QuizPageContent {

    private VBox pageContent;
    private int chapterIndex;
    private Grader grader;

    public QuizPageContent(int chapterIndex, Grader grader) {
        pageContent = new VBox();
        this.chapterIndex = chapterIndex;
        this.grader = grader;
    }

    public VBox getPageContent() {
        return pageContent;
    }

    public void initialize(int pageIndex) {
        pageContent.getChildren().clear();

        QuizzesModel.ChapterModel chapterModels = Main.quizzesModel.getChapter(chapterIndex + 1);
        int numPages = chapterModels.getNumQuestions() / 2 + 1;
        int numQuestions = chapterModels.getNumQuestions();

        int questionsPerPage = 2;
        //if we're on the last page
        if (pageIndex + 1 == numPages) {
            //will that page have 1 or 2 questions?
            questionsPerPage = (numQuestions % 2 == 0) ? 2 : 1;
        }

        //max 2 questions per page
        for (int i = 0; i < questionsPerPage; i++) {
            //get the current question index
            int question = pageIndex * 2 + i;
            //get the total number of answers for the question
            int numAnswers = chapterModels.getQuestion(question).getNumAnswers();

            VBox questionBox = new VBox();
            questionBox.setSpacing(15);
            questionBox.setPadding(new Insets(40,0,20,20));
            Label statement = new Label();
            statement.setWrapText(true);
            statement.setPrefWidth(700);
            //set the question statement
            statement.setText(chapterModels.getQuestion(question).getStatement());
            //add the statement for thr question to the questionBox
            questionBox.getChildren().add(statement);

            String correctAnswer = chapterModels.getQuestion(question).getCorrectAnswer();
            ArrayList<String> answers = shuffle(chapterModels.getQuestion(question).getIncorrectAnswers());
            //choose which radio button will be the correct answer;
            Random rand = new Random();
            int correctAnswerPosition = rand.nextInt(numAnswers);
            //insert the correct answer into the random position of the array of possible answers
            answers.add(correctAnswerPosition, correctAnswer);

            ToggleGroup toggleGroup = new ToggleGroup();

            for (int j = 0; j < numAnswers; j++) {
                ToggleButton toggleButton = new RadioButton();
                toggleButton.setUserData(answers.get(j));
                toggleButton.setText(answers.get(j));
                toggleButton.setToggleGroup(toggleGroup);
                questionBox.getChildren().add(toggleButton);
            }

            toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
                grader.addStudentAnswer(question, newVal.getUserData().toString());
            });
            pageContent.getChildren().add(questionBox);
        }
    }

    private ArrayList<String> shuffle(ArrayList<String> text) {
        int n = text.size();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int change = i + rand.nextInt(n - i);
            swap(text, i, change);
        }
        return text;
    }

    private static void swap (ArrayList<String> text, int i, int change) {
        String temp = text.get(i);
        text.set(i, text.get(change));
        text.set(change, temp);
    }
}
