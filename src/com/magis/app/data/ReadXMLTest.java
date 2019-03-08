package com.magis.app.data;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReadXMLTest {

    @Test
    public void getNameTest() {
        ReadXML readXML = new ReadXML("1");
        String name = "John Doe";
        Assert.assertEquals(name, readXML.getName());
    }

    @Test
    public void getChapterImagesTest() {
        ReadXML readXML = new ReadXML("1");
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("url.com/generic/url1");
        descriptions.add("url.com/generic/url2");
        Assert.assertEquals(descriptions, readXML.getChapterImages());
    }

    @Test
    public void getChapterDescriptionsTest() {
        ReadXML readXML = new ReadXML("1");
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("This chapter is so exciting!!!");
        descriptions.add("This chapter is diffrent and so exciting!!!");
        Assert.assertEquals(descriptions, readXML.getChapterDescriptions());
    }

    @Test
    public void getSingleChapterProgressTest() {
        ReadXML readXML = new ReadXML("1");
        Double stuff = readXML.getChapterProgress(1);
        Assert.assertEquals((Double)2.0, stuff);
    }

    @Test
    public void getChapterProgressTest() {
        ReadXML readXML = new ReadXML("1");
        ArrayList<Double> chapterProgress = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chapterProgress.add(i + 1.0);
        }
        Assert.assertEquals(chapterProgress, readXML.getAllChaptersProgress());
    }

    @Test
    public void getSingleQuizScoreTest() {
        ReadXML readXML = new ReadXML("1");
        Double stuff = readXML.getQuizScore(1);
        Assert.assertEquals((Double)2.0, stuff);
    }

    @Test
    public void getQuizScoresTest() {
        ReadXML readXML = new ReadXML("1");
        ArrayList<Double> quizzes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            quizzes.add(i + 1.0);
        }
        Assert.assertEquals(quizzes, readXML.getAllQuizScores());
    }

    @Test
    public void getLastStudentIDTest() {
        ReadXML readXML = new ReadXML("5");
        Assert.assertEquals("2", readXML.getLastStudentID());
    }
}