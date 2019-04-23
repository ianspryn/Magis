package com.magis.app.page;
import com.magis.app.Main;

import static com.magis.app.Configure.*;

public class QuizSidePanel extends ExamSidePanel {

    public QuizSidePanel(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerQuiz.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_QUIZ_QUESTIONS));
    }
}
