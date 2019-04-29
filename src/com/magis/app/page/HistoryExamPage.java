package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.models.StudentModel;

public class HistoryExamPage {

    private int chapterIndex;
    private int index;
    StudentModel.Student.Attempt attempt;

    public HistoryExamPage(int chapterIndex, int index, String type) {
        this.chapterIndex = chapterIndex;
        this.index = index;
        switch (type) {
            case "quiz":
                attempt = Main.studentModel.getStudent().getQuiz(chapterIndex).getAttempt(index);
                break;
            case "test":
                attempt = Main.studentModel.getStudent().getTest(chapterIndex).getAttempt(index);
                break;
        }
        buildPage();
    }

    private void buildPage() {

    }


}
