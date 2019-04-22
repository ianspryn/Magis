package com.magis.app.page;
import com.magis.app.Main;

public class QuizPageContent extends ExamPageContent {
    public QuizPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerQuiz.get(Main.lessonModel.getChapter(chapterIndex).getTitle()), Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
    }
}
