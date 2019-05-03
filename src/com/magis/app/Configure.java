package com.magis.app;

public class Configure {

    /******************************************************************************

     Modify these variables before compiling the program to apply custom values

     ******************************************************************************/

    /*
    The default number of questions each quiz will have
     */
    public static final int DEFAULT_NUM_QUIZ_QUESTIONS = 15;
    /*
    The default number of question each test will have
     */
    public static final int DEFAULT_NUM_TEST_QUESTIONS = 35;
    /*
    The number of questions each page on a quiz or test will have
     */
    public static final int NUM_QUESTIONS_PER_PAGE = 2;

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
