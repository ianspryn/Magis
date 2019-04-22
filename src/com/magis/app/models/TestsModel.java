package com.magis.app.models;


public class TestsModel extends ExamsModel {

    public TestsModel() {
        super("tests");
    }

    public boolean hasTest(String chapterName) {
        return chapters.containsKey(chapterName);
    }
}
