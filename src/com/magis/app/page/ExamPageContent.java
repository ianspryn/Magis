package com.magis.app.page;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.magis.app.Main;
import com.magis.app.models.ExamsModel;
import com.magis.app.test.Grader;
import com.magis.app.test.questions.generator.QuestionGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class ExamPageContent extends PageContent {

    protected ExamsModel.ChapterModel exam;
    protected int chapterIndex;
    protected int numAvailableBankQuestions;
    protected int numQuestions;
    protected QuestionGenerator questionGenerator;
    protected Grader grader;
    protected HashMap<Integer, ToggleGroup> toggleGroups;
    protected HashMap<Integer, ArrayList<JFXCheckBox>> checkboxGroups;
    protected ArrayList<Integer> usedBankQuestions;
    protected ArrayList<String> usedGeneratorQuestions;
    protected ArrayList<VBox> pageContents;

    public ExamPageContent(int chapterIndex, int numQuestions, ExamsModel.ChapterModel exam) {
        this.chapterIndex = chapterIndex;
        this.numQuestions = numQuestions;
        this.exam = exam;
        numAvailableBankQuestions = exam.getNumAvailableQuestions();
        questionGenerator = Main.questionGenerator.getOrDefault(chapterIndex, null);
        grader = new Grader(numQuestions);
        toggleGroups = new HashMap<>();
        checkboxGroups = new HashMap<>();
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();
        pageContents = new ArrayList<>();
    }

    @Override
    void update(int pageIndex) {
        setScrollPaneContent(pageContents.get(pageIndex));
    }

    @Override
    void buildPage(int pageIndex) {

        Random rand = new Random();
        String generatedQuestion;
        VBox pageContent = new VBox();
        int maxQuestionsPerPage = 2;

        //keep under the max number of questions per test, and also keep under the max number of questions per page
        for (int i = 0, questionIndex = pageIndex * maxQuestionsPerPage + i; i < maxQuestionsPerPage && questionIndex < numQuestions; ++i, questionIndex = pageIndex * 2 + i) {
            ArrayList<String> answers = new ArrayList<>();
            ArrayList<String> correctAnswers = new ArrayList<>();
            VBox questionBox = new VBox();
            questionBox.setSpacing(15);
            questionBox.setPadding(new Insets(40,0,20,20));
            Label statement = new Label();
            statement.setWrapText(true);
            statement.setPrefWidth(700);

            //decide if the question is pulled from a bank (0) or generated (1)
            int typeOfQuestion;
            if (numAvailableBankQuestions > usedBankQuestions.size() && questionGenerator != null) { //if we have available bank questions and there exists a question generator
                typeOfQuestion = rand.nextInt(2); //0 or 1
            } else if (numAvailableBankQuestions > usedBankQuestions.size() && questionGenerator == null) { //if we don't have a question generator but do have bank questions
                typeOfQuestion = 0;
            } else if (numAvailableBankQuestions <= usedBankQuestions.size() && questionGenerator != null) { //if we do have a question generator but don't have bank questions
                typeOfQuestion = 1;
            } else { //if we don't have either
                return;
            }

            switch(typeOfQuestion) {
                case 0:
                    //grab a random question from the question bank that hasn't been used before
                    int question;
                    do question = rand.nextInt(numAvailableBankQuestions);
                    while (usedBankQuestions.contains(question));
                    usedBankQuestions.add(question); //add the question to the used bank of questions
                    //set the question statement
                    statement.setText(exam.getQuestion(question).getStatement());
                    //add the statement for the question to the questionBox
                    questionBox.getChildren().add(statement);

                    //get all of the correct answers (there may be 1 or more correct answers)
                    correctAnswers.addAll(exam.getQuestion(question).getCorrectAnswers());
                    ///add the incorrect answers and the correct answer to the ArrayList of possible answers
                    answers.addAll(exam.getQuestion(question).getIncorrectAnswers());
                    answers.addAll(correctAnswers);
                    //shuffle the order
                    Collections.shuffle(answers);
                    //add the correct answer to the grader for future grading
                    grader.addCorrectAnswer(questionIndex, correctAnswers);
                    break;
                case 1:
                    questionGenerator.initialize();
                    do generatedQuestion = questionGenerator.getQuestion();
                    while (usedGeneratorQuestions.contains(generatedQuestion));
                    usedGeneratorQuestions.add(generatedQuestion);

                    statement.setText(generatedQuestion);
                    questionBox.getChildren().add(statement);
                    correctAnswers.add(questionGenerator.getCorrectAnswer());
                    //add the correct answer to the grader for future grading
                    grader.addCorrectAnswer(questionIndex, correctAnswers);
                    answers = questionGenerator.getAnswers();
                    break;
            }


             /*
            Add each possible answer to a radio button or checkbox
             */
            if (correctAnswers.size() == 1) {
                ToggleGroup toggleGroup = new ToggleGroup();
                toggleGroups.put(questionIndex, toggleGroup);
                for (String answer : answers) {
                    JFXRadioButton radioButton = new JFXRadioButton();
                    radioButton.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                    radioButton.setId(Integer.toString(questionIndex));
                    radioButton.getStyleClass().addAll("exam-radio-button", "jfx-radio-button");
                    radioButton.setUserData(answer);
                    radioButton.setText(answer);
                    radioButton.setToggleGroup(toggleGroup);
                    questionBox.getChildren().add(radioButton);
                }
                //every time the student clicks a radio button, update the grader with the new answer the student selected
                int index = questionIndex;
                toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> grader.addStudentAnswer(index, newVal.getUserData().toString()));
            } else {
                ArrayList<JFXCheckBox> checkBoxes = new ArrayList<>();
                checkboxGroups.put(questionIndex, checkBoxes);
                for (String answer : answers) {
                    JFXCheckBox checkboxButton = new JFXCheckBox();
                    checkboxButton.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                    checkboxButton.setId(Integer.toString(questionIndex));
                    checkboxButton.getStyleClass().add("test-checkbox-button");
                    checkboxButton.setUserData(answer);
                    checkboxButton.setText(answer);
                    questionBox.getChildren().add(checkboxButton);
                    checkBoxes.add(checkboxButton);
                    //every time the student clicks a radio button, update the grader with the new answer the student selected
                    int index = questionIndex;
                    checkboxButton.selectedProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal) grader.addStudentAnswer(index, checkboxButton.getUserData().toString());
                        else grader.removeStudentAnswer(index, checkboxButton.getUserData().toString());
                    });
                }
            }
            pageContent.getChildren().add(questionBox);
        }
        pageContents.add(pageContent);
    }

    /**
     * Disable input of all radio buttons and checkboxes used for the exam so that the student cannot modify the exam after submitting it
     */
    protected void disableInput() {
        for (Map.Entry<Integer, ToggleGroup> toggleGroup : toggleGroups.entrySet()) {
            toggleGroup.getValue().getToggles().stream().map((toggle) -> (ToggleButton) toggle).forEach((button) -> button.setDisable(true));
        }
        for (Map.Entry<Integer, ArrayList<JFXCheckBox>> checkboxGroup : checkboxGroups.entrySet()) {
            ArrayList<JFXCheckBox> checkBoxes = checkboxGroup.getValue();
            for (JFXCheckBox checkBox : checkBoxes) {
                checkBox.setDisable(true);
            }
        }
    }

    /**
     * Colorize each exam question to indicate if the student answered the question correctly or not
     */
    protected void colorize() {
        for (int i = 0; i < numQuestions; i++) {
            int questionIndex = i;
            if (grader.getNumCorrectAnswer(i) == 1) { //then it's radio buttons with only 1 correct answer
                toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton)toggle).forEach((button) -> {
                    //highlight the correct answer as green
                    if (grader.contains(questionIndex, button.getText())) {
                        button.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a;");
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    if (!grader.contains(questionIndex, button.getText()) && button.isSelected()) {
                        button.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336;");
                    }
                });
            } else { //then it's checkboxes with 1 or more correct answers
                ArrayList<JFXCheckBox> checkBoxes = checkboxGroups.get(i);
                for (JFXCheckBox checkBox : checkBoxes) {
                    //highlight the correct answer as green
                    if (grader.contains(questionIndex, checkBox.getText())) {
                        checkBox.setCheckedColor(Color.valueOf("#00C853")); //Green A700
                        checkBox.setUnCheckedColor(Color.valueOf("#00C853")); //Green A700
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    if (!grader.contains(questionIndex, checkBox.getText()) && checkBox.isSelected()) {
                        checkBox.setCheckedColor(Color.valueOf("#FF1744")); //Red A400
                    }
                }

            }
        }
    }

}
