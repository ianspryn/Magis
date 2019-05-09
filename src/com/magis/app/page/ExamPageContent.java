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
import com.magis.app.test.diff_match_patch;
import com.magis.app.test.questions.generator.QuestionGenerator;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

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
            pointsAndIndexLabel.getStyleClass().addAll("lesson-header-three-text", "text-color");
            grader.addPointLabel(pointsAndIndexLabel);
            questionBox.getChildren().add(pointsAndIndexLabel);

            //parse and add the statement for the question to the questionBox
            buildStatement(questionBox, examQuestion, chapterIndex);



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
            }
             /*
            Add each possible answer to a radio button or checkbox
             */
            //if there's only one correct answer, then use toggle buttons
            else if (examQuestion.getNumCorrectAnswers() == 1) {
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
    public static void  buildStatement(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        if (examQuestion.isWritten()) buildAsWrittenAnswer(questionBox, examQuestion, chapterIndex);
        else buildAsChoiceAnswer(questionBox, examQuestion, chapterIndex);
    }

    public static void buildAsWrittenAnswer(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        String[] splitQuestion = splitQuestion(examQuestion.getQuestion());
        //if the input field is in the middle of the question
        if (Arrays.asList(splitQuestion).contains("###")) {
            buildWithTextField(questionBox, examQuestion, chapterIndex);
        }
        //else there is no input field, and therefore we should put an input field at the end of the question
        else {
            Text text = new Text(examQuestion.getQuestion());
            text.getStyleClass().addAll("lesson-text", "text-no-color");

            HBox container = new HBox();
            container.getStyleClass().add("code-text");
            container.setMaxWidth(HBox.USE_PREF_SIZE);
            JFXTextField textField = new JFXTextField();
            examQuestion.addTextField(0, textField);
//            textField.setOnKeyPressed(e -> examQuestion.addWrittenStudentAnswer(0, textField.getText()));
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

    private static void buildWithTextFlow(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        buildWritten(questionBox, examQuestion, chapterIndex, "TEXTFLOW");

    }

    private static void buildWithTextField(VBox questionBox, ExamQuestion examQuestion, int chapterIndex) {
        buildWritten(questionBox, examQuestion, chapterIndex, "TEXTFIELD");
    }

    private static void buildWritten (VBox questionBox, ExamQuestion examQuestion, int chapterIndex, String mode) {
        String[] splitQuestion = splitQuestion(examQuestion.getQuestion());
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
                        if (mode.equals("TEXTFIELD") && questionBox.getChildren().get(questionBox.getChildren().size() - 1).equals(regularText) || questionBox.getChildren().size() == 1) {
                            codeQuestion = new TextFlow();
                            codeQuestion.setMaxWidth(TextFlow.USE_PREF_SIZE);
                            codeQuestion.setLineSpacing(8);
                            codeQuestion.getStyleClass().add("code-text");
                            questionBox.getChildren().add(codeQuestion);
                        } else {
                            codeQuestion.setMaxWidth(TextFlow.USE_PREF_SIZE);
                            codeQuestion.setLineSpacing(8);
                            codeQuestion.getStyleClass().add("code-text");
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
                    if (mode.equals("TEXTFIELD")) {
                        JFXTextField textField = new JFXTextField();
                        int finalTextFieldCounter = textFieldCounter;
                        examQuestion.addTextField(finalTextFieldCounter, textField);
//                        textField.setOnKeyPressed(e -> examQuestion.addWrittenStudentAnswer(finalTextFieldCounter, textField.getText()));
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
                    } else if (mode.equals("TEXTFLOW")) {
                        TextFlow textFlow = new TextFlow();
                        textFlow.setStyle("-fx-background-color: #00C853"); //Green A700
                        Text text = new Text(examQuestion.getCorrectAnswers().get(textFieldCounter));
                        System.out.println(examQuestion.getCorrectAnswers().get(textFieldCounter));
                        text.setStyle("-fx-fill: #004c05"); //Dark Green
                        textFlow.getChildren().add(text);
                        codeQuestion.getChildren().add(textFlow);
                        textFieldCounter++;
                    }
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
                        /*
                        if true, then this is a question on a quiz that the student will fill out
                        and it is NOT after the quiz has been submitted.
                         */
                        if (mode.equals("TEXTFIELD")) {
                            regularText = new Label(subString);
                            regularText.setMinHeight(Label.BASELINE_OFFSET_SAME_AS_HEIGHT); //force the label's height to match that of the text it
                            regularText.setWrapText(true);
                            regularText.setPrefWidth(700);
                            regularText.getStyleClass().addAll("lesson-text", "text-no-color");
                            questionBox.getChildren().add(regularText);
                        }
                    }
            }
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
        int questionIndex = 0;
        int insertionTracker = 0;
        for (ExamQuestion examQuestion : grader.getQuestions()) {
            int questionIndexFinal = questionIndex;
            if (examQuestion.isWritten()) {
                VBox questionBox = writtenQuestionBoxes.get(questionIndex);
                diff_match_patch dmp = new diff_match_patch();
                for (int answerIndex = 0; answerIndex < examQuestion.getNumCorrectAnswers(); answerIndex++) {
                    HBox answerContainer = new HBox();
                    answerContainer.setMaxHeight(TextFlow.USE_PREF_SIZE);
                    answerContainer.setMaxWidth(TextFlow.USE_PREF_SIZE);
                    int numChildren = questionBox.getChildren().size();
                    /*
                    For the questions that have multiple fill-in-the-blanks
                    Find the first (or next) textField and replace it with a TextFlow
                     */
                    for (int i = 0; i < numChildren; i++) {
                        Node node = questionBox.getChildren().get(i);
                        if (node instanceof TextFlow) {
                            TextFlow textFlow = (TextFlow) node;
                            int numTextParts = textFlow.getChildren().size();
                            for (int i2 = 0; i2 < numTextParts; i2++) {
                                Node node2 = textFlow.getChildren().get(i2);
                                if (node2 instanceof  JFXTextField) {
                                    textFlow.getChildren().remove(i2);
                                    textFlow.getChildren().add(i2, answerContainer);
                                    break;
                                }
                            }
                            break;
                        }
                        //for the questions with a single fill-in-the-blank at the end
                        if (node instanceof HBox) {
                            HBox hBox = (HBox) node;
                            int numHBoxParts = hBox.getChildren().size();
                            for (int i2 = 0; i2 < numHBoxParts; i2++) {
                                Node node2 = hBox.getChildren().get(i2);
                                if (node2 instanceof JFXTextField) {
                                    hBox.getChildren().remove(i2);
                                    hBox.getChildren().add(i2, answerContainer);
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    String correctAnswer = examQuestion.getCorrectAnswers().get(answerIndex);
                    String studentAnswer = examQuestion.getWrittenStudentAnswer(answerIndex);
                    LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(correctAnswer, studentAnswer);
                    System.out.println(diff);
                    for (int i = 0; i < diff.size(); i++) {
                        diff_match_patch.Diff diffPart = diff.get(i);
                        String type = diffPart.operation.toString();
                        TextFlow textFlow = new TextFlow();
                        //Constrain it to the size of the text inside of it
                        textFlow.setMaxHeight(TextFlow.USE_PREF_SIZE);
                        textFlow.setMaxWidth(TextFlow.USE_PREF_SIZE);
                        Text text = new Text();
                        textFlow.getChildren().add(text);
                        answerContainer.getChildren().add(textFlow);
                        switch (type) {
                            case "EQUAL":
                                text.setText(diffPart.text);
                                textFlow.setStyle("-fx-background-color: #00C853"); //Green A700
                                text.setStyle("-fx-fill: #004c05"); //Dark Green
                                break;
                            case "INSERT":
                                //let the extra white space slide, but indicate it's unnecessary
                                text.setText(diffPart.text);
                                if (Pattern.matches((" +"), diffPart.text)) {
                                    textFlow.setStyle("-fx-background-color: #00E676"); //Green A400
                                    text.setStyle("-fx-fill: #004c05"); //Dark Green
                                } else {
                                    textFlow.setStyle("-fx-background-color: #EF9A9A"); //Red 200
                                    text.setStyle("-fx-fill: #D50000"); //Red A700
                                }
                                break;
                            case "DELETE":
                                //ignore extra whitespace
                                if (i < diff.size() - 1 && diff.get(i + 1).operation.toString().equals("INSERT")) continue;
                                if (Pattern.matches((" +"), diffPart.text)) continue;
                                text.setText(" ");
                                textFlow.setStyle("-fx-background-color: #80DEEA"); //Cyan 200
                                text.setStyle("-fx-fill: #00B8D4"); //Cyan A700
                                break;
                        }
                    }
                    answerContainer.setMaxWidth(HBox.USE_PREF_SIZE);
                }
                /*
                Create a duplicate "question" to show the student the correct answer
                and allow easier compare-and-contrast
                 */
                questionBox = new VBox();
                questionBox.setSpacing(15);
                questionBox.setPadding(new Insets(0, 0, 20, 20));
                int page = questionIndex / NUM_QUESTIONS_PER_PAGE;
                int questionOnPage = (questionIndex + insertionTracker) % NUM_QUESTIONS_PER_PAGE;


                if (Arrays.asList(splitQuestion(examQuestion.getQuestion())).contains("###")) {
                    buildWithTextFlow(questionBox, examQuestion, chapterIndex);
                }
                //else it's a fill-in-the-blank at the end and we are guaranteed it's only one fill-in-the-blank
                else {
                    HBox container = new HBox();
                    container.getStyleClass().add("code-text");
                    container.setMaxWidth(HBox.USE_PREF_SIZE);

                    TextFlow textFlow = new TextFlow();
                    textFlow.setStyle("-fx-background-color: #00C853"); //Green A700
                    Text text = new Text(examQuestion.getCorrectAnswers().get(0));
                    text.setStyle("-fx-fill: #004c05"); //Dark Green
                    textFlow.getChildren().add(text);
                    //Constrain it to the size of the text inside of it
                    textFlow.setMaxHeight(TextFlow.USE_PREF_SIZE);
                    textFlow.setMaxWidth(TextFlow.USE_PREF_SIZE);
                    container.getChildren().add(textFlow);
                    questionBox.getChildren().add(container);
                }

                VBox pageContent = new VBox();

                Label correctAnswerText = new Label("Correct Answer");
                correctAnswerText.setPadding(new Insets(0,0,0,20));
                correctAnswerText.getStyleClass().add("lesson-header-three-text");
                correctAnswerText.setStyle("-fx-text-fill: #00C853;");

                /*
                Since we add new boxes to pageContent, that pushes everything down
                So we need to keep track of that so we can insert the correct answers
                at the right position
                 */
                insertionTracker++;
                pageContent.getChildren().addAll(correctAnswerText, questionBox);

                /*
                page + 1 because we added "Results" page at the beginning
                questionOnPage + 1 because we want to show the correct answer right after the original answer
                 */
                pageContents.get(page + 1).getChildren().add(questionOnPage + 1, pageContent);
            }
            else if (examQuestion.getNumCorrectAnswers() == 1) { //then it's radio buttons with only 1 correct answer
                toggleGroups.get(questionIndex).getToggles().stream().map((toggle) -> (ToggleButton) toggle).forEach((button) -> {
                    //highlight the correct answer as green
                    if (grader.isCorrect(questionIndexFinal, button.getText())) {
                        button.setStyle("-fx-text-fill: #00cd0a; -jfx-selected-color: #00cd0a; -jfx-unselected-color: #00cd0a;");
                        button.setUnderline(true);
                    }

                    //if the user selected the wrong answer, highlight their answer as red
                    if (!grader.isCorrect(questionIndexFinal, button.getText()) && button.isSelected()) {
                        button.setStyle("-fx-text-fill: #f44336; -jfx-selected-color: #f44336; -jfx-unselected-color: #f44336;");
                    }
                });
            } else { //then it's checkboxes with more than 1 correct answer
                ArrayList<JFXCheckBox> checkBoxes = checkboxGroups.get(questionIndex);
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
//                    checkBox.setSelected(!checkBox.isSelected());
//                    checkBox.setSelected(!checkBox.isSelected());
                }

            }
            questionIndex++;
            if (questionIndex % NUM_QUESTIONS_PER_PAGE == 0) {
                //reset, because we're on a new page
                insertionTracker = 0;
            }
        }
    }

    public ExamSaver getExamSaver() {
        return examSaver;
    }
}
