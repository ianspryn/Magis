package com.magis.app.page;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.magis.app.Configure;
import com.magis.app.Main;
import com.magis.app.models.ExamsModel;
import com.magis.app.test.ExamQuestion;
import com.magis.app.test.ExamSaver;
import com.magis.app.test.Grader;
import com.magis.app.test.questions.generator.QuestionGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

import static com.magis.app.Configure.NUM_QUESTIONS_PER_PAGE;

public abstract class ExamPageContent extends PageContent {

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
    protected ExamSaver examSaver;

    protected Random rand;
    protected Label statement;
    protected ExamQuestion examQuestion;
    protected VBox questionBox;
    protected ArrayList<String> correctAnswers;
    protected ArrayList<String> answers;
    protected int questionIndex;
    protected String generatedQuestion;

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
        examSaver = new ExamSaver(chapterIndex);
    }

    @Override
    void update(int pageIndex) {
        setScrollPaneContent(pageContents.get(pageIndex));
    }

    @Override
    void buildPage(int pageIndex) {

        rand = new Random();
        VBox pageContent = new VBox();

        //keep under the max number of questions per test, and also keep under the max number of questions per page
        for (int i = 0, questionIndex = pageIndex * NUM_QUESTIONS_PER_PAGE + i; i < NUM_QUESTIONS_PER_PAGE && questionIndex < numQuestions; ++i, questionIndex = pageIndex * 2 + i) {
            this.questionIndex = questionIndex;
            ExamQuestion examQuestion = new ExamQuestion(); //used to save the question (for viewing this exam at a later date after submitting it)
            this.examQuestion = examQuestion;
//            answers = new ArrayList<>();
//            correctAnswers = new ArrayList<>();
            questionBox = new VBox();
            questionBox.setSpacing(15);
            questionBox.setPadding(new Insets(40,0,20,20));
            statement = new Label();
            statement.setWrapText(true);
            statement.setPrefWidth(700);

            buildQuestions();

             /*
            Add each possible answer to a radio button or checkbox
             */
            if (examQuestion.getNumCorrectAnswers() == 1) {
                ToggleGroup toggleGroup = new ToggleGroup();
                toggleGroups.put(questionIndex, toggleGroup);
                for (String answer : examQuestion.getAnswers()) {
                    JFXRadioButton radioButton = new JFXRadioButton();
                    radioButton.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                    radioButton.setId(Integer.toString(questionIndex));
                    radioButton.getStyleClass().addAll("exam-radio-button", "jfx-radio-button");
                    radioButton.setUserData(answer);
                    radioButton.setText(answer);
                    radioButton.setToggleGroup(toggleGroup);
                    questionBox.getChildren().add(radioButton);
                }
                //every time the student clicks a radio button, update the grader and exam saver with the new answer the student selected
                toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
//                    grader.addStudentAnswer(index, newVal.getUserData().toString());
                    examQuestion.addStudentAnswer(newVal.getUserData().toString());
                    if (oldVal != null) {
//                        grader.removeStudentAnswer(index, oldVal.getUserData().toString());
                        examQuestion.removeStudentAnswer(oldVal.getUserData().toString());
                    }
                });
            } else {
                ArrayList<JFXCheckBox> checkBoxes = new ArrayList<>();
                checkboxGroups.put(questionIndex, checkBoxes);
                for (String answer : examQuestion.getAnswers()) {
                    JFXCheckBox checkboxButton = new JFXCheckBox();
                    checkboxButton.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                    checkboxButton.setId(Integer.toString(questionIndex));
                    checkboxButton.getStyleClass().add("test-checkbox-button");
                    checkboxButton.setUserData(answer);
                    checkboxButton.setText(answer);
                    questionBox.getChildren().add(checkboxButton);
                    checkBoxes.add(checkboxButton);
                    //every time the student clicks a radio button, update the grader with the new answer the student selected
                    checkboxButton.selectedProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal) {
                            examQuestion.addStudentAnswer(checkboxButton.getUserData().toString());
//                            grader.addStudentAnswer(index, checkboxButton.getUserData().toString());
                        } else {
                            examQuestion.removeStudentAnswer(checkboxButton.getUserData().toString());
//                            grader.removeStudentAnswer(index, checkboxButton.getUserData().toString());
                        }
                    });
                }
            }
            pageContent.getChildren().add(questionBox);
            examSaver.add(examQuestion);
            grader.addQuestion(examQuestion);
        }
        pageContents.add(pageContent);
    }

    protected abstract void buildQuestions();

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
            if (grader.getNumCorrectAnswers(i) == 1) { //then it's radio buttons with only 1 correct answer
                toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton)toggle).forEach((button) -> {
                    //highlight the correct answer as green
                    if (grader.isCorrect(questionIndex, button.getText())) {
                        button.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a;");
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    if (!grader.isCorrect(questionIndex, button.getText()) && button.isSelected()) {
                        button.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336;");
                    }
                });
            } else { //then it's checkboxes with 1 or more correct answers
                ArrayList<JFXCheckBox> checkBoxes = checkboxGroups.get(i);
                for (JFXCheckBox checkBox : checkBoxes) {
                    //highlight the correct answer as green
                    if (grader.isCorrect(questionIndex, checkBox.getText())) {
                        checkBox.setCheckedColor(Color.valueOf("#00C853")); //Green A700
                        checkBox.setUnCheckedColor(Color.valueOf("#00C853")); //Green A700
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    if (!grader.isCorrect(questionIndex, checkBox.getText()) && checkBox.isSelected()) {
                        checkBox.setCheckedColor(Color.valueOf("#FF1744")); //Red A400
                    }
                }

            }
        }
    }

    public ExamSaver getExamSaver() {
        return examSaver;
    }
}
