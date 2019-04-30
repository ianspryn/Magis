package com.magis.app.page;

import com.magis.app.Main;

public class TestPageContent extends ExamPageContent {
    public TestPageContent(int chapterIndex) {
<<<<<<< HEAD
<<<<<<< HEAD
        super(chapterIndex, Main.numQuestionsPerTest.get(Main.lessonModel.getChapter(chapterIndex).getTitle()), Main.testsModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()), "TEST");
        examSaver.setType("test");
=======
        super(chapterIndex, Main.numQuestionsPerTest.get(Main.lessonModel.getChapter(chapterIndex).getTitle()), Main.testsModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
>>>>>>> parent of 1b7d89d... Merge branch 'UI' into QuestionGeneratorVol2
=======
        super(chapterIndex, Main.numQuestionsPerTest.get(Main.lessonModel.getChapter(chapterIndex).getTitle()), Main.testsModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
>>>>>>> parent of 1b7d89d... Merge branch 'UI' into QuestionGeneratorVol2
    }
}
