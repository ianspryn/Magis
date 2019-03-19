package com.magis.app.quiz;

import com.magis.app.Main;
import com.magis.app.models.QuizzesModel;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Random;

public class QuizPageContent {

    private VBox pageContent;
    private int chapterIndex;

    public QuizPageContent(int chapterIndex) {
        pageContent = new VBox();
        this.chapterIndex = chapterIndex;
    }

    public void initialize() {
        update(0);
    }

    private void update(int pageIndex) {
        pageContent.getChildren().clear();

        QuizzesModel.ChapterModel chapterModels = Main.quizzesModel.getChapter(chapterIndex + 1);
        int numPages = chapterModels.getNumQuestions() % 2 + 1;

        VBox questionsList = new VBox();
        //2 questions per page
        for (int i = 0; i < 2; i++) {
            Label statement = new Label();
            statement.setText(chapterModels.getQuestion(pageIndex * 2 + i).getStatement());

            RadioButton radioButton1 = new RadioButton();
            Label answer1 = new Label();

            ArrayList<String> incorrecAnswers = chapterModels.getQuestion(pageIndex * 2 + i).getIncorrectAnswers();

            //choose which radio button will be the correct answer;
            Random rand = new Random();
            ArrayList<Integer> used = new ArrayList<>();
            int n = -1;
            while (!used.contains(n)) {
                n = rand.nextInt(chapterModels.getQuestion(pageIndex * 2 + i).getNumAnswers());
                used.add(n);
            }

        }
    }
}
