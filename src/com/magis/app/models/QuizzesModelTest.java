package com.magis.app.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class QuizzesModelTest {

    @Test
    public void getChapterID() {
        QuizzesModel quizzesModel = new QuizzesModel();
        int chapterID = quizzesModel.getChapter(1).getChapterID();
        Assert.assertEquals(1, chapterID);
    }

    @Test
    public void getNumQuestions() {
        QuizzesModel quizzesModel = new QuizzesModel();
        int size = quizzesModel.getChapter(1).getNumQuestions();
        Assert.assertEquals(1, size);
    }

    @Test
    public void getStatement() {
        QuizzesModel quizzesModel = new QuizzesModel();
        String actualString = quizzesModel.getChapter(1).getQuestion(0).getStatement();
        Assert.assertEquals("my statement", actualString);
    }


    @Test
    public void getCorrectAnswer() {
        QuizzesModel quizzesModel = new QuizzesModel();
        String actualString = quizzesModel.getChapter(1).getQuestion(0).getCorrectAnswer();
        Assert.assertEquals("blah1", actualString);
    }

    @Test
    public void getIncorrectAnswers() {
        QuizzesModel quizzesModel = new QuizzesModel();
        ArrayList<String> expectedAnswers = new ArrayList<>();
        expectedAnswers.add("asdf1");
        expectedAnswers.add("asdf2");
        expectedAnswers.add("asdf3");
        ArrayList<String> actualAnswers = quizzesModel.getChapter(1).getQuestion(0).getIncorrectAnswers();
        Assert.assertEquals(expectedAnswers,actualAnswers);
    }
}