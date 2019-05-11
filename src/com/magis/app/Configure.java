package com.magis.app;

public class Configure {

    public static final String PINK = "#E91E63"; //Pink 500
    public static final String PURPLE = "#9C27B0"; //Purple 500
    public static final String CYAN = "#00BCD4"; //Pink 500
    public static final String GREEN = "#8BC34A"; //Pink 500
    public static final String BLUE_GRAY = "#607D8B"; //Pink 500

    /******************************************************************************

     Modify these variables before compiling the program to apply custom values

     ******************************************************************************/

    /*
    The default number of questions each quiz will have
     */
    public static final int DEFAULT_NUM_QUIZ_QUESTIONS = 100;
    /*
    The default number of question each test will have
     */
    public static final int DEFAULT_NUM_TEST_QUESTIONS = 35;
    /*
    The number of questions each page on a quiz or test will have
     */
    public static final int NUM_QUESTIONS_PER_PAGE = 2;
    /*
    The worst acceptable score a student can get on a test
     */
    public static final int MINIMUM_TEST_SCORE = 70;
    /*
    The worst acceptable score a student can get on a quiz
     */
    public static  final int MINIMUM_QUIZ_SCORE = 70;

    public static void values() {
        /*
        To make a certain quiz have a custom number of questions, set it here
        The key is the chapterName
        The value is the the number of questions
         */
//        Main.numQuestionsPerQuiz.put(Main.lessonModel.getChapter(0).getTitle(), 20);
//        Main.numQuestionsPerQuiz.put(Main.lessonModel.getChapter(1).getTitle(), 13);

        /*
        To make a certain test have a custom number of questions, set it here
        The key is the chapterName
        The value is the the number of questions
         */
//        Main.numQuestionsPerTest.put(Main.lessonModel.getChapter(0).getTitle(), 50);
    }
}
