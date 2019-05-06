package com.magis.app.page;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import com.magis.app.Main;
import com.magis.app.models.ExamsModel;
import com.magis.app.test.ExamQuestion;
import com.magis.app.test.ExamSaver;
import com.magis.app.test.Grader;
import com.magis.app.test.questions.generator.QuestionGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    protected HashMap<Integer, VBox> writtenQuestionBoxes;
    protected ArrayList<Integer> usedBankQuestions;
    protected ArrayList<String> usedGeneratorQuestions;
    protected ArrayList<VBox> pageContents;
    protected ExamSaver examSaver;

    protected Random rand;
    protected String pointsAndIndex;
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
        numAvailableBankQuestions = exam != null ? exam.getNumAvailableQuestions() : 0;
        questionGenerator = Main.questionGenerator.getOrDefault(chapterIndex, null);
        grader = new Grader();
        toggleGroups = new HashMap<>();
        checkboxGroups = new HashMap<>();
        writtenQuestionBoxes = new HashMap<>();
        usedBankQuestions = new ArrayList<>();
        usedGeneratorQuestions = new ArrayList<>();
        pageContents = new ArrayList<>();
        examSaver = new ExamSaver(chapterIndex);
        numGeneratedQuestions = 0;
    }

    @Override
    void update(int pageIndex) {
        setScrollPaneContent(pageContents.get(pageIndex));
        JFXScrollPane.smoothScrolling(getScrollPane());
        getScrollPane().setVvalue(0);
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
            Label pointsAndIndexLabel = new Label(pointsAndIndex);
            pointsAndIndexLabel.setPadding(new Insets(0, 0, -10, 0));
            pointsAndIndexLabel.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it
            pointsAndIndexLabel.getStyleClass().addAll("lesson-text-small", "text-color");
            grader.addPointLabel(pointsAndIndexLabel);
            questionBox.getChildren().add(pointsAndIndexLabel);

            //parse and add the statement for the question to the questionBox
            buildStatement(questionBox, examQuestion, chapterIndex);


             /*
            Add each possible answer to a radio button or checkbox
             */
            //if there's only one correct answer, then use toggle buttons
            if (examQuestion.getNumCorrectAnswers() < 1) {
                System.err.println("Error. No correct answer was marked for the question of \"" + examQuestion.getQuestion() + "\" for the chapter \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\"");
            }
            /*
            If this is an written response, then buildStatement() above handled creating the question
            and we don't need to add any toggle buttons or checkboxes.
             */
            else if (examQuestion.isWritten()) {
                //we will need to heavily modify this box after we grade it, so save it for future use
                writtenQuestionBoxes.put(questionIndex, questionBox);
            } else if (examQuestion.getNumCorrectAnswers() == 1) {
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
                    JFXCheckBox checkBox = new JFXCheckBox();
                    checkBox.getStyleClass().add("jfx-custom-check-box");
                    checkBox.setDisableVisualFocus(true); //fix first radio button on page appear to be highlighted (not selected, just highlighted)
                    checkBox.setId(Integer.toString(questionIndex));
                    checkBox.getStyleClass().add("exam-checkbox-button");
                    checkBox.setUserData(answer);
                    checkBox.setText(answer);
                    questionBox.getChildren().add(checkBox);
                    checkBoxes.add(checkBox);
                    //every time the student clicks a radio button, update the examQuestion with the new answer the student selected
                    checkBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal) examQuestion.addStudentAnswer(checkBox.getUserData().toString());
                        else examQuestion.removeStudentAnswer(checkBox.getUserData().toString());
                    });
                }
            }
            pageContent.getChildren().add(questionBox);
            examSaver.add(examQuestion);
            grader.addQuestion(examQuestion);
        }
        return true; //success


    }

    /**
     * build the statement for thr given question
     *
     * @param questionBox  the box to add the statement to
     * @param examQuestion the class instance to pull exam data from
     * @param chapterIndex used for error output and easier debugging
     */
    public static void buildStatement(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        if (examQuestion.isWritten()) buildAsWrittenAnswer(questionBox, examQuestion, chapterIndex);
        else buildAsChoiceAnswer(questionBox, examQuestion, chapterIndex);
    }

    private static void buildAsWrittenAnswer(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        String[] splitQuestion = splitQuestion(examQuestion.getQuestion());
        //if the input field is in the middle of the question
        if (Arrays.asList(splitQuestion).contains("###")) {
            Label regularText = null;
            TextFlow codeQuestion = new TextFlow();
            Stack<String> stack = new Stack<>();
            int textFieldCounter = 0;
            for (String subString : splitQuestion) {
                switch (subString) {
                    case "```": //beginning or ending of code segment
                        if (stack.isEmpty()) {
                            /*
                            If the last element was a regular text element, then we are about another code section
                            Therefore, we need to create new TextFlow object and add it to the question box.
                            Example of when this would happen:

                            ```
                            for () {
                               ###
                            }
                            ```

                            This is regular text    //<-- this is what would be the last item added to the questionBox

                            ```                     //<-- this is where we would be right now
                            if () {
                               ###
                            }
                            ```

                            OR

                            If this is our first code segment of the question (the points and questionIndex is already in questionBox, so we check of size is 1)
                             */
                            if (questionBox.getChildren().get(questionBox.getChildren().size() - 1).equals(regularText) || questionBox.getChildren().size() == 1) {
                                codeQuestion = new TextFlow();
                                codeQuestion.setMaxWidth(TextFlow.USE_PREF_SIZE);
                                codeQuestion.setLineSpacing(8);
                                codeQuestion.getStyleClass().add("code-text");
                                //we are not in any kind of formatting segment
                                questionBox.getChildren().add(codeQuestion);
                            }
                            stack.push(subString);
                        } else if (stack.peek().equals("```")) {
                            stack.pop();
                        } else
                            System.err.println("Non-balanced text formatting of type [```] for the quiz \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" with the question of \"" + examQuestion.getQuestion() + "\"");
                        break;
                    case "###": //textField area
                        if (stack.isEmpty()) {
                            System.err.println("Error. Written segment of question is not inside a code block. [###] must be surrounded by [```]. Question in error: \"" + examQuestion.getQuestion() + "\"");
                        }
                        JFXTextField textField = new JFXTextField();
                        int finalTextFieldCounter = textFieldCounter;
                        textField.setOnKeyReleased(e -> examQuestion.addWrittenStudentAnswer(finalTextFieldCounter, textField.getText()));
                        textField.setStyle("-fx-font-family: \"monospace\";");
                        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = img.createGraphics();
                        FontMetrics fm = g2d.getFontMetrics();
                        /*
                        calculate a good fitting size for the text based on the font size and type
                        (and add an extra 1/3 so that the student doesn't catch on that the correct answer perfectly fits in the textField)
                         */
                        try {
                            textField.setMinWidth(1.5 * fm.stringWidth(examQuestion.getCorrectAnswers().get(textFieldCounter)) + (double) fm.stringWidth(examQuestion.getCorrectAnswers().get(textFieldCounter)) / 3);
                        } catch (IndexOutOfBoundsException e) {
                            throw new IndexOutOfBoundsException("\n\nError. The number of answers for this question is less than the number of fill-in-the blanks.\nThere are " + Collections.frequency(Arrays.asList(splitQuestion), "###") +
                                    " written statement segments, and yet only " + examQuestion.getNumCorrectAnswers() + " answer elements.\nAdd the remaining answers for the question:\n\n\"" + examQuestion.getQuestion() + "\"\n\n");
                        }
                        g2d.dispose();
                        codeQuestion.getChildren().add(textField);
                        textFieldCounter++;
                        break;
                    default: //text only
                        String formatType = stack.isEmpty() ? "" : stack.peek();
                        //we're in code segment
                        if ("```".equals(formatType)) {
                            Text codeText = new Text(subString);
                            codeText.getStyleClass().addAll("lesson-text", "text-no-color");
                            codeText.setStyle("-fx-font-family: \"monospace\";");
                            codeQuestion.getChildren().add(codeText);
                        //we are not in any kind of formatting segment
                        } else {
                            regularText = new Label(subString);
                            regularText.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it
                            regularText.setWrapText(true);
                            regularText.setPrefWidth(700);
                            regularText.getStyleClass().addAll("lesson-text", "text-no-color");
                            questionBox.getChildren().add(regularText);
                        }
                }
            }
            //else there is no input field, and therefore we should put an input field at the end of the question
        } else {
            Text text = new Text(examQuestion.getQuestion());
            text.getStyleClass().addAll("lesson-text", "text-no-color");

            HBox container = new HBox();
            container.getStyleClass().add("code-text");
            container.setMaxWidth(HBox.USE_PREF_SIZE);
            JFXTextField textField = new JFXTextField();
            textField.setOnKeyReleased(e -> examQuestion.addWrittenStudentAnswer(0, textField.getText()));
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            FontMetrics fm = g2d.getFontMetrics();
            /*
            calculate a good fitting size for the text based on the font size and type
            (and add an extra 1/3 so that the student doesn't catch on that the correct answer perfectly fits in the textField)
             */
            textField.setPrefWidth(1.5 * fm.stringWidth(examQuestion.getCorrectAnswers().get(0)) + (double) fm.stringWidth(examQuestion.getCorrectAnswers().get(0)) / 3);
            g2d.dispose();
            container.getChildren().add(textField);
            questionBox.getChildren().addAll(text, container);
        }
    }

    private static void buildAsChoiceAnswer(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        String[] splitStrings = splitQuestion(examQuestion.getQuestion());
        Stack<String> stack = new Stack<>();
        for (String subString : splitStrings) {
            Label label = new Label();
            label.setWrapText(true);
            label.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it
            switch (subString) {
                case "```": //beginning or ending of code segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("```")) stack.pop();
                    else
                        System.err.println("Non-balanced text formatting of type [```] for the quiz \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" with the question of \"" + examQuestion.getQuestion() + "\"");
                    break;
                case "###": //beginning of code output segment
                    if (stack.isEmpty()) stack.push(subString);
                    else if (stack.peek().equals("###")) stack.pop();
                    else
                        System.err.println("Non-balanced text formatting of type [###] for the quiz \"" + Main.lessonModel.getChapter(chapterIndex).getTitle() + "\" with the question of \"" + examQuestion.getQuestion() + "\"");
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
                            label.getStyleClass().add("lesson-text");
                    }
            }
            questionBox.getChildren().add(label);
        }
    }

    public static String[] splitQuestion(String statement) {
        return statement.split("(?<=```)|(?=```)|(?<=###)|(?=###)");
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
            if (grader.getExamQuestion(i).isWritten()) {
                //TODO: colorize logic here for fill-in-the-blank
            }
            else if (grader.getExamQuestion(i).getNumCorrectAnswers() == 1) { //then it's radio buttons with only 1 correct answer
                toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton) toggle).forEach((button) -> {
                    //highlight the correct answer as green
                    if (grader.isCorrect(questionIndex, button.getText())) {
                        button.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a; -jfx-unselected-color: #00cd0a;");
                        button.setUnderline(true);
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    if (!grader.isCorrect(questionIndex, button.getText()) && button.isSelected()) {
                        button.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336; -jfx-unselected-color: #f44336;");
                    }
                });
            } else { //then it's checkboxes with more than 1 correct answer
                ArrayList<JFXCheckBox> checkBoxes = checkboxGroups.get(i);
                for (JFXCheckBox checkBox : checkBoxes) {
                    checkBox.getStyleClass().remove("jfx-custom-check-box");
                    //highlight the correct answer as green
                    if (grader.isCorrect(questionIndex, checkBox.getText())) {
                        checkBox.setStyle("-fx-text-fill: #00cd0a;"); //Green A700
                        checkBox.setCheckedColor(Color.valueOf("#00cd0a"));
                        checkBox.setUnCheckedColor(Color.valueOf("#00cd0a"));
                        checkBox.setUnderline(true);
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    else if (!grader.isCorrect(questionIndex, checkBox.getText())) {
                        checkBox.setStyle("-fx-text-fill: #f44336;"); //Red A400
                        checkBox.setCheckedColor(Color.valueOf("#f44336"));
                    }
                    checkBox.setSelected(!checkBox.isSelected());
                    checkBox.setSelected(!checkBox.isSelected());
                }

            }
        }
    }

    public ExamSaver getExamSaver() {
        return examSaver;
    }
}
