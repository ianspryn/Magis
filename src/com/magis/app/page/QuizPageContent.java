package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.UI.PageContentContainer;
import com.magis.app.models.QuizzesModel;
import com.magis.app.test.Grader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizPageContent extends PageContent {

    public ArrayList<PageContentContainer> pageContentContainers;
    private Grader grader;
    private QuizzesModel.ChapterModel quiz;
    private ArrayList<ToggleGroup> toggleGroups;
    private ArrayList<Integer> usedBankQuestions;
    private ArrayList<String> usedGeneratorQuestions;
    private VBox pageContent;
    private int chapterIndex;
    private int numAvailableBankQuestions;
    private int numQuestions;

    public QuizPageContent(int chapterIndex, int numQuestions, Grader grader, ArrayList<Integer> usedBankQuestions, ArrayList<String> usedGeneratorQuestions) {
        pageContentContainers = new ArrayList<>();
        this.grader = grader;

        String chapterName = Main.lessonModel.getChapter(chapterIndex).getTitle();
        quiz = Main.quizzesModel.getChapter(chapterName);

        toggleGroups = new ArrayList<>();
        this.usedBankQuestions = usedBankQuestions;
        this.usedGeneratorQuestions = usedGeneratorQuestions;
        pageContent = new VBox();
        this.chapterIndex = chapterIndex;
        numAvailableBankQuestions = quiz.getNumAvailableQuestions();
        this.numQuestions = numQuestions;
    }

    private void initialize(int pageIndex) {
        Random rand = new Random();
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

            //decide if the qusetion is pulled from a bank (0) or generated (1)
            int typeOfQuestion = 1; //default
            //if we're not out of bank questions, then randomly choose if the question should be from the bank or generated
            if (numAvailableBankQuestions > usedBankQuestions.size()) {
                typeOfQuestion = rand.nextInt(2); //0 or 1
            }

            switch(typeOfQuestion) {
                case 0:
                    //grab a random question from the question bank that hasn't been used before
                    int question;
                    do {
                        question = rand.nextInt(numAvailableBankQuestions);
                    } while (usedBankQuestions.contains(question));
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
                    //TODO FINISH
                    break;
            }

        }
    }

    @Override
    void update(int pageIndex) {

    }

    @Override
    void buildPage(int index) {

    }
}
