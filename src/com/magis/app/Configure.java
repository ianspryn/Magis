package com.magis.app;

import com.magis.app.models.LessonModel;
import com.magis.app.test.questions.generator.QuestionGenerator;

import java.util.Map;

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
    public static final int DEFAULT_NUM_QUIZ_QUESTIONS = 15;
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

    public static void calculateNumUniqueQuestions() {
        int totalGeneratedQuestions = 0;
        int totalStaticQuestions = 0;
        for (Map.Entry<Integer, QuestionGenerator> iterate : Main.questionGenerator.entrySet()) {
           if (iterate.getValue().getNumUnique() == Integer.MAX_VALUE) continue;
            totalGeneratedQuestions += iterate.getValue().getNumUnique();
        }
        int numChapters = Main.lessonModel.getNumChapters();
        for (int i = 0; i < numChapters; i++) {
            String chapterTitle = Main.lessonModel.getChapter(i).getTitle();
            if (Main.quizzesModel.getChapter(chapterTitle) != null) {
                totalStaticQuestions += Main.quizzesModel.getChapter(chapterTitle).getNumAvailableQuestions();
            }
            if (Main.testsModel.getChapter(chapterTitle) != null) {
                totalStaticQuestions += Main.testsModel.getChapter(chapterTitle).getNumAvailableQuestions();
            }
        }
        System.out.println("Not counting question generators who use random integers (resulting in basically infinite number of unique qusetions), the number of unique questions for " +
                "question generators is " + totalGeneratedQuestions);
        System.out.println("The number of static questions, both quizzes and tests is " + totalStaticQuestions);
        System.out.println("This is a grand total of " + (totalGeneratedQuestions + totalStaticQuestions));
    }
}
