package com.magis.app.test.quiz;

import com.magis.app.Main;
import com.magis.app.models.QuizzesModel;
import com.magis.app.test.Grader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Random;

public class QuizPageContent {

    private VBox pageContent;
    private int chapterIndex;
    private Grader grader;
    private QuizzesModel.ChapterModel chapterModel;
    private int numPages;
    private int numQuestions;
    private ArrayList<ToggleGroup> toggleGroups;

    public QuizPageContent(int chapterIndex, Grader grader) {
        pageContent = new VBox();
        this.chapterIndex = chapterIndex;
        this.grader = grader;
        chapterModel = Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle());
        numPages = chapterModel.getNumQuestions() / 2 + 1;
        numQuestions = chapterModel.getNumQuestions();
        toggleGroups = new ArrayList<>();
        Main.lessonModel.getChapter(chapterIndex).getTitle();
    }

    public VBox getPageContent() {
        return pageContent;
    }

    public void initialize(int pageIndex) {
        pageContent.getChildren().clear();

        Random rand = new Random();

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
            int numAnswers = chapterModel.getQuestion(question).getNumAnswers();

            VBox questionBox = new VBox();
            questionBox.setSpacing(15);
            questionBox.setPadding(new Insets(40,0,20,20));
            Label statement = new Label();
            statement.setWrapText(true);
            statement.setPrefWidth(700);

            //Decide if the question is to be conceptual or calculation based
            int typeOfQuestion = rand.nextInt();

            //set the question statement
            statement.setText(chapterModel.getQuestion(question).getStatement());
            //add the statement for thr question to the questionBox
            questionBox.getChildren().add(statement);

            String correctAnswer = chapterModel.getQuestion(question).getCorrectAnswer();
            ArrayList<String> answers = shuffle(chapterModel.getQuestion(question).getIncorrectAnswers());
            //choose which radio button will be the correct answer;
            int correctAnswerPosition = rand.nextInt(numAnswers);
            //insert the correct answer into the random position of the array of possible answers
            answers.add(correctAnswerPosition, correctAnswer);

            ToggleGroup toggleGroup = new ToggleGroup();

            for (int j = 0; j < numAnswers; j++) {
                RadioButton radioButton = new RadioButton();
                radioButton.setUserData(answers.get(j));
                radioButton.setText(answers.get(j));
                radioButton.setToggleGroup(toggleGroup);
                questionBox.getChildren().add(radioButton);
            }

            toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> grader.addStudentAnswer(question, newVal.getUserData().toString()));
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

    public void disableInput() {
        for (int i = 0; i < toggleGroups.size(); i++) {
            toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton)toggle).forEach((button) -> button.setDisable(true));
        }


    }

    public void colorize(Grader grader) {

        for (int i = 0; i < toggleGroups.size(); i++) {
            int index = i;
            toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton)toggle).forEach((button) -> {

                //highlight the correct answer as green
                if (button.getText().equals(grader.getCorrectAnswer(index))) {
                    button.setStyle("-fx-text-fill: #57d154;");
                }

                //if the user selected the wrong answer, highlight their answer as red
                if (!button.getText().equals(grader.getCorrectAnswer(index)) && button.isSelected()) {
                    button.setStyle("-fx-text-fill: #f44336;");
                }
            });
        }
//        int index = 0;
//        for (int i = 0; i < numQuestions; i++) {
//            if (toggleGroups.get(i).getSelectedToggle().getUserData().toString().equals(grader.getCorrectAnswer(i))) {
//                toggleGroups.get(i).getSelectedToggle()
//            }
//            ObservableList toggleButtons = toggleGroups.get(i).getToggles();
//            toggleButtons.forEach((toggleButton) -> {
//            });
//        }
    }
}
