package com.magis.app.models;


public class TestsModel extends ExamsModel {

    public TestsModel() {
        super("tests");
    }

    /**
     * Checks if there is a test associated with this chapter
     * @param chapterName the name of the chapter
     * @return true if there is a test, false otherwise
     */
    public boolean hasTest(String chapterName) {
        return chapters.containsKey(chapterName);
    }
}
