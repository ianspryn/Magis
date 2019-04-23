package com.magis.app.page;
import com.magis.app.Configure;
import com.magis.app.Main;

import static com.magis.app.Configure.*;

public class QuizPageContent extends ExamPageContent {
    public QuizPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerQuiz.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_QUIZ_QUESTIONS), Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
    }
}
