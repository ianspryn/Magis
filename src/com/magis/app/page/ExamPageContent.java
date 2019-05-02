package com.magis.app.page;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
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
    protected String points;
    protected String statement;
    protected ExamQuestion examQuestion;
    protected VBox questionBox;
    protected ArrayList<String> correctAnswers;
    protected ArrayList<String> answers;
    protected int questionIndex;
    protected String generatedQuestion;
    protected int numGeneratedQuestions;

    public ExamPageContent(int chapterIndex, int numQuestions, ExamsModel.ChapterModel exam) {
        this.chapterIndex = chapterIndex;
        this.numQuestions = numQuestions;
        this.exam = exam;
        numAvailableBankQuestions = exam.getNumAvailableQuestions();
        questionGenerator = Main.questionGenerator.getOrDefault(chapterIndex, null);
        grader = new Grader();
        toggleGroups = new HashMap<>();
        checkboxGroups = new HashMap<>();
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();
        pageContents = new ArrayList<>();
        examSaver = new ExamSaver(chapterIndex);
        numGeneratedQuestions = 0;
    }

    @Override
    void update(int pageIndex) {
        setScrollPaneContent(pageContents.get(pageIndex));
    }

    @Override
    boolean buildPage(int pageIndex) {

        rand = new Random();
        VBox pageContent = new VBox();
        pageContents.add(pageContent);

        //keep under the max number of questions per exam, and also keep under the max number of questions per page
        for (int i = 0, questionIndex = pageIndex * NUM_QUESTIONS_PER_PAGE + i; i < NUM_QUESTIONS_PER_PAGE && questionIndex < numQuestions; ++i, questionIndex = pageIndex * 2 + i) {
            this.questionIndex = questionIndex; //because we need to access this variable in other classes (namely the buildQuestion() method)
            /*
            We keep a local copy of examQuestion because if we don't, the toggle buttons and checkboxes
            will always refer to the last ExamQuestion created when updating their state (when student selects an answer, etc)
            But, we also need a protected ExamQuestion class for QuizPageContent or TestPageContent to refer to.
             */
            ExamQuestion examQuestion = new ExamQuestion();
            this.examQuestion = examQuestion;
            questionBox = new VBox();
            questionBox.setSpacing(15);
            questionBox.setPadding(new Insets(40, 0, 20, 20));


            //if we run out of questions to use, stop
            if (!buildQuestion()) {
                numQuestions = questionIndex;
                return false;
            }

            //add the number of points to the question
            Label pointsLabel = new Label(points);
            pointsLabel.setPadding(new Insets(0, 0, -10, 0));
            pointsLabel.getStyleClass().addAll("lesson-text-small", "text-color");
            grader.addPointLabel(pointsLabel);
            questionBox.getChildren().add(pointsLabel);

            //parse and add the statement for the question to the questionBox
            buildStatement(questionBox);

             /*
            Add each possible answer to a radio button or checkbox
             */
            //if there's only one correct answer, then use toggle buttons
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
                    examQuestion.addStudentAnswer(newVal.getUserData().toString());
                    if (oldVal != null) examQuestion.removeStudentAnswer(oldVal.getUserData().toString());
                });
            } else { //if there's more than one correct answer, then use check boxes
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
                    //every time the student clicks a radio button, update the examQuestion with the new answer the student selected
                    checkboxButton.selectedProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal) examQuestion.addStudentAnswer(checkboxButton.getUserData().toString());
                        else examQuestion.removeStudentAnswer(checkboxButton.getUserData().toString());
                    });
                }
            }
            pageContent.getChildren().add(questionBox);
            examSaver.add(examQuestion);
            grader.addQuestion(examQuestion);
        }
        return true; //success
    }

    public void buildStatement(VBox questionBox) {
        String delimiter = "(?<=```)|(?=```)|(?<=###)|(?=###)";
        String[] splitStrings = statement.split(delimiter);
        Stack<String> stack = new Stack<>();
        for (String subString : splitStrings) {
            Label label = new Label();
            label.setWrapText(true);
            label.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it
            switch (subString) {
                case "```": //beginning of code segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("```")) stack.pop();
                    else
                        System.err.println("Non-balanced text formatting of type [```] for the quiz \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" with the question of \"" + examQuestion.getQuestion() + "\"");
                    break;
                case "###": //beginning of code output segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("###")) stack.pop();
                    else
                        System.err.println("Non-balanced text formatting of type [```] for the quiz \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" with the question of \"" + examQuestion.getQuestion() + "\"");
                    break;
                default: //text only
                    String formatType = stack.isEmpty() ? "" : stack.peek();
                    if (subString.charAt(0) == '\n')
                        subString = subString.substring(1); //get rid of the first new line if it exists because of the way we split the strings
                    label.setText(subString);
                    switch (formatType) {
                        case "```": //we're in code segment
                            label.getStyleClass().add("code-text");
                            break;
                        case "###": //we're in a code output segment
                            label.getStyleClass().addAll("code-output-text", "drop-shadow");
                            break;
                        default: //we are not in any kind of formatting segment
                            label.setPrefWidth(700);
                            label.getStyleClass().addAll("lesson-text", "lesson-text-color");
                    }
            }
            questionBox.getChildren().add(label);
        }
    }

    protected abstract boolean buildQuestion();

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
                toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton) toggle).forEach((button) -> {
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
