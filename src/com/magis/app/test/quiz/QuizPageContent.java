package com.magis.app.test.quiz;

import com.magis.app.Main;
import com.magis.app.UI.TestPageContent;
import com.magis.app.models.QuizzesModel;
import com.magis.app.test.Grader;
import com.magis.app.test.questions.generator.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizPageContent {

    private VBox pageContent;
    private int chapterIndex;
    private Grader grader;
    private QuizzesModel.ChapterModel chapterModel;
    private int numPages;
    private int numBankQuestions;
    private int numQuestions;
    private ArrayList<ToggleGroup> toggleGroups;
    private ArrayList<Integer> usedBankQuestions;

    public QuizPageContent(int numQuestions, int numPages, int chapterIndex,  Grader grader, TestPageContent testPageContent, ArrayList<Integer> usedBankQuestions) {
        pageContent = new VBox();
        this.numQuestions = numQuestions;
        this.chapterIndex = chapterIndex;
        this.grader = grader;
        this.chapterModel = Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle());
        this.numPages = numPages;
        this.numBankQuestions = chapterModel.getNumAvailableQuestions();
        this.toggleGroups = new ArrayList<>();
        testPageContent.add(pageContent);
        this.usedBankQuestions = usedBankQuestions;
    }

    public void initialize(int pageIndex) {
        pageContent.getChildren().clear();
        Random rand = new Random();
        ArrayList<String> usedGeneratorQuestions = new ArrayList<>();
        String generatedQuestion = "";
        //max 2 questions per page
        for (int i = 0, questionIndex = pageIndex * 2 + i; i < 2 && questionIndex < numQuestions; ++i, questionIndex = pageIndex * 2 + i) {
            ArrayList<String> answers = new ArrayList<>();
            String correctAnswer;
            VBox questionBox = new VBox();
            questionBox.setSpacing(15);
            questionBox.setPadding(new Insets(40,0,20,20));
            Label statement = new Label();
            statement.setWrapText(true);
            statement.setPrefWidth(700);

            //Decide if the question is to be conceptual (0) or calculation-based (1)
            int typeOfQuestion = 1; //default
            //if we're not out of bank questions, then randomly choose if the question should be conceptual or calculation-based
            if (numBankQuestions > usedBankQuestions.size()) {
                 typeOfQuestion = rand.nextInt(2);
            }

            if(typeOfQuestion == 0) {
                //grab a random question from the question bank that hasn't been used before
                int question;
                do {
                    question = rand.nextInt(numBankQuestions);
                } while (usedBankQuestions.contains(question));
                usedBankQuestions.add(question);
                //set the question statement
                statement.setText(chapterModel.getQuestion(question).getStatement());
                //add the statement for the question to the questionBox
                questionBox.getChildren().add(statement);

                //add the incorrect answers and the correct answer to the ArrayList of possible answers
                correctAnswer = chapterModel.getQuestion(question).getCorrectAnswer();
                answers = chapterModel.getQuestion(question).getIncorrectAnswers();
                answers.add(correctAnswer);
                //shuffle the order
                Collections.shuffle(answers);
                //add the correct answer to the grader for future grading
                grader.addCorrectAnswer(questionIndex, correctAnswer);
            } else {
                int typeSelector;
                String chapterTitle = Main.lessonModel.getChapter(chapterIndex).getTitle();
                switch (chapterTitle) {
                    case "Comments":
                        CommentQuestions cQuestion = new CommentQuestions();
                        cQuestion.generateGeneralCommentQuestion();

                        do{
                            generatedQuestion = cQuestion.getQuestion();
                        }while(usedGeneratorQuestions.contains(generatedQuestion));
                        usedGeneratorQuestions.add(generatedQuestion);

                        statement.setText(generatedQuestion);
                        questionBox.getChildren().add(statement);
                        correctAnswer = cQuestion.getCorrectAnswer();
                        //add the correct answer to the grader for future grading
                        grader.addCorrectAnswer(questionIndex, correctAnswer);
                        answers = cQuestion.getCommentAnswers();
                        break;
                    case "Primitive Types":
                        DataTypeQuestions dtQuestion = new DataTypeQuestions();
                        dtQuestion.datatypeMatchingQuestion();

                        do{
                            generatedQuestion = dtQuestion.getQuestion();
                        }while(usedGeneratorQuestions.contains(generatedQuestion));
                        usedGeneratorQuestions.add(generatedQuestion);

                        statement.setText(generatedQuestion);
                        questionBox.getChildren().add(statement);
                        correctAnswer = dtQuestion.getCorrectAnswer();
                        //add the correct answer to the grader for future grading
                        grader.addCorrectAnswer(questionIndex, correctAnswer);
                        answers = dtQuestion.getAnswers();
                        break;
                    case "Object and Object Comparison":
                        ObjectComparisonQuestions objcQuestion = new ObjectComparisonQuestions();

                        typeSelector = rand.nextInt(2);
                        if (typeSelector == 0) {
                            objcQuestion.generateComparableQuestion();
                        } else {
                            objcQuestion.generateEqualsQuestion();
                        }


                        do{
                            generatedQuestion = objcQuestion.getQuestion();
                        }while(usedGeneratorQuestions.contains(generatedQuestion));
                        usedGeneratorQuestions.add(generatedQuestion);

                        statement.setText(generatedQuestion);
                        questionBox.getChildren().add(statement);
                        correctAnswer = objcQuestion.getCorrectAnswer();
                        //add the correct answer to the grader for future grading
                        grader.addCorrectAnswer(questionIndex, correctAnswer);
                        answers = objcQuestion.getAnswers();
                        break;
                    case "Operators":
                        OperatorQuestions opQuestion = new OperatorQuestions();

                        typeSelector = rand.nextInt(5);
                        if (typeSelector == 0) {
                            opQuestion.getIncrementalQuestion();
                        } else if (typeSelector == 1) {
                            opQuestion.getIntegerDivisionQuestion();
                        } else if (typeSelector == 2) {
                            opQuestion.getModularQuestion();
                        } else {
                            opQuestion.getSubstringQuestion();
                        }


                        do{
                            generatedQuestion = opQuestion.getQuestion();
                        }while(usedGeneratorQuestions.contains(generatedQuestion));
                        usedGeneratorQuestions.add(generatedQuestion);

                        statement.setText(generatedQuestion);
                        questionBox.getChildren().add(statement);
                        correctAnswer = opQuestion.getCorrectAnswer();
                        //add the correct answer to the grader for future grading
                        grader.addCorrectAnswer(questionIndex, correctAnswer);
                        answers = opQuestion.getAnswers();
                        break;
                    case "Methods":
                        MethodQuestions mQuestion = new MethodQuestions();

                        typeSelector = rand.nextInt(2);
                        if (typeSelector == 0) {
                            mQuestion.getMathMethodQuestion();
                        } else {
                            mQuestion.getStringMethodQuestion();
                        }


                        do{
                            generatedQuestion = mQuestion.getQuestion();
                        }while(usedGeneratorQuestions.contains(generatedQuestion));
                        usedGeneratorQuestions.add(generatedQuestion);

                        statement.setText(generatedQuestion);
                        questionBox.getChildren().add(statement);
                        correctAnswer = mQuestion.getCorrectAnswer();
                        //add the correct answer to the grader for future grading
                        grader.addCorrectAnswer(questionIndex, correctAnswer);
                        answers = mQuestion.getAnswers();
                        break;
                    case "Variables":
                        VariableQuestions vQuestion = new VariableQuestions();

                        typeSelector = rand.nextInt(2);
                        if (typeSelector == 0) {
                            vQuestion.getInstanceVariableQuestion();
                        } else {
                            vQuestion.getVariableNameQuestion();
                        }


                        do{
                            generatedQuestion = vQuestion.getQuestion();
                        }while(usedGeneratorQuestions.contains(generatedQuestion));
                        usedGeneratorQuestions.add(generatedQuestion);

                        statement.setText(generatedQuestion);
                        questionBox.getChildren().add(statement);
                        correctAnswer = vQuestion.getCorrectAnswer();
                        //add the correct answer to the grader for future grading
                        grader.addCorrectAnswer(questionIndex, correctAnswer);
                        answers = vQuestion.getAnswers();
                        break;
                }

            }

            /*
            Add each possible answer to a radio button
             */
            ToggleGroup toggleGroup = new ToggleGroup();
            toggleGroups.add(toggleGroup);

            for (int j = 0; j < answers.size(); j++) {
                RadioButton radioButton = new RadioButton();
                radioButton.setId(Integer.toString(questionIndex));
                radioButton.getStyleClass().addAll("test-radio-button");
                radioButton.setUserData(answers.get(j));
                radioButton.setText(answers.get(j));
                radioButton.setToggleGroup(toggleGroup);
                questionBox.getChildren().add(radioButton);
            }
            //every time the student clicks a radio button, update the grader with the new answer the student selected
            int index = questionIndex;
            toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> grader.addStudentAnswer(index, newVal.getUserData().toString()));

            pageContent.getChildren().add(questionBox);
        }
    }

    /**
     * Disable input of all radio buttons used for the quiz so that the student cannot modify the quiz after submitting it
     */
    public void disableInput() {
        for (int i = 0; i < toggleGroups.size(); i++) {
            toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton)toggle).forEach((button) -> button.setDisable(true));
        }
    }

    /**
     * Colorize each quiz question to indicate if the student answered the question correctly or not
     * @param grader the current grader instance being used to grade the quiz
     */
    public void colorize(Grader grader, int page) {
        for (int i = 0; i < toggleGroups.size(); i++) {
            int index = i;
            toggleGroups.get(i).getToggles().stream().map((toggle) -> (ToggleButton)toggle).forEach((button) -> {

                //highlight the correct answer as green
                if (button.getText().equals(grader.getCorrectAnswer(index + page * 2))) {
                    button.setStyle("-fx-text-fill: #00cd0a;");
                }

                //if the user selected the wrong answer, highlight their answer as red
                if (!button.getText().equals(grader.getCorrectAnswer(index + page * 2)) && button.isSelected()) {
                    button.setStyle("-fx-text-fill: #f44336;");
                }
            });
        }
    }
}
