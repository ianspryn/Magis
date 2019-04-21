package com.magis.app.page;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.UI.PageContentContainer;
import com.magis.app.models.QuizzesModel;
import com.magis.app.test.Grader;
import com.magis.app.test.questions.generator.QuestionGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizPageContent extends PageContent {

    private QuestionGenerator questionGenerator;
    private Grader grader;
    private QuizzesModel.ChapterModel quiz;
    private ArrayList<ToggleGroup> toggleGroups;
    private ArrayList<Integer> usedBankQuestions;
    private ArrayList<String> usedGeneratorQuestions;
    private ArrayList<VBox> pageContents;
    private int chapterIndex;
    private int numAvailableBankQuestions;
    private int numQuestions;

    public QuizPageContent(int chapterIndex) {
        questionGenerator = Main.questionGenerator.getOrDefault(chapterIndex, null);

        String chapterName = Main.lessonModel.getChapter(chapterIndex).getTitle();
        quiz = Main.quizzesModel.getChapter(chapterName);

        toggleGroups = new ArrayList<>();
        pageContents = new ArrayList<>();
        this.chapterIndex = chapterIndex;
        numAvailableBankQuestions = quiz.getNumAvailableQuestions();
    }

    void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    void setGrader(Grader grader) {
        this.grader = grader;
    }

    void setUsedBankQuestions(ArrayList<Integer> usedBankQuestions) {
        this.usedBankQuestions = usedBankQuestions;
    }

    void setUsedGeneratorQuestions(ArrayList<String> usedGeneratorQuestions) { this.usedGeneratorQuestions = usedGeneratorQuestions; }

    @Override
    void update(int pageIndex) {
        setScrollPaneContent(pageContents.get(pageIndex));
    }

    @Override
    void buildPage(int pageIndex) {

        Random rand = new Random();
        String generatedQuestion;
        VBox pageContent = new VBox();
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

            //decide if the question is pulled from a bank (0) or generated (1)
            int typeOfQuestion = 1; //default
            //if we're not out of bank questions, then randomly choose if the question should be from the bank or generated
            if (numAvailableBankQuestions > usedBankQuestions.size()) {
                typeOfQuestion = rand.nextInt(2); //0 or 1
            } else if (questionGenerator == null && numAvailableBankQuestions <= usedBankQuestions.size()) { // if we're out of bank questions and there doesn't exist a question generator, return
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
                    statement.setText(quiz.getQuestion(question).getStatement());
                    //add the statement for the question to the questionBox
                    questionBox.getChildren().add(statement);

                    //add the incorrect answers and the correct answer to the ArrayList of possible answers
                    correctAnswer = quiz.getQuestion(question).getCorrectAnswer();
                    answers = quiz.getQuestion(question).getIncorrectAnswers();
                    answers.add(correctAnswer);
                    //shuffle the order
                    Collections.shuffle(answers);
                    //add the correct answer to the grader for future grading
                    grader.addCorrectAnswer(questionIndex, correctAnswer);
                    break;
                case 1:
                    questionGenerator.initialize();
                    do generatedQuestion = questionGenerator.getQuestion();
                    while (usedGeneratorQuestions.contains(generatedQuestion));
                    usedGeneratorQuestions.add(generatedQuestion);

                    statement.setText(generatedQuestion);
                    questionBox.getChildren().add(statement);
                    correctAnswer = questionGenerator.getCorrectAnswer();
                    //add the correct answer to the grader for future grading
                    grader.addCorrectAnswer(questionIndex, correctAnswer);
                    answers = questionGenerator.getAnswers();
                    break;
            }


             /*
            Add each possible answer to a radio button
             */
            ToggleGroup toggleGroup = new ToggleGroup();
            toggleGroups.add(toggleGroup);

            for (String answer : answers) {
                JFXRadioButton radioButton = new JFXRadioButton();
                radioButton.setId(Integer.toString(questionIndex));
                radioButton.getStyleClass().addAll("test-radio-button");
                radioButton.setUserData(answer);
                radioButton.setText(answer);
                radioButton.setToggleGroup(toggleGroup);
                questionBox.getChildren().add(radioButton);
            }
            //every time the student clicks a radio button, update the grader with the new answer the student selected
            int index = questionIndex;
            toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> grader.addStudentAnswer(index, newVal.getUserData().toString()));

            pageContent.getChildren().add(questionBox);
        }
        pageContents.add(pageContent);
    }
}
